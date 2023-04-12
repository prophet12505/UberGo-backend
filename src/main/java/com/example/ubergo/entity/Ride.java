package com.example.ubergo.entity;

import com.example.ubergo.DTO.CreateARideReqDTO;
import jakarta.persistence.Column;
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
    @Column(name = "pick_up_lat")
    private Double pickUpLat ;
    @Column(name = "pick_up_long")
    private Double pickUpLong;
    @Column(name = "pick_up_resolved_address")
    private String pickUpResolvedAddress;
    @Column(name = "dest_lat")
    private Double destLat;
    @Column(name = "dest_long")
    private Double destLong;
    @Column(name = "dest_resolved_address")
    private String destResolvedAddress;
    private String status;
    @Column(name = "order_take_time")
    private LocalDateTime orderTakeTime;
    private LocalDateTime pickUpTime;
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
