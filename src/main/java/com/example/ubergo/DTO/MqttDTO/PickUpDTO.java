package com.example.ubergo.DTO.MqttDTO;

import com.example.ubergo.entity.Ride;
import com.example.ubergo.entity.User;
import lombok.Data;

@Data
public class PickUpDTO {
    private Ride ride;
    private User user;
    public PickUpDTO(Ride ride, User user) {
        this.ride = ride;
        this.user = user;
    }
}
