package com.example.ubergo.controller;

import com.example.ubergo.entity.Person;
import com.example.ubergo.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
