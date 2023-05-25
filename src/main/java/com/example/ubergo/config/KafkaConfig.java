package com.example.ubergo.config;

import com.example.ubergo.entity.Log;
import com.example.ubergo.mapper.LogMapper;
import com.example.ubergo.service.LogService;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;
    @Value("${spring.kafka.consumer.group-id}")
    private String consumerGroupId;

    static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(KafkaConfig.class);

    @Autowired
    private LogMapper logMapper;

    @Bean
    public ProducerFactory<String, Log> producerFactory() {
        // Define and configure the producer factory
        Map<String, Object> properties = new HashMap<>();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);

        // Add any additional properties for the producer

        DefaultKafkaProducerFactory<String, Log> producerFactory = new DefaultKafkaProducerFactory<>(properties);
        producerFactory.setKeySerializer(new StringSerializer()); // Set the key serializer
        producerFactory.setValueSerializer(new JsonSerializer<>()); // Set the value serializer

        return producerFactory;
    }

    @Bean
    public KafkaTemplate<String, Log> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }


    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Log> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Log> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setConcurrency(1); // Set the desired concurrency level
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
        return factory;
    }

    @Bean
    public ConsumerFactory<String, Log> consumerFactory() {
        // Define and configure the consumer factory
        Map<String, Object> properties = new HashMap<>();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroupId);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        //add entity to trusted packages
        properties.put(JsonDeserializer.TRUSTED_PACKAGES, "com.example.ubergo.entity");

        return new DefaultKafkaConsumerFactory<>(properties);
    }

    @KafkaListener(topics = "uberGo-logService-1-createALog")
    public void receiveMessage(Log log) {
        // Process the received message
        try{
            logMapper.createALog(log);
        }
        catch (Exception e){
            LOGGER.error(e.getMessage());
        }

    }
}
