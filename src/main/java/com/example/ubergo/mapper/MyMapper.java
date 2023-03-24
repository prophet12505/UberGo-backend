package com.example.ubergo.mapper;


import com.example.ubergo.entity.Person;
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