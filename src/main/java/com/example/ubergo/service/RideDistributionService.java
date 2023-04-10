package com.example.ubergo.service;

import com.example.ubergo.DTO.CreateARideReqDTO;
import com.example.ubergo.DTO.GeneralMessageDTO;
import com.example.ubergo.DTO.TakeOrderReqDTO;
import com.example.ubergo.UberGoApplication;
import com.example.ubergo.entity.OrderForm;
import com.example.ubergo.entity.Ride;
import com.example.ubergo.mapper.OrderFormMapper;
import com.example.ubergo.mapper.RideMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
@Slf4j
@Service
public class RideDistributionService {
    static Logger LOGGER = LoggerFactory.getLogger(RideDistributionService.class);
    private final RideMapper rideMapper;
    private final OrderFormMapper orderFormMapper;

    @Autowired
    public RideDistributionService(RideMapper rideMapper, OrderFormMapper orderFormMapper) {
        this.rideMapper = rideMapper;
        this.orderFormMapper = orderFormMapper;
    }

    //need to check if previous order is paid or not
    public GeneralMessageDTO createARide(CreateARideReqDTO createARideReqDTO){
        try{
            Ride ride = new Ride(createARideReqDTO);
            int passengerUid=ride.getPassengerUid();
            OrderForm orderForm=orderFormMapper.selectUnpaidUserById(passengerUid);
            if(orderForm!=null){
                return new GeneralMessageDTO(201, "Order not paid");
            }
            else{
//                Integer rideId=rideMapper.selectLargestRideId();
//                if(rideId==null) rideId=0;
//                rideId+=1;
                final int finalRideId = rideMapper.getNextRideId();
                String channelName=finalRideId+"-"+createARideReqDTO.getProvince()+"-"+createARideReqDTO.getCity();
                ride.setChannel(channelName);
                rideMapper.createARide(ride);

                return new GeneralMessageDTO(0,"Success", new Object() {
                    public final int rid = finalRideId;
                });
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return new GeneralMessageDTO(209, "ERROR:"+e.getMessage());
        }


    }

    public GeneralMessageDTO takeOrder(int rid, TakeOrderReqDTO takeOrderReqDTO){
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

}
