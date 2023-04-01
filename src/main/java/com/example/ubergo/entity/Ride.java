package com.example.ubergo.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Ride {
    private int id;
    private LocalDateTime creationTime;
    private int passengerUid;
    private int driverUid;
    private String mqttChannel;
    private String itineraryType;
    private float departureLatitude;
    private float departureLongitude;
    private String departureAddress;
    private float endLatitude;
    private float endLongitude;
    private String endAddress;
    private String status;
    private LocalDateTime orderTakeTime;
    private LocalDateTime pickupTime;
    private LocalDateTime arrivalTime;
    private LocalDateTime cancelTime;
    private float totalLength;
    private int itineraryOrderId;
    private String alarmStatus;
    private String afterSalesStatus;
    private float tripEvaluationScore;
    private String itineraryEvaluationContent;

    // Constructor, getters, and setters
}
