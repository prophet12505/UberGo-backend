package com.example.mqttlearn1;

import com.example.mqttlearn1.factory.MqttFactory;
import com.example.mqttlearn1.util.MqttUtil;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UberGoMQTTPublishTest implements IMqttMessageListener {
    static Logger LOGGER = LoggerFactory.getLogger(MqttFactory.class);
    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
        LOGGER.info(String.format("MQTT: 订阅主题[%s]发来消息[%s]", topic, new String(mqttMessage.getPayload())));
    }

    public static void main(String[] args) {
        MqttUtil.send("test01", "hey bro");
        SpringApplication.run(UberGoMQTTPublishTest.class, args);
        while (true) {
            System.out.println("Hello, world!");
            MqttUtil.send("test01", "hey bro");
            try {
                Thread.sleep(3000); // Sleep for 3 seconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


}
