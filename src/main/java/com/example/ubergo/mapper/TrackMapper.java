package com.example.ubergo.mapper;


import com.example.ubergo.entity.Person;
import com.example.ubergo.entity.Track;
import io.lettuce.core.tracing.TraceContext;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Component
@Mapper
public interface TrackMapper {
    @Select("SELECT * FROM track WHERE id=#{track_id}")
    Track getById(Long track_id);


    @Insert("INSERT INTO track (ride_id, time_series, gps_trajectory, speed_trajectory, altitude_trajectory) " +
            "VALUES (#{rideId}, #{timeSeries}, #{gpsTrajectory}, #{speedTrajectory}, #{altitudeTrajectory})")
    void insertTrack(Track track);

    @Update({ "<script>",
            "UPDATE track " ,
            "<set>"
            , "<if test='rideId != null'>ride_id = #{rideId}, </if>"
            , "<if test='timeSeries != null'>time_series = #{timeSeries}, </if>"
            , "<if test='gpsTrajectory != null'>gps_trajectory = #{gpsTrajectory}, </if>"
            , "<if test='speedTrajectory != null'>speed_trajectory = #{speedTrajectory}, </if>"
            , "<if test='altitudeTrajectory != null'>altitude_trajectory = #{altitudeTrajectory}, </if>"
            ,"</set>"
            , "WHERE ride_id = #{rideId}",
            "</script>"
            })
    void updateTrack(Track track);

    @Select("SELECT * FROM track WHERE ride_id = #{rideId}")
    Track getByRideId(Long rideId);

    @Select("SELECT COUNT(*) FROM track WHERE ride_id = #{rid}")
    int countByRideId(Long rid);
}
