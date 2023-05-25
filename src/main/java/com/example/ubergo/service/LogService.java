package com.example.ubergo.service;

import com.example.ubergo.entity.Log;
import com.example.ubergo.mapper.LogMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LogService {
    static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(LogService.class);
    private LogMapper logMapper;
    private KafkaTemplate<String, Log> kafkaTemplate;



    public LogService(LogMapper logMapper,KafkaTemplate<String, Log> kafkaTemplate) {
        this.logMapper = logMapper;
        this.kafkaTemplate=kafkaTemplate;
    }

    public void createALog(String sourceModule, String level,String logContent){
        try{
            Log log =new Log(sourceModule,level,logContent);
            kafkaTemplate.send("uberGo-logService-1-createALog",log);
        }
        catch (Exception e){
            LOGGER.error(e.getMessage());
        }

    }
}
