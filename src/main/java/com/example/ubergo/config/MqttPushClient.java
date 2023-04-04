package com.example.ubergo.config;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MqttPushClient{

    @Autowired
    private PushCallback pushCallback;

    private static MqttClient client;

    private int publishQos;


    private int subscribeQos;

    public int getSubscribeQos() {
        return subscribeQos;
    }


    public static void setClient(MqttClient client) {
        MqttPushClient.client = client;
    }

    public static MqttClient getClient() {
        return client;
    }

    public void connect(String host, String clientID, String username, String password, int timeout, int keepalive,int publishQos,int subscribeQos) {
        MqttClient client;
        this.publishQos=publishQos;
        this.subscribeQos=subscribeQos;
        try {
            client = new MqttClient(host, clientID, new MemoryPersistence());
            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(true);
            options.setUserName(username);
            options.setPassword(password.toCharArray());
            options.setConnectionTimeout(timeout);
            options.setKeepAliveInterval(keepalive);

            MqttPushClient.setClient(client);
            try {
                //set up callback object
                client.setCallback(pushCallback);
                //client.connect(options);
                IMqttToken iMqttToken = client.connectWithResult(options);
                boolean complete = iMqttToken.isComplete();
                log.info("MQTT connected to "+(complete?"succeed":"failed"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * publish，default qos is 0，non persistence
     *
     * @param topic
     * @param pushMessage
     */
    public void publish(String topic, String pushMessage) {
        publish(this.publishQos, false, topic, pushMessage);
    }
    public void publish(String topic, String pushMessage,int publishQos) {
        publish(publishQos, false, topic, pushMessage);
    }

    /**
     * publish
     *
     * @param qos
     * @param retained
     * @param topic
     * @param pushMessage
     */
    public void publish(int qos, boolean retained, String topic, String pushMessage) {
        MqttMessage message = new MqttMessage();
        message.setQos(qos);
        message.setRetained(retained);
        message.setPayload(pushMessage.getBytes());
        MqttTopic mTopic = MqttPushClient.getClient().getTopic(topic);
        if (null == mTopic) {
            log.error("topic doesn't exist:{}",mTopic);
        }
        try {
            mTopic.publish(message);
        } catch (Exception e) {
            log.error("error while sending mqtt message:",e);
        }
    }

}