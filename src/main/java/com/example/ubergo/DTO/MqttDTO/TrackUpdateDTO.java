package com.example.ubergo.DTO.MqttDTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@JsonSerialize
public class TrackUpdateDTO implements Serializable {
    private Long rideId;
    @JsonIgnore
    private LocalDateTime timeSeries;

    private LocationShorthandDTO currentGps;
    //object is to be modified
    private Double currentSpeed;
    private Double currentAltitude;
}
