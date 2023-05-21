package com.example.ubergo.DTO.MqttDTO;

import lombok.Data;

@Data
public class locationShorthandDTO {
    public locationShorthandDTO(String lng, String lat) {
        this.lng = lng;
        this.lat = lat;
    }

    private String lng;
    private String lat;

}
