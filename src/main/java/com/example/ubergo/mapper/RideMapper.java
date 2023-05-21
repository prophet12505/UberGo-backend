package com.example.ubergo.mapper;


import com.example.ubergo.entity.Person;
import com.example.ubergo.entity.Ride;
import com.example.ubergo.entity.User;

import org.apache.ibatis.annotations.*;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.ubergo.utils.Constants.CREATED;
import static com.example.ubergo.utils.Constants.UNPAID;

@Repository
@Component
@Mapper
public interface RideMapper {
    @Select("SELECT * FROM ride WHERE id = #{ID}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "creation_time", property = "creationTime"),
            @Result(column = "passenger_uid", property = "passengerUid"),
            @Result(column = "driver_uid", property = "driverUid"),
            @Result(column = "channel", property = "channel"),
            @Result(column = "type", property = "type"),
            @Result(column = "pick_up_lat", property = "pickUpLat"),
            @Result(column = "pick_up_long", property = "pickUpLong"),
            @Result(column = "pick_up_resolved_address", property = "pickUpResolvedAddress"),
            @Result(column = "dest_lat", property = "destLat"),
            @Result(column = "dest_long", property = "destLong"),
            @Result(column = "dest_resolved_address", property = "destResolvedAddress"),
            @Result(column = "status", property = "status"),
            @Result(column = "order_take_time", property = "orderTakeTime"),
            @Result(column = "pick_up_time", property = "pickUpTime"),
            @Result(column = "arrival_time", property = "arrivalTime"),
            @Result(column = "cancel_time", property = "cancelTime"),
            @Result(column = "total_length", property = "totalLength"),
            @Result(column = "itinerary_order_id", property = "itineraryOrderId"),
            @Result(column = "alarm_status", property = "alarmStatus"),
            @Result(column = "after_sales_status", property = "afterSalesStatus"),
            @Result(column = "trip_evaluation_score", property = "tripEvaluationScore"),
            @Result(column = "itinerary_evaluation_content", property = "itineraryEvaluationContent")
    })
    Ride getById(Long ID);

    @Insert("INSERT INTO ride ( passenger_uid, driver_uid, channel, type, pick_up_lat, pick_up_long, pick_up_resolved_address, dest_lat, dest_long, dest_resolved_address, status, order_take_time, pick_up_time, arrival_time, cancel_time, total_length, itinerary_order_id, alarm_status, after_sales_status, trip_evaluation_score, itinerary_evaluation_content) "
            + "VALUES ( #{passengerUid}, NULL, #{channel}, #{type}, #{pickUpLat}, #{pickUpLong}, #{pickUpResolvedAddress}, #{destLat}, #{destLong}, #{destResolvedAddress}, '"+CREATED+"', #{orderTakeTime}, #{pickUpTime}, #{arrivalTime}, #{cancelTime}, #{totalLength}, #{itineraryOrderId}, #{alarmStatus}, #{afterSalesStatus}, #{tripEvaluationScore}, #{itineraryEvaluationContent})")
    void createARide(Ride ride);

    @Select("SELECT MAX(id) FROM ride;")
    Integer selectLargestRideId();

    @Cacheable(value = "ridCache")
    @Select("SELECT AUTO_INCREMENT FROM information_schema.TABLES WHERE TABLE_SCHEMA = 'ubergo' AND TABLE_NAME = 'ride'")
    Integer getNextRideId();

    @Update({
            "<script>",
            "UPDATE ride ",
            "<set>"
            , "<if test='passengerUid != null'>passenger_uid = #{passengerUid}, </if>"
            , "<if test='driverUid != null'>driver_uid = #{driverUid}, </if>"
            , "<if test='channel != null'>channel = #{channel}, </if>"
            , "<if test='type != null'>type = #{type}, </if>"
            , "<if test='pickUpLat != null'>pick_up_lat = #{pickUpLat}, </if>"
            , "<if test='pickUpLong != null'>pick_up_long = #{pickUpLong}, </if>"
            , "<if test='pickUpResolvedAddress != null'>pick_up_resolved_address = #{pickUpResolvedAddress}, </if>"
            , "<if test='destLat != null'>dest_lat = #{destLat}, </if>"
            , "<if test='destLong != null'>dest_long = #{destLong}, </if>"
            , "<if test='destResolvedAddress != null'>dest_resolved_address = #{destResolvedAddress}, </if>"
            , "<if test='status != null'>status = #{status}, </if>"
            , "<if test='orderTakeTime != null'>order_take_time = #{orderTakeTime}, </if>"
            , "<if test='pickUpTime != null'>pick_up_time = #{pickUpTime}, </if>"
            , "<if test='arrivalTime != null'>arrival_time = #{arrivalTime}, </if>"
            , "<if test='cancelTime != null'>cancel_time = #{cancelTime}, </if>"
            , "<if test='totalLength != null'>total_length = #{totalLength}, </if>"
            , "<if test='itineraryOrderId != null'>itinerary_order_id = #{itineraryOrderId}, </if>"
            , "<if test='alarmStatus != null'>alarm_status = #{alarmStatus}, </if>"
            , "<if test='afterSalesStatus != null'>after_sales_status = #{afterSalesStatus}, </if>"
            , "<if test='tripEvaluationScore != null'>trip_evaluation_score = #{tripEvaluationScore}, </if>"
            , "<if test='itineraryEvaluationContent != null'>itinerary_evaluation_content = #{itineraryEvaluationContent}, </if>"
            ,"</set>"
            , "WHERE id = #{id}",
            "</script>"
    })
    void updateRide(Ride ride);


    @Select("SELECT * FROM ride WHERE passenger_uid = #{uid}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "creation_time", property = "creationTime"),
            @Result(column = "passenger_uid", property = "passengerUid"),
            @Result(column = "driver_uid", property = "driverUid"),
            @Result(column = "channel", property = "channel"),
            @Result(column = "type", property = "type"),
            @Result(column = "pick_up_lat", property = "pickUpLat"),
            @Result(column = "pick_up_long", property = "pickUpLong"),
            @Result(column = "pick_up_resolved_address", property = "pickUpResolvedAddress"),
            @Result(column = "dest_lat", property = "destLat"),
            @Result(column = "dest_long", property = "destLong"),
            @Result(column = "dest_resolved_address", property = "destResolvedAddress"),
            @Result(column = "status", property = "status"),
            @Result(column = "order_take_time", property = "orderTakeTime"),
            @Result(column = "pick_up_time", property = "pickUpTime"),
            @Result(column = "arrival_time", property = "arrivalTime"),
            @Result(column = "cancel_time", property = "cancelTime"),
            @Result(column = "total_length", property = "totalLength"),
            @Result(column = "itinerary_order_id", property = "itineraryOrderId"),
            @Result(column = "alarm_status", property = "alarmStatus"),
            @Result(column = "after_sales_status", property = "afterSalesStatus"),
            @Result(column = "trip_evaluation_score", property = "tripEvaluationScore"),
            @Result(column = "itinerary_evaluation_content", property = "itineraryEvaluationContent")
    })
    List<Ride> getRideByPassengerId(Long uid);

}