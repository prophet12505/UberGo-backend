package com.example.ubergo.config;

import com.example.ubergo.DTO.MqttDTO.MqttMessageWrapperDTO;
import com.example.ubergo.DTO.MqttDTO.TrackDTO;
import com.example.ubergo.entity.Track;
import com.example.ubergo.service.RideTrackingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.example.ubergo.utils.Constants.UPDATE_TRACK;

@Slf4j
@Component
public class PushCallback implements MqttCallback {
    @Autowired
    private MqttConfiguration mqttConfiguration;
    private RideTrackingService rideTrackingService;
    static Logger LOGGER = LoggerFactory.getLogger(PushCallback.class);

    public PushCallback(RideTrackingService rideTrackingService) {
        this.rideTrackingService = rideTrackingService;
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
    public void messageArrived(String topic, MqttMessage message) {
        // arrived message would be outputed here
        log.info("message topic : " + topic);
        log.info("received message Qos : " + message.getQos());
        String messageJSONString=new String(message.getPayload());
        log.info("content: " + messageJSONString);

        // tracking module -- message arrived from private tracking channel
        MqttMessageWrapperDTO mqttMessageWrapperDTO=new MqttMessageWrapperDTO(messageJSONString);
        switch (mqttMessageWrapperDTO.getAction()){
            case UPDATE_TRACK:
            {
                LOGGER.info("UPDATING TRACK FROM CHANNEL:"+topic);
                //update the track to database
                try{
                    ObjectMapper objectMapper = new ObjectMapper();
                    TrackDTO trackDTO = objectMapper.convertValue(mqttMessageWrapperDTO.getPayload(), TrackDTO.class);
                    Track track=new Track(trackDTO);
                    rideTrackingService.updateATrack(track);
                }
                catch (Exception e){
                    LOGGER.error(e.getMessage());
                }
                break;
            }
            default:
                break;
        }
    }

}