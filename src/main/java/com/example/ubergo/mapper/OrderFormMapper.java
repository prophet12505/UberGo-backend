package com.example.ubergo.mapper;


import com.example.ubergo.entity.OrderForm;
import com.example.ubergo.entity.Person;
import com.example.ubergo.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.ubergo.utils.Constants.UNPAID;

@Repository
@Component
@Mapper
public interface OrderFormMapper {

    @Select("SELECT * FROM order_form WHERE id = #{id}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "ride_id", property = "rideId"),
            @Result(column = "creation_time", property = "creationTime"),
            @Result(column = "total_price", property = "totalPrice"),
            @Result(column = "starting_price", property = "startingPrice"),
            @Result(column = "tour_fees", property = "tourFees"),
            @Result(column = "fuel_costs", property = "fuelCosts"),
            @Result(column = "time_fee", property = "timeFee"),
            @Result(column = "special_location_fee", property = "specialLocationFee"),
            @Result(column = "dynamic_prices", property = "dynamicPrices"),
            @Result(column = "status", property = "status"),
            @Result(column = "payment_platform", property = "paymentPlatform"),
            @Result(column = "payment_serial_number", property = "paymentSerialNumber"),
            @Result(column = "payment_result", property = "paymentResult")
    })
    OrderForm getById(Long id);

    @Select("SELECT * FROM order_form WHERE ride_id IN (SELECT id FROM ride WHERE passenger_uid = #{passengerUid}) AND status = 'UNPAID'")
    OrderForm selectUnpaidUserById(Long passengerUid);

    @Insert("INSERT INTO `order_form` (ride_id,total_price, starting_price, tour_fees, fuel_costs, time_fee, special_location_fee, dynamic_prices, status, payment_platform, payment_serial_number, payment_result) " +
            "VALUES (#{rideId}, #{totalPrice}, #{startingPrice}, #{tourFees}, #{fuelCosts}, #{timeFee}, #{specialLocationFee}, #{dynamicPrices}, '"+UNPAID+"', #{paymentPlatform}, #{paymentSerialNumber}, #{paymentResult})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void createAnOrder(OrderForm order);

    @Select("SELECT AUTO_INCREMENT FROM information_schema.TABLES WHERE TABLE_SCHEMA = 'ubergo' AND TABLE_NAME = 'order_form'")
    Integer getNextOrderId();
}