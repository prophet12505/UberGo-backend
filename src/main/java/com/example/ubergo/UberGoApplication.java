package com.example.ubergo;


import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UberGoApplication implements IMqttMessageListener {
    static Logger LOGGER = LoggerFactory.getLogger(UberGoApplication.class);

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
        LOGGER.info(String.format("MQTT: topic[%s] has sent message [%s]", topic, new String(mqttMessage.getPayload())));
    }
    public static void main(String[] args) {
        //MqttUtil.subscribe("test01", new UberGoApplication());
        SpringApplication.run(UberGoApplication.class, args);

    }
}
