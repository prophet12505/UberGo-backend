package com.example.mqttlearn1.controller;

import com.example.mqttlearn1.entity.Person;
import com.example.mqttlearn1.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HelloController {

    private final HelloService helloService;
    @Autowired
    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    @RequestMapping("/")
    public String Hello(){
        Person person=helloService.getPersonById(1);
        return "hello, "+person.getName();
    }
}
