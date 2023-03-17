package com.example.mqttlearn1.mapper;


import com.example.mqttlearn1.entity.Person;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MyMapper {
    @Select("SELECT * FROM person WHERE id = #{id}")
    Person getById(int id);

    @Select("SELECT * FROM person")
    List<Person> getAllPersons();
}