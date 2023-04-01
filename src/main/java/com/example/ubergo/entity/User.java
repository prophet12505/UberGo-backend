package com.example.ubergo.entity;

import lombok.Data;

@Data
public class User {
    private int UID;
    private String mobileNumber;
    private String identity;
    private String key;
    private String userName;
    private String licensePlateNumber;
    private String carType;
    private float totalTripLength;
    private String regionalProvince;
    private String city;
}

