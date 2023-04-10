package com.example.ubergo.mapper;


import com.example.ubergo.entity.OrderForm;
import com.example.ubergo.entity.Person;
import com.example.ubergo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Component
@Mapper
public interface OrderFormMapper {

    @Select("SELECT * FROM order_form WHERE ride_id IN (SELECT id FROM ride WHERE passenger_uid = #{passengerUid}) AND status = 'UNPAID'")
    OrderForm selectUnpaidUserById(int passengerUid);

}