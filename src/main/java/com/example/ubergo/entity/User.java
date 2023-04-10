package com.example.ubergo.entity;

import lombok.Data;

@Data
public class User {
    private int uid;
    private String mobileNumber;
    private String identity;
    private String password;
    private String userName;
    private String licensePlateNumber;
    private String carType;
    private float totalTripLength;
    private String regionalProvince;
    private String city;



}

