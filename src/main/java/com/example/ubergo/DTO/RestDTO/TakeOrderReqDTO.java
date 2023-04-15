package com.example.ubergo.DTO.RestDTO;

import lombok.Data;

@Data
public class TakeOrderReqDTO {
    private Long driverUid;
    private Double longitude;
    private Double latitude;
    private String numberPlate;
    private String vehicleInfo;
}
