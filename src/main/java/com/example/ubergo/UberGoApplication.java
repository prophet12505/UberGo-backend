package com.example.ubergo;


import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class UberGoApplication  {
    static Logger LOGGER = LoggerFactory.getLogger(UberGoApplication.class);


    public static void main(String[] args) {
        //MqttUtil.subscribe("test01", new UberGoApplication());
        SpringApplication.run(UberGoApplication.class, args);
    }
}
