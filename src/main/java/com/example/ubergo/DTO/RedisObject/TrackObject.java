package com.example.ubergo.DTO.RedisObject;

import com.example.ubergo.DTO.MqttDTO.LocationShorthandDTO;
import com.example.ubergo.DTO.MqttDTO.TrackUpdateDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
//@NoArgsConstructor
public class TrackObject implements Serializable {
    private Long rideId;
    private LocalDateTime timeSeries;

    private List<LocationShorthandDTO> gpsTrajectory;
    //object is to be modified
    private List<Double> speedTrajectory;
    private List<Double> altitudeTrajectory;
    private Double totalLength;
    public TrackObject(Long rideId){
        this.rideId=rideId;
        this.gpsTrajectory=new ArrayList<>();
        this.speedTrajectory=new ArrayList<>();
        this.altitudeTrajectory=new ArrayList<>();
        this.totalLength=0d;
    }

    public void updateTrajectory(TrackUpdateDTO trackUpdateDTO){
        this.timeSeries= trackUpdateDTO.getTimeSeries();
        this.gpsTrajectory.add(trackUpdateDTO.getCurrentGps());
        this.speedTrajectory.add(trackUpdateDTO.getCurrentSpeed());
        this.altitudeTrajectory.add(trackUpdateDTO.getCurrentAltitude());
    }
}
