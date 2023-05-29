package com.example.ubergo.DTO.MqttDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class TrackUpdateDTO implements Serializable {
    private Long rideId;
    private LocalDateTime timeSeries;

    private LocationShorthandDTO currentGps;
    //object is to be modified
    private Double currentSpeed;
    private Double currentAltitude;
}
