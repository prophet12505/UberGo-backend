package com.example.ubergo.controller;

import com.example.ubergo.DTO.CreateARideReqDTO;
import com.example.ubergo.DTO.GeneralMessageDTO;
import com.example.ubergo.DTO.TakeOrderReqDTO;
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

    @RequestMapping(value = "/ride/{rid}",method = RequestMethod.PUT)
    public GeneralMessageDTO takeOrder(@PathVariable int rid, @RequestBody TakeOrderReqDTO takeOrderReqDTO){
        return rideDistributionService.takeOrder(rid,takeOrderReqDTO);
    }

}
