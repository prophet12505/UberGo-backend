package com.example.ubergo.controller;

import com.example.ubergo.DTO.MqttDTO.PickUpDTO;
import com.example.ubergo.DTO.RestDTO.*;
import com.example.ubergo.entity.Track;
import com.example.ubergo.service.RideDistributionService;
import com.example.ubergo.service.RideTrackingService;
import org.springframework.web.bind.annotation.*;

@RestController
public class RideDistributionController {
    private final RideDistributionService rideDistributionService;
    private final RideTrackingService rideTrackingService;

    public RideDistributionController(RideDistributionService rideDistributionService, RideTrackingService rideTrackingService) {
        this.rideDistributionService = rideDistributionService;
        this.rideTrackingService = rideTrackingService;
    }

    @RequestMapping(value = "/ride",method = RequestMethod.POST)
    public GeneralMessageDTO CreateARide(@RequestBody CreateARideReqDTO createARideReqDTO){
        return rideDistributionService.createARide(createARideReqDTO);
    }

    @RequestMapping(value = "/ride/{rid}/takeOrder",method = RequestMethod.PUT)
    public GeneralMessageDTO takeOrder(@PathVariable long rid, @RequestBody TakeOrderReqDTO takeOrderReqDTO){
        return rideDistributionService.takeOrder(rid,takeOrderReqDTO);
    }

    @RequestMapping(value = "/ride/{rid}/cancel",method = RequestMethod.PUT)
    public GeneralMessageDTO cancelRide(@PathVariable long rid, @RequestBody CancelRideReqDTO cancelRideReqDTO){
        return rideDistributionService.cancelRide(rid,cancelRideReqDTO);
    }

    @RequestMapping(value = "/ride/{rid}/pickUpPassenger",method = RequestMethod.PUT)
    public GeneralMessageDTO pickUpPassenger(@RequestBody PickUpDTO pickUpDTO){
        return rideDistributionService.pickUpPassenger(pickUpDTO);
    }
    @RequestMapping(value = "/ride/{rid}/arrive",method = RequestMethod.PUT)
    public GeneralMessageDTO arrive(@PathVariable long rid, @RequestBody Track track){
        //msg hasn't been used, will do the error handling later on
        String msg=rideTrackingService.updateATrack(track);
        return rideDistributionService.arrive(rid);
    }

    @RequestMapping(value = "/ride/{rid}",method = RequestMethod.GET)
    public GeneralMessageDTO getRideInfo(@PathVariable long rid, @RequestParam(value = "long") Double longitide, @RequestParam(value = "lat") Double latitude){
        //I haven't made use of location given
        LocationDTO locationDTO=new LocationDTO(longitide,latitude);
        return rideDistributionService.getRiderInfo(rid,locationDTO);
        //

    }
    @RequestMapping(value = "/ride/{uid}/history",method = RequestMethod.GET)
    public GeneralMessageDTO getItineraryHistory(@PathVariable Long  uid) {
        return rideDistributionService.getItineraryHistory(uid);
    }
}
