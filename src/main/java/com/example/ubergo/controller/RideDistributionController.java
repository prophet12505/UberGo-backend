package com.example.ubergo.controller;

import com.example.ubergo.DTO.RestDTO.*;
import com.example.ubergo.service.RideDistributionService;
import org.springframework.web.bind.annotation.*;

@RestController
public class RideDistributionController {
    private final RideDistributionService rideDistributionService;

    public RideDistributionController(RideDistributionService rideDistributionService) {
        this.rideDistributionService = rideDistributionService;
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

    @RequestMapping(value = "/ride/{rid}",method = RequestMethod.GET)
    public GeneralMessageDTO getRideInfo(@PathVariable long rid, @RequestParam(value = "long") Double longitide, @RequestParam(value = "lat") Double latitude){
        //I haven't made use of location given
        LocationDTO locationDTO=new LocationDTO(longitide,latitude);
        return rideDistributionService.getRiderInfo(rid,locationDTO);
    }

}
