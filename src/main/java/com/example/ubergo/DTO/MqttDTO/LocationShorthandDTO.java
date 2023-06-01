package com.example.ubergo.DTO.MqttDTO;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@JsonSerialize
public class LocationShorthandDTO implements Serializable {
    public LocationShorthandDTO(String lng, String lat) {
        this.lng = lng;
        this.lat = lat;
    }

    private String lng;
    private String lat;

}
