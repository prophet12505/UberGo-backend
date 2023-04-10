package com.example.ubergo.config;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class MqttSubClient {
    private int subscribeQos;
    public MqttSubClient(MqttPushClient mqttPushClient){
        this.subscribeQos=mqttPushClient.getSubscribeQos();
        subScribeDataPublishTopic();
    }


    private void subScribeDataPublishTopic(){
        //subscribe test01 topic, for default testing purpose
       subscribe("test01");
    }

    /**
     * subscribe a topic，qos is 0 by default
     *
     * @param topic
     */
    public void subscribe(String topic) {

        subscribe(topic, this.subscribeQos);
    }
//    public void subscribe(String topic,int subscribeQos) {
//
//        subscribe(topic, subscribeQos);
//    }

    /**
     * subscribe a certain topic
     *
     * @param topic
     * @param qos
     */
    public void subscribe(String topic, int qos) {
        try {
            MqttClient client = MqttPushClient.getClient();
            if (client == null) return;
            client.subscribe(topic, qos);
            log.info("subscribed topic :{}",topic);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

}
