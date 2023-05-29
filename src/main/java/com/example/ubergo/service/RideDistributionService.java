package com.example.ubergo.service;

import com.example.ubergo.DTO.MqttDTO.MqttMessageWrapperDTO;
import com.example.ubergo.DTO.MqttDTO.PickUpDTO;
import com.example.ubergo.DTO.RedisObject.TrackObject;
import com.example.ubergo.DTO.RestDTO.*;
import com.example.ubergo.config.MqttPushClient;
import com.example.ubergo.config.MqttSubClient;
import com.example.ubergo.entity.OrderForm;
import com.example.ubergo.entity.Ride;
import com.example.ubergo.entity.Track;
import com.example.ubergo.entity.User;
import com.example.ubergo.mapper.OrderFormMapper;
import com.example.ubergo.mapper.RideMapper;
import com.example.ubergo.mapper.TrackMapper;
import com.example.ubergo.mapper.UserMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.ubergo.utils.Constants.*;

@Slf4j
@Service
public class RideDistributionService {
    static Logger LOGGER = LoggerFactory.getLogger(RideDistributionService.class);
    private final UserMapper userMapper;
    private final RideMapper rideMapper;
    private final OrderFormMapper orderFormMapper;
    private final TrackMapper trackMapper;
    private  final RideTrackingService rideTrackingService;
    private final RedisTemplate<String, TrackObject> redisTrackTemplate;
    @Autowired
    private MqttPushClient mqttPushClient;
    @Autowired
    private MqttSubClient mqttSubClient;

    @Autowired
    public RideDistributionService(UserMapper userMapper, RideMapper rideMapper, OrderFormMapper orderFormMapper, TrackMapper trackMapper, RideTrackingService rideTrackingService, RedisTemplate<String, TrackObject> redisTrackTemplate) {
        this.userMapper = userMapper;
        this.rideMapper = rideMapper;
        this.orderFormMapper = orderFormMapper;
        this.trackMapper = trackMapper;
        this.rideTrackingService = rideTrackingService;
        this.redisTrackTemplate = redisTrackTemplate;
    }

    //need to check if previous order is paid or not
    public GeneralMessageDTO createARide(CreateARideReqDTO createARideReqDTO){
        try{
            Ride ride = new Ride(createARideReqDTO);
            Long passengerUid=ride.getPassengerUid();
            OrderForm orderForm=orderFormMapper.selectUnpaidUserById(passengerUid);
            if(orderForm!=null){
                return new GeneralMessageDTO(201, "Order not paid");
            }
            else{

                //create ride record in the database
                final long finalRideId = rideMapper.getNextRideId();
                String channelName=finalRideId+"-"+createARideReqDTO.getProvince()+"-"+createARideReqDTO.getCity();
                ride.setChannel(channelName);
                ride.setStatus(CREATED);
                rideMapper.createARide(ride);
                ride.setId(finalRideId);

                // find related user accoring to ride
                User user=userMapper.getById(ride.getPassengerUid());
                //send message to the MQTT dispatch channel
                ObjectMapper objectMapper=new ObjectMapper();

                mqttPushClient.publish("distribution"+"-"+createARideReqDTO.getProvince()+"-"+createARideReqDTO.getCity(),
                        objectMapper.writeValueAsString(new MqttMessageWrapperDTO(DISTRIBUTION_RIDE_CREATED,new PickUpDTO(ride,user))),
                        2);
                mqttSubClient.subscribe("distribution"+"-"+createARideReqDTO.getProvince()+"-"+createARideReqDTO.getCity());
                //call Tracking service to subscribe to the private channel
                rideTrackingService.monitorItinerary(channelName);

                return new GeneralMessageDTO(0,"Success", new Object() {
                    public final long rid = finalRideId;
                });
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return new GeneralMessageDTO(209, "ERROR:"+e.getMessage());
        }


    }

    public GeneralMessageDTO takeOrder(Long rid, TakeOrderReqDTO takeOrderReqDTO){
        try{
            Ride ride =rideMapper.getById(rid);
            LOGGER.info(ride.getStatus());
            LOGGER.info(String.valueOf(ride.getDriverUid()));
            if(ride.getDriverUid()!=null){
                return new GeneralMessageDTO(211,"Ride has been accepted by others");
            }
            else{
                // update ride record
                ride.setDriverUid(takeOrderReqDTO.getDriverUid());
                ride.setStatus(GOING_TO_DEPARTURE_POINT);
                rideMapper.updateRide(ride);

                // find driver user accoring to ride
                User user=userMapper.getById(ride.getDriverUid());
                //push mqtt message to let client know ride has been taken
                ObjectMapper objectMapper=new ObjectMapper();

                //temporory solution for Jackson localTimeDate unsupported error: set creationTime to null
                ride.setCreationTime(null);
                mqttPushClient.publish(ride.getChannel(),
                        objectMapper.writeValueAsString(new MqttMessageWrapperDTO(ORDER_HAS_BEEN_TAKEN,new PickUpDTO(ride,user))),
                        2);
                //push to public channel to inform other driver
                User passenger=userMapper.getById(ride.getPassengerUid());
                mqttPushClient.publish("distribution"+"-"+passenger.getRegionalProvince()+"-"+passenger.getCity(),
                        objectMapper.writeValueAsString(new MqttMessageWrapperDTO(DISTRIBUTION_RIDE_PICKED_UP,new Object(){public final Long rid=ride.getId();})
                        ), 2);

                return new GeneralMessageDTO(0,"Success",new Object(){public final String channel=ride.getChannel();});
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return new GeneralMessageDTO(599,"Error"+e.getMessage());
        }
    }
    public GeneralMessageDTO pickUpPassenger(PickUpDTO pickUpDTO){
        try
        {
            //update the status
            pickUpDTO.getRide().setStatus(ARRIVING);
            rideMapper.updateRide( pickUpDTO.getRide());

            //send mqtt message to rider's client to notify
            ObjectMapper objectMapper=new ObjectMapper();
            //push to private channel that passenger has been picked up
            mqttPushClient.publish(pickUpDTO.getRide().getChannel(),
                    objectMapper.writeValueAsString(new MqttMessageWrapperDTO(PASSENGER_HAS_BEEN_PICKED_UP, null)),
                    2);


            return  new GeneralMessageDTO(0,"Success");
        }
        catch (Exception e){
            e.printStackTrace();
            return new GeneralMessageDTO(599,"Error"+e.getMessage());
        }
    }

    public GeneralMessageDTO cancelRide( Long rid,  CancelRideReqDTO cancelRideReqDTO) {
        if(cancelRideReqDTO.getCancel()==Boolean.TRUE){
            Ride ride = rideMapper.getById(rid);
            if(ride.getStatus().equals(CANCELLED)){
                return new GeneralMessageDTO(221,"Ride has been cancelled");
            }
            //call tracking service to resolve it if picked up
            if(ride.getStatus().equals(ARRIVING) || ride.getStatus().equals(GOING_TO_DEPARTURE_POINT)){
                // call tracking model to save it
                rideTrackingService.cancelPickedUpRide(rid,  cancelRideReqDTO);
            }

            ride.setStatus(CANCELLED);
            rideMapper.updateRide(ride);
            return new GeneralMessageDTO(0,"Success");



        }

            return new GeneralMessageDTO(0,"cancel is set to false, No operation executed!");

    }
    public GeneralMessageDTO arrive( Long rid){
        try{

            //get track from redis table, write track table to confirm
            TrackObject storedTrack = (TrackObject) redisTrackTemplate.opsForHash().get("tracks", rid.toString());
            if(storedTrack!=null){
                ObjectMapper objectMapper=new ObjectMapper();
                //LOGGER.debug("StoredTrack"+objectMapper.writeValueAsString(storedTrack));
                Track trackToUpdate=new Track(storedTrack);
//                LOGGER.error("Track ride"+trackToUpdate.getRideId());
//                LOGGER.error("Track time series"+trackToUpdate.getTimeSeries().toString());
//                LOGGER.error("Track GPS"+trackToUpdate.getGpsTrajectory().toString());
//                LOGGER.error("Track speed"+trackToUpdate.getSpeedTrajectory().toString());
//                LOGGER.error("Track Altitude"+trackToUpdate.getAltitudeTrajectory().toString());
                //trackMapper.insertTrack(trackToUpdate);
                rideTrackingService.updateATrack(trackToUpdate);
            }
            else
                LOGGER.error("Tracking Object is null!");

            //update status
            Ride ride = rideMapper.getById(rid);
            ride.setStatus(ARRIVED);
            //update arrive time
            ride.setArrivalTime( LocalDateTime.now());
            //get updated length in redis cache
            ride.setTotalLength(storedTrack.getTotalLength());
            rideMapper.updateRide(ride);

            //pull order module, to do

            //send mqtt message to client to confirm arrived
            ObjectMapper objectMapper=new ObjectMapper();
            mqttPushClient.publish(ride.getChannel(),
                    objectMapper.writeValueAsString(new MqttMessageWrapperDTO(DESTINATION_ARRIVED, new Object(){public Long rid=ride.getId();})),
                    2);
            return new GeneralMessageDTO(0,"Success");
        }
        catch (Exception e){
            e.printStackTrace();
            LOGGER.info(e.getMessage());
            return  new GeneralMessageDTO(599,e.getMessage());
        }
    }

    public  GeneralMessageDTO getRiderInfo( Long rid,  LocationDTO locationDTO){
        try{
            Ride ride=rideMapper.getById(rid);
            GetRideInforResDTO getRideInforResDTO;
            if(ride.getDriverUid()!=null){
                User driver=userMapper.getById(ride.getDriverUid());
                 getRideInforResDTO=new GetRideInforResDTO(ride,driver);
            }
            else {
                 getRideInforResDTO=new GetRideInforResDTO(ride);
            }
            return new GeneralMessageDTO(0,"Success",getRideInforResDTO);
        }
        catch (Exception e){
            e.printStackTrace();
            return new GeneralMessageDTO(599,"ERROR:"+e.getMessage());
        }
    }
    public GeneralMessageDTO getItineraryHistory(Long uid){
        try{
            //query by ride status
             List<Ride> rideList=rideMapper.getRideByPassengerId(uid);
             List<GetItineraryHistoryResDTO> getItineraryHistoryResDTOList=new ArrayList<>();
            for(Ride ride:rideList){
                GetItineraryHistoryResDTO getItineraryHistoryResDTO=new GetItineraryHistoryResDTO();
                //set ride
                getItineraryHistoryResDTO.setRide(ride);
                //set driver
                User driver=userMapper.getById(ride.getDriverUid());
                getItineraryHistoryResDTO.setDriver(driver);
                //set track
                Track track=trackMapper.getByRideId(ride.getId());
                getItineraryHistoryResDTO.setTrack(track);

                getItineraryHistoryResDTOList.add(getItineraryHistoryResDTO);
            }
                return new GeneralMessageDTO(0,"Success",
                        getItineraryHistoryResDTOList);

        }
        catch (Exception e){
            e.printStackTrace();
            return new GeneralMessageDTO(599,"ERROR:"+e.getMessage());
        }
    }


}
