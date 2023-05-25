package com.example.ubergo.controller;

import com.example.ubergo.config.MqttPushClient;
import com.example.ubergo.entity.Person;
import com.example.ubergo.service.HelloService;
import com.example.ubergo.service.LogService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private final HelloService helloService;
    
    @Autowired
    private MqttPushClient mqttPushClient;

    @Autowired
    private LogService logService;

    @Autowired
    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(LogService.class);

    @RequestMapping("/")
    public String Hello(){
        try{
            this.logService.createALog("Hello Module","INFO","this is a test log");
        }
       catch (Exception e){
           LOGGER.error(e.getMessage());
       }
//        Person person=helloService.getPersonById(1);
//        mqttPushClient.publish("test01","{\"message\":\"this is a test message\",\"qos\":2}",2);
        return "hello, ";
    }




}
