package com.example.ubergo.DTO.RestDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LocationDTO {
    public LocationDTO(Double longitude, Double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    @JsonProperty("long")
    private Double longitude;
    @JsonProperty("lat")
    private Double latitude;
}
