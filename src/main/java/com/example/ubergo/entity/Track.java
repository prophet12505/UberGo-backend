package com.example.ubergo.entity;

import com.example.ubergo.DTO.MqttDTO.TrackDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Track {
//    private Long id;
    private Long rideId;
    private LocalDateTime timeSeries;
    private String gpsTrajectory;
    private String speedTrajectory;
    private String altitudeTrajectory;
    public Track(TrackDTO trackDTO) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        this.rideId = trackDTO.getRideId();
        this.timeSeries = trackDTO.getTimeSeries();
        this.gpsTrajectory = objectMapper.writeValueAsString(trackDTO.getGpsTrajectory());
        this.speedTrajectory = objectMapper.writeValueAsString(trackDTO.getSpeedTrajectory());
        this.altitudeTrajectory = objectMapper.writeValueAsString(trackDTO.getAltitudeTrajectory());
    }
}
