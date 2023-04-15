package com.example.ubergo.mapper;


import com.example.ubergo.entity.Person;
import com.example.ubergo.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Component
@Mapper
public interface UserMapper {
    @Select("SELECT * FROM user WHERE uid = #{UID}")
    @Results({
            @Result(column = "mobile_number", property = "mobileNumber"),
            @Result(column = "city", property = "city"),
            @Result(column = "regional_province", property = "regionalProvince")
    })
    User getById(Long UID);

    @Select("SELECT * FROM user WHERE mobile_number = #{mobileNumber}")
    @Results({
            @Result(column = "mobile_number", property = "mobileNumber"),
            @Result(column = "city", property = "city"),
            @Result(column = "regional_province", property = "regionalProvince")
    })
    User getByMobileNumber(String mobileNumber);

    @Insert("INSERT INTO user(mobile_number, identity, password, user_name, license_plate_number, car_type, total_trip_length, regional_province, city) " +
            "VALUES (#{mobileNumber}, #{identity}, #{password}, #{userName}, #{licensePlateNumber}, #{carType}, #{totalTripLength}, #{regionalProvince}, #{city})")
    @Options(useGeneratedKeys = true, keyProperty = "uid")
    int createAUser(
            User user
    );

//    @Select("SELECT * FROM user WHERE mobile_number = #{mobileNumber}")
//    User getUserByMobileNumber(String mobileNumber);

    @Update({
            "<script>",
            "UPDATE user",
            "<set>",
            "  <if test='mobileNumber != null'>mobile_number = #{mobileNumber},</if>",
            "  <if test='identity != null'>identity = #{identity},</if>",
            "  <if test='password != null'>password = #{password},</if>",
            "  <if test='userName != null'>user_name = #{userName},</if>",
            "  <if test='licensePlateNumber != null'>license_plate_number = #{licensePlateNumber},</if>",
            "  <if test='carType != null'>car_type = #{carType},</if>",
            "  <if test='totalTripLength != null'>total_trip_length = #{totalTripLength},</if>",
            "  <if test='regionalProvince != null'>regional_province = #{regionalProvince},</if>",
            "  <if test='city != null'>city = #{city},</if>",
            "</set>",
            "WHERE UID = #{uid}",
            "</script>"
    })
    void updateUser(
            User user );



}