package com.example.ubergo.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Track {
    private Long id;
    private Long tripId;
    private LocalDateTime timeSeries;
    private String gpsTrajectory;
    private Double speedTrajectory;
    private Double altitudeTrajectory;
}
