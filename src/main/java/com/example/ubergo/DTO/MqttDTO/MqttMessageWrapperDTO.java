package com.example.ubergo.DTO.MqttDTO;

import lombok.Data;

@Data
public class MqttMessageWrapperDTO {

    private String action;
    private Object payload;
    public MqttMessageWrapperDTO(String action, Object payload) {
        this.action = action;
        this.payload = payload;
    }

}
