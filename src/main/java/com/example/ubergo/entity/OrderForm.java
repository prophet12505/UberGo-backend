package com.example.ubergo.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderForm {
    private int id;
    private int tripId;
    private LocalDateTime creationTime;
    private float totalPrice;
    private float startingPrice;
    private float tourFees;
    private float fuelCosts;
    private float timeFee;
    private float specialLocationFee;
    private float dynamicPrices;
    private String status;
    private String paymentPlatform;
    private String paymentSerialNumber;
    private String paymentResult;

    // Constructor, getters, and setters
}