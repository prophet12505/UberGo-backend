package com.example.ubergo.controller;

import com.example.ubergo.DTO.ConfirmPaymentReqDTO;
import com.example.ubergo.DTO.CreateAnOrderReqDTO;
import com.example.ubergo.DTO.GeneralMessageDTO;
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
    public GeneralMessageDTO getOrderInfo(@PathVariable Integer oid,@RequestParam(value="uid") Integer uid){
            return orderCheckoutService.getOrderInfo(oid,uid);

    }

    @RequestMapping(value = "/order/{oid}/createPaymentRequest",method = RequestMethod.PUT)
    public GeneralMessageDTO createPaymentRequest(@PathVariable Integer oid,@RequestBody CreateAnOrderReqDTO createAnOrderReqDTO){
        return orderCheckoutService.createPaymentRequest(oid,createAnOrderReqDTO);

    }
    @RequestMapping(value = "/order/{oid}/confirmPayment",method = RequestMethod.PUT)
    public GeneralMessageDTO confirmPayment(@PathVariable Integer oid,@RequestBody ConfirmPaymentReqDTO confirmPaymentReqDTO){
        return new GeneralMessageDTO(0,"Success");
    }
}
