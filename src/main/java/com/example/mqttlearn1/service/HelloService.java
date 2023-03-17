package com.example.mqttlearn1.service;

import com.example.mqttlearn1.entity.Person;
import com.example.mqttlearn1.mapper.MyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HelloService {


    private final MyMapper myMapper;
    @Autowired
    public HelloService(MyMapper myMapper) {
        this.myMapper = myMapper;
    }

    public Person getPersonById(int id){

        return myMapper.getById(id);
    }
    public List<Person> getAllPersons(){
        return myMapper.getAllPersons();
    }
}
