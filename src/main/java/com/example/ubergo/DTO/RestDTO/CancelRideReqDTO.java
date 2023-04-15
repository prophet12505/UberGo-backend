package com.example.ubergo.DTO.RestDTO;

import lombok.Data;

@Data
public class CancelRideReqDTO {
    private Long uid;
    private Boolean cancel;
}
