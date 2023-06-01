package com.example.ubergo.config;

import com.example.ubergo.DTO.MqttDTO.LocationShorthandDTO;
import com.example.ubergo.DTO.MqttDTO.MqttMessageWrapperDTO;
import com.example.ubergo.DTO.MqttDTO.TrackUpdateDTO;
import com.example.ubergo.DTO.RedisObject.TrackObject;
import com.example.ubergo.entity.Track;
import com.example.ubergo.service.RideTrackingService;
import com.example.ubergo.utils.LocationUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static com.example.ubergo.utils.Constants.UPDATE_TRACK;

@Slf4j
@Component
public class PushCallback implements MqttCallback {
    @Autowired
    private MqttConfiguration mqttConfiguration;
    private RideTrackingService rideTrackingService;
    private final RedisTemplate<String, TrackObject> redisTrackTemplate;
    static Logger LOGGER = LoggerFactory.getLogger(PushCallback.class);

    public PushCallback(RideTrackingService rideTrackingService, RedisTemplate<String, TrackObject> redisTemplate) {
        this.rideTrackingService = rideTrackingService;
        this.redisTrackTemplate = redisTemplate;
    }

    @Override
    public void connectionLost(Throwable cause) {        // 连接丢失后，一般在这里面进行重连
        log.info("connection lost，reconnecting...");
        MqttPushClient mqttPushClient = mqttConfiguration.getMqttPushClient();
        if (null != mqttPushClient) {
            mqttPushClient.connect(mqttConfiguration.getHost(), mqttConfiguration.getClientid(), mqttConfiguration.getUsername(),
                    mqttConfiguration.getPassword(), mqttConfiguration.getTimeout(), mqttConfiguration.getKeepalive(),mqttConfiguration.getPublishQos(),mqttConfiguration.getSubscribeQos());
            log.info("already reconnected");
        }

    }

    /**
     * 发送消息，消息到达后处理方法
     * @param token
     */
    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }
    /**
     * 订阅主题接收到消息处理方法
     * @param topic
     * @param message
     */
    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception{
        // arrived message would be outputed here
        log.info("message topic : " + topic);
        log.info("received message Qos : " + message.getQos());
        String messageJSONString=new String(message.getPayload());
        log.info("content: " + messageJSONString);
        try{


        // tracking module -- message arrived from private tracking channel
        MqttMessageWrapperDTO mqttMessageWrapperDTO=new MqttMessageWrapperDTO(messageJSONString);
        switch (mqttMessageWrapperDTO.getAction()){
            case UPDATE_TRACK:
            {
                LOGGER.info("UPDATING TRACK FROM CHANNEL:"+topic);
                //update the track to database
                try{
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.registerModule(new JavaTimeModule());
                    TrackUpdateDTO trackUpdateDTO = objectMapper.convertValue(mqttMessageWrapperDTO.getPayload(), TrackUpdateDTO.class);
                    trackUpdateDTO.setTimeSeries(LocalDateTime.now());
                    //Track track=new Track(trackObject);
                    TrackObject prevTrackObject = (TrackObject) redisTrackTemplate.opsForHash().get("tracks", trackUpdateDTO.getRideId().toString());
                    if(prevTrackObject==null)
                    {
                        TrackObject newTrackObject=new TrackObject(trackUpdateDTO.getRideId());
                        //add new update into trajectory
                        newTrackObject.updateTrajectory(trackUpdateDTO);
                        redisTrackTemplate.opsForHash().put("tracks", trackUpdateDTO.getRideId().toString(), newTrackObject);

                    } else{
                        //compute the distance between track and previous track and updata total length
                        //I need to add 3 extra fields inthe trackUpdateDTO to express the current volume and compute route
                        LocationShorthandDTO prevLocation=prevTrackObject.getGpsTrajectory().get(prevTrackObject.getGpsTrajectory().size()-1);
                        Double lengthIncrement=LocationUtils.distance(
                                Double.parseDouble(prevLocation.getLng()),
                                Double.parseDouble(prevLocation.getLat()),
                                Double.parseDouble(trackUpdateDTO.getCurrentGps().getLng()),
                                Double.parseDouble(trackUpdateDTO.getCurrentGps().getLat())
                        );

                        //add updated version to it
                        prevTrackObject.updateTrajectory(trackUpdateDTO);
                        //add length increment
                        prevTrackObject.setTotalLength(prevTrackObject.getTotalLength()+lengthIncrement);


                        LOGGER.info("A Trajectory:"+prevTrackObject.getAltitudeTrajectory().toString());
                        //update total length, todo

                        redisTrackTemplate.opsForHash().put("tracks", trackUpdateDTO.getRideId().toString(), prevTrackObject);
                    }
                    //the updated tracking is written to redis cache and then written to database, to do



                }
                catch (Exception e){
                    LOGGER.error(e.getMessage());
                    LOGGER.error(e.getLocalizedMessage());
                    LOGGER.error(String.valueOf(e.getStackTrace()));
                    LOGGER.error(String.valueOf(e.getCause()));

                }
                break;
            }
            default:
                break;
        }
        }
        catch (Exception e){
            LOGGER.error(e.getMessage());
        }
    }

}