package com.example.ubergo.DTO.MqttDTO;

import lombok.Data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

@Data
public class MqttMessageWrapperDTO {

    private String action;
    private Object payload;

    public MqttMessageWrapperDTO() {
    }

    public MqttMessageWrapperDTO(String action, Object payload) {
        this.action = action;
        this.payload = payload;
    }

    public MqttMessageWrapperDTO(String jsonString) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            MqttMessageWrapperDTO dto = objectMapper.readValue(jsonString, MqttMessageWrapperDTO.class);
            this.action = dto.getAction();
            this.payload = dto.getPayload();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}

