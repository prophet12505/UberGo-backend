package com.example.ubergo.DTO;

import com.example.ubergo.entity.Ride;
import com.example.ubergo.utils.LocationUtils;
import lombok.Data;

import java.util.List;


@Data
public class GetRideInforResDTO {
    private int rid;
    private String createTime;
    private LocationDTO pickUpLocation;
    private LocationDTO destLocation;
    private String pickUpResolvedAddress;
    private String destResolvedAddress;
    private double estimatedMileage;
    private String type;
    private String status;
    private String channel;
    private List<LocationDTO> nearbyVehicles;

    public GetRideInforResDTO(Ride ride){
        this.rid=ride.getId();
        this.createTime= String.valueOf(ride.getCreationTime());
        this.pickUpLocation=new LocationDTO(ride.getPickUpLong(),ride.getPickUpLat());
        this.destLocation=new LocationDTO(ride.getDestLong(),ride.getDestLat());
        this.pickUpResolvedAddress=ride.getPickUpResolvedAddress();
        this.destResolvedAddress=ride.getDestResolvedAddress();
//        this.estimatedMileage= LocationUtils.distance(ride.getPickUpLong(),ride.getPickUpLat(),ride.getDestLong(),ride.getDestLat());
        this.estimatedMileage=0;
        this.type= ride.getType();
        this.status= ride.getStatus();
        this.channel=ride.getChannel();
        this.nearbyVehicles=null;
    }
}

