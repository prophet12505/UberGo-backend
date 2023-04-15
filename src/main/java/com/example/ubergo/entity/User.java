package com.example.ubergo.entity;

import lombok.Data;

@Data
public class User {
    private Long uid;
    private String mobileNumber;
    private String identity;
    private String password;
    private String userName;
    private String licensePlateNumber;
    private String carType;
    private Double totalTripLength;
    private String regionalProvince;
    private String city;



}

