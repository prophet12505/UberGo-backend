package com.example.ubergo.entity;

import com.example.ubergo.DTO.RedisObject.TrackObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@JsonSerialize
public class Track implements Serializable {
//    private Long id;
    private Long rideId;
    private LocalDateTime timeSeries;
    private String gpsTrajectory;
    private String speedTrajectory;
    private String altitudeTrajectory;
    public Track(TrackObject trackObject) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        this.rideId = trackObject.getRideId();
        this.timeSeries = trackObject.getTimeSeries();
        this.gpsTrajectory = objectMapper.writeValueAsString(trackObject.getGpsTrajectory());
        this.speedTrajectory = objectMapper.writeValueAsString(trackObject.getSpeedTrajectory());
        this.altitudeTrajectory = objectMapper.writeValueAsString(trackObject.getAltitudeTrajectory());
    }
}
