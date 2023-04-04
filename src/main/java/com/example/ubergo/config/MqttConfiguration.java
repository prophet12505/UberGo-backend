package com.example.ubergo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * mqtt配置类，获取mqtt连接
 */
@Component
@Configuration
@ConfigurationProperties(MqttConfiguration.PREFIX)
public class MqttConfiguration {

    @Autowired
    private MqttPushClient mqttPushClient;
    //prefix in application-local.properties
    public static final String PREFIX = "ubergo.mqtt";
    private String host;
    private String clientId;
    private String userName;
    private String password;
    private String topic;
    private int timeout;
    private int keepAlive;

    private int publishQos;
    private int subscribeQos;




    public String getClientid() {
        return clientId;
    }

    public void setClientid(String clientid) {
        this.clientId = clientid;
    }

    public String getUsername() {
        return userName;
    }

    public void setUsername(String username) {
        this.userName = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getKeepalive() {
        return keepAlive;
    }

    public void setKeepalive(int keepalive) {
        this.keepAlive = keepalive;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
    public int getPublishQos() {
        return publishQos;
    }

    public void setPublishQos(int publishQos) {
        this.publishQos = publishQos;
    }

    public int getSubscribeQos() {
        return subscribeQos;
    }

    public void setSubscribeQos(int subscribeQos) {
        this.subscribeQos = subscribeQos;
    }

    /**
     * 连接至mqtt服务器，获取mqtt连接
     * @return
     */
    @Bean
    public MqttPushClient getMqttPushClient() {
        //connect to mqtt server，obtain mqtt connection
        mqttPushClient.connect(host, clientId, userName, password, timeout, keepAlive,publishQos,subscribeQos);
        //subscribe the topics needed once connected to mqtt（for example : test_queue）
        new MqttSubClient(mqttPushClient);
        return mqttPushClient;
    }
}