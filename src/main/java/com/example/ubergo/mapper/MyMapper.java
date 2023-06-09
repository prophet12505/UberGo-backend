package com.example.ubergo.mapper;


import com.example.ubergo.entity.Person;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Component
@Mapper
public interface MyMapper {
    @Select("SELECT * FROM person WHERE id = #{id}")
    Person getById(int id);

    @Select("SELECT * FROM person")
    List<Person> getAllPersons();




    
}