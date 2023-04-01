package com.example.ubergo.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Track {
    private int id;
    private int tripId;
    private LocalDateTime timeSeries;
    private String gpsTrajectory;
    private float speedTrajectory;
    private float altitudeTrajectory;
}
