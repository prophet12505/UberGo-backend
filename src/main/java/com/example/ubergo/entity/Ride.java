package com.example.ubergo.entity;

import com.example.ubergo.DTO.CreateARideReqDTO;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Ride {
    private Integer id;
    private LocalDateTime creationTime;
    private Integer passengerUid;
    private Integer driverUid;
    private String channel;
    private String type;
    private Double pickUpLat ;
    private Double pickUpLong;
    private String pickUpResolvedAddress;
    private Double destLat;
    private Double destLong;
    private String destResolvedAddress;
    private String status;
    private LocalDateTime orderTakeTime;
    private LocalDateTime pickupTime;
    private LocalDateTime arrivalTime;
    private LocalDateTime cancelTime;
    private Double totalLength;
    private Integer itineraryOrderId;
    private String alarmStatus;
    private String afterSalesStatus;
    private Integer tripEvaluationScore;
    private String itineraryEvaluationContent;

    // Constructor, getters, and setters
    public Ride(CreateARideReqDTO createARideReqDTO){

        this.passengerUid=createARideReqDTO.getUid();
        this.pickUpLat=createARideReqDTO.getPickUpLat();
        this.pickUpLong=createARideReqDTO.getPickUpLong();
        this.pickUpResolvedAddress=createARideReqDTO.getPickUpResolvedAddress();
        this.destResolvedAddress=createARideReqDTO.getDestResolvedAddress();
        this.type=createARideReqDTO.getType();

    }
    public Ride(){

    }
}
