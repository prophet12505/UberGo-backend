package com.example.ubergo.service;

import com.example.ubergo.DTO.RestDTO.ArriveReqDTO;
import com.example.ubergo.DTO.RestDTO.CancelRideReqDTO;
import com.example.ubergo.DTO.RestDTO.GeneralMessageDTO;
import com.example.ubergo.config.MqttPushClient;
import com.example.ubergo.config.MqttSubClient;
import com.example.ubergo.entity.Track;
import com.example.ubergo.mapper.TrackMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RideTrackingService {
    static Logger LOGGER = LoggerFactory.getLogger(RideTrackingService.class);
    private final TrackMapper trackMapper;
    @Autowired
    private MqttPushClient mqttPushClient;
    @Autowired
    private MqttSubClient mqttSubClient;

    public RideTrackingService(TrackMapper trackMapper) {
        this.trackMapper = trackMapper;
    }


    public String updateATrack(Track track){
        try{
            int trackCount = trackMapper.countByRideId(track.getRideId());
            if(trackCount == 0)
            {
                LOGGER.info("Created a new track record");
                trackMapper.insertTrack(track);
            }
            else{
                LOGGER.info("Updated a track record");
                trackMapper.updateTrack(track);
            }

            return null;
        }
        catch (Exception e){
            LOGGER.error(e.getMessage());
            return  e.getMessage();
        }
    }
    public void monitorItinerary(String trackChannel){
        try{
            //subscribe the channel
            mqttSubClient.subscribe(trackChannel,2);
            LOGGER.info("Tracking Module: Subscribe track channel:"+trackChannel);

        }
        catch (Exception e){
            e.printStackTrace();

        }
    }
    //if the ride is arriving, cancel it through cancel arriving service
    public GeneralMessageDTO cancelPickedUpRide( Long rid,  CancelRideReqDTO cancelRideReqDTO) {
        try{
            //
            return new GeneralMessageDTO(0,"Success");
        }
        catch (Exception e){
            LOGGER.error(e.getMessage());
            return  new GeneralMessageDTO(599,e.getMessage());
        }

    }

}
