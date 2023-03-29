package com.example.ubergo.factory;

import com.example.ubergo.util.MqttProperties;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MqttFactory {
    private static MqttProperties configuration=new MqttProperties();

    private static MqttClient client;
    static Logger LOGGER = LoggerFactory.getLogger(MqttFactory.class);
    /**
     *   获取客户端实例
     *   单例模式, 存在则返回, 不存在则初始化
     */
    public static MqttClient getInstance() {   
        if (client == null) {     
            init();   
        }   
        return client; 
    } 

    /**
     *   初始化客户端
     */
    public static void init() {   
        try {     
            client = new MqttClient(configuration.getAddress(), "client-" + System.currentTimeMillis());     
            // MQTT configuration object
            MqttConnectOptions options = new MqttConnectOptions();
            // automatic connection is true
            options.setAutomaticReconnect(true);

            if (!client.isConnected()) {       
                client.connect(options);     
            }   
        } catch (MqttException e) {
            LOGGER.error(String.format("MQTT: Connect to message server [%s] failed", configuration.getAddress()));
        } 
    }


}
