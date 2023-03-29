//package com.example.ubergo.config;
//import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.integration.annotation.IntegrationComponentScan;
//import org.springframework.integration.annotation.ServiceActivator;
//import org.springframework.integration.channel.DirectChannel;
//import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
//import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
//import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
//import org.springframework.integration.mqtt.support.DefaultMqttHeaderMapper;
//import org.springframework.integration.mqtt.support.MqttHeaderMapper;
//import org.springframework.messaging.MessageHandler;
//
//@Configuration
//@IntegrationComponentScan
//public class MqttConfig {
//
//    @Value("${mqtt.broker.url}")
//    private String brokerUrl;
//
//    @Value("${mqtt.broker.username}")
//    private String username;
//
//    @Value("${mqtt.broker.password}")
//    private String password;
//
//    @Value("${mqtt.topic}")
//    private String topic;
//
//    @Bean
//    public MqttConnectOptions mqttConnectOptions() {
//        MqttConnectOptions options = new MqttConnectOptions();
//        options.setUserName(username);
//        options.setPassword(password.toCharArray());
//        return options;
//    }
//
//    @Bean
//    public MqttPahoClientFactory mqttClientFactory() {
//        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
//        factory.setConnectionOptions(mqttConnectOptions());
//        return factory;
//    }
//
//    @Bean
//    public DirectChannel mqttInputChannel() {
//        return new DirectChannel();
//    }
//
//    @Bean
//    public MqttHeaderMapper mqttHeaderMapper() {
//        return new DefaultMqttHeaderMapper();
//    }
//
//    @Bean
//    public MqttPahoMessageDrivenChannelAdapter mqttInbound() {
//        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(
//                brokerUrl,
//                "spring-boot-mqtt",
//                mqttClientFactory(),
//                topic
//        );
//        adapter.setCompletionTimeout(5000);
//        adapter.setConverter(new JsonMessageConverter());
//        adapter.setQos(1);
//        adapter.setOutputChannel(mqttInputChannel());
//        adapter.setHeaderMapper(mqttHeaderMapper());
//        return adapter;
//    }
//
//    @Bean
//    @ServiceActivator(inputChannel = "mqttInputChannel")
//    public MessageHandler mqttHandler() {
//        return new MqttMessageHandler();
//    }
//
//}
