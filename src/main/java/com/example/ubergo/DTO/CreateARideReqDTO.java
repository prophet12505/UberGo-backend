package com.example.ubergo.DTO;

import lombok.Data;

@Data
public class CreateARideReqDTO {
    private Integer uid;
    private Double pickUpLat ;
    private Double pickUpLong;
    private String type;
    private String pickUpResolvedAddress;
    private String destResolvedAddress;
    private String province;
    private String city;
}
