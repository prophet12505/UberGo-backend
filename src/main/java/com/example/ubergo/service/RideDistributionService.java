package com.example.ubergo.service;

import com.example.ubergo.DTO.MqttDTO.MqttMessageWrapperDTO;
import com.example.ubergo.DTO.RestDTO.*;
import com.example.ubergo.config.MqttPushClient;
import com.example.ubergo.config.MqttSubClient;
import com.example.ubergo.entity.OrderForm;
import com.example.ubergo.entity.Ride;
import com.example.ubergo.mapper.OrderFormMapper;
import com.example.ubergo.mapper.RideMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.ubergo.utils.Constants.*;

@Slf4j
@Service
public class RideDistributionService {
    static Logger LOGGER = LoggerFactory.getLogger(RideDistributionService.class);
    private final RideMapper rideMapper;
    private final OrderFormMapper orderFormMapper;
    @Autowired
    private MqttPushClient mqttPushClient;
    @Autowired
    private MqttSubClient mqttSubClient;

    @Autowired
    public RideDistributionService(RideMapper rideMapper, OrderFormMapper orderFormMapper) {
        this.rideMapper = rideMapper;
        this.orderFormMapper = orderFormMapper;
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
                //send message to the MQTT dispatch channel
                ObjectMapper objectMapper=new ObjectMapper();

                mqttPushClient.publish("distribution"+"-"+createARideReqDTO.getProvince()+"-"+createARideReqDTO.getCity(),
                        objectMapper.writeValueAsString(new MqttMessageWrapperDTO(DISTRIBUTION_RIDE_CREATED,ride)),
                        2);
                mqttSubClient.subscribe("distribution"+"-"+createARideReqDTO.getProvince()+"-"+createARideReqDTO.getCity());

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
                ride.setDriverUid(takeOrderReqDTO.getDriverUid());

                rideMapper.updateRide(ride);
                return new GeneralMessageDTO(0,"Success",new Object(){public final String channel=ride.getChannel();});
            }
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
            if(ride.getStatus().equals(ARRIVING)){
                // call tracking model to save it
            }
            else{
                ride.setStatus(CANCELLED);
                rideMapper.updateRide(ride);
                return new GeneralMessageDTO(0,"Success");
            }

        }

            return new GeneralMessageDTO(0,"cancel is set to false, No operation executed!");

    }

    public  GeneralMessageDTO getRiderInfo( Long rid,  LocationDTO locationDTO){
        try{
            Ride ride=rideMapper.getById(rid);
//            ObjectMapper objectMapper = new ObjectMapper();
//            LOGGER.info(objectMapper.writeValueAsString(ride));
            GetRideInforResDTO getRideInforResDTO=new GetRideInforResDTO(ride);
            return new GeneralMessageDTO(0,"Success",getRideInforResDTO);
        }
        catch (Exception e){
            e.printStackTrace();
            return new GeneralMessageDTO(599,"ERROR:"+e.getMessage());
        }
    }

}
