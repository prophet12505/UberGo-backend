package com.example.mqttlearn1.util;

import com.example.mqttlearn1.factory.MqttFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

public class MqttUtil {
    /**
     *   发送消息
     *   @param topic 主题
     *   @param data 消息内容
     */
    static Logger LOGGER = LoggerFactory.getLogger(MqttFactory.class);
    public static void send(String topic, Object data) {  
        // create a new client instance
        MqttClient client = MqttFactory.getInstance();
        ObjectMapper mapper = new ObjectMapper();
        try {
            // convert message into JSON String
            String json = mapper.writeValueAsString(data);   
            client.publish(topic, new MqttMessage(json.getBytes(StandardCharsets.UTF_8)));
        } catch (JsonProcessingException e) {
            LOGGER.error(String.format("MQTT: topic [%s] failed to convert to JSON String", topic));
        } catch (MqttException e) {
            LOGGER.error(String.format("MQTT: topic [%s] failed to send message", topic));
        } 
    }

    /**
     * subscribe topic
     * @param topic topic
     * @param listener
     */
    public static void subscribe(String topic, IMqttMessageListener listener) {
        MqttClient client = MqttFactory.getInstance(); 
        try {  
            client.subscribe(topic, listener); 
        } catch (MqttException e) {

            LOGGER.error(String.format("MQTT: failed to subscribe topic", topic));
            LOGGER.error(e.getMessage());
        }
    }


}
