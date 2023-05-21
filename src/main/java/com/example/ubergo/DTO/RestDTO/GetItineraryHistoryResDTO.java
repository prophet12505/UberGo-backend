package com.example.ubergo.DTO.RestDTO;

import com.example.ubergo.entity.Ride;
import com.example.ubergo.entity.Track;
import com.example.ubergo.entity.User;
import lombok.Data;

@Data
public class GetItineraryHistoryResDTO {
    private Ride ride;
    private User driver;
    private Track track;

}
