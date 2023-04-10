package com.example.ubergo.DTO;

import lombok.Data;

@Data
public class TakeOrderReqDTO {
    private Integer driverUid;
    private Double longitude;
    private Double latitude;
    private String numberPlate;
    private String vehicleInfo;
}
