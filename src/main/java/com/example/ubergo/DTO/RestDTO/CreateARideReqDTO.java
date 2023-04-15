package com.example.ubergo.DTO.RestDTO;

import lombok.Data;

@Data
public class CreateARideReqDTO {
    private Long uid;
    private Double pickUpLat ;
    private Double pickUpLong;
    private String type;
    private String pickUpResolvedAddress;
    private String destResolvedAddress;
    private Double destLong;
    private Double destLat;
    private String province;
    private String city;
}
