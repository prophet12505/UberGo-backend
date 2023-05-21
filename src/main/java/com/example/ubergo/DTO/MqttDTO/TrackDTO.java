package com.example.ubergo.DTO.MqttDTO;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TrackDTO {
    private Long rideId;
    private LocalDateTime timeSeries;

    private List<Object> gpsTrajectory;
    //object is to be modified
    private List<Object> speedTrajectory;
    private List<Object> altitudeTrajectory;
}
