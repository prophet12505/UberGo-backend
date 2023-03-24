package com.example.ubergo;

import com.example.ubergo.factory.MqttFactory;
import com.example.ubergo.util.MqttUtil;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UberGoMQTTSubscribeTest implements IMqttMessageListener {
    static Logger LOGGER = LoggerFactory.getLogger(MqttFactory.class);
    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
        LOGGER.info(String.format("MQTT: 订阅主题[%s]发来消息[%s]", topic, new String(mqttMessage.getPayload())));
    }

    public static void main(String[] args) {

        MqttUtil.subscribe("test01", new UberGoMQTTSubscribeTest());
        SpringApplication.run(UberGoMQTTSubscribeTest.class, args);

    }


}
