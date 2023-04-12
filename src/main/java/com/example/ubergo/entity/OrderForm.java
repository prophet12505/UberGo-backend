package com.example.ubergo.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderForm {
    private Integer id;
    private Integer rideId;
    private LocalDateTime creationTime;
    private Double totalPrice;
    private Double startingPrice;
    private Double tourFees;
    private Double fuelCosts;
    private Double timeFee;
    private Double specialLocationFee;
    private Double dynamicPrices;
    private String status;
    private String paymentPlatform;
    private String paymentSerialNumber;
    private String paymentResult;

    // Constructor, getters, and setters
    public OrderForm(Integer rid){
        this.rideId=rid;
    }
}