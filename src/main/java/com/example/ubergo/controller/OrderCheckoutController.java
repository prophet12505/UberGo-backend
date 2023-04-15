package com.example.ubergo.controller;

import com.example.ubergo.DTO.RestDTO.ConfirmPaymentReqDTO;
import com.example.ubergo.DTO.RestDTO.CreateAnOrderReqDTO;
import com.example.ubergo.DTO.RestDTO.GeneralMessageDTO;
import com.example.ubergo.service.OrderCheckoutService;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderCheckoutController {
    private final OrderCheckoutService orderCheckoutService;

    public OrderCheckoutController(OrderCheckoutService orderCheckoutService) {
        this.orderCheckoutService = orderCheckoutService;
    }

    @RequestMapping(value = "/order",method = RequestMethod.POST)
    public GeneralMessageDTO createAnOrder(@RequestBody CreateAnOrderReqDTO createAnOrderReqDTO){
        return orderCheckoutService.createAnOrder(createAnOrderReqDTO.getRid());

    }

    @RequestMapping(value = "/order/{oid}",method = RequestMethod.GET)
    public GeneralMessageDTO getOrderInfo(@PathVariable Long oid,@RequestParam(value="uid") Long uid){
            return orderCheckoutService.getOrderInfo(oid,uid);

    }

    @RequestMapping(value = "/order/{oid}/createPaymentRequest",method = RequestMethod.PUT)
    public GeneralMessageDTO createPaymentRequest(@PathVariable Long oid,@RequestBody CreateAnOrderReqDTO createAnOrderReqDTO){
        return orderCheckoutService.createPaymentRequest(oid,createAnOrderReqDTO);

    }
    @RequestMapping(value = "/order/{oid}/confirmPayment",method = RequestMethod.PUT)
    public GeneralMessageDTO confirmPayment(@PathVariable Long oid,@RequestBody ConfirmPaymentReqDTO confirmPaymentReqDTO){
        return new GeneralMessageDTO(0,"Success");
    }
}
