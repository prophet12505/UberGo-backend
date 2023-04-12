package com.example.ubergo.service;

import com.example.ubergo.DTO.CreateAnOrderReqDTO;
import com.example.ubergo.DTO.GeneralMessageDTO;
import com.example.ubergo.DTO.GetOrderInfoResDTO;
import com.example.ubergo.entity.OrderForm;
import com.example.ubergo.mapper.OrderFormMapper;
import com.example.ubergo.mapper.RideMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderCheckoutService {
    static Logger LOGGER = LoggerFactory.getLogger(OrderCheckoutService.class);
    private final OrderFormMapper orderFormMapper;
    private final RideMapper rideMapper;

    public OrderCheckoutService(OrderFormMapper orderFormMapper, RideMapper rideMapper) {
        this.orderFormMapper = orderFormMapper;
        this.rideMapper = rideMapper;
    }

    public GeneralMessageDTO createAnOrder(Integer rid){
        try{
            orderFormMapper.createAnOrder(new OrderForm(rid));
            return new GeneralMessageDTO(0,"Success",new Object(){public final Integer oid=orderFormMapper.getNextOrderId();});

        }
        catch (Exception e){
            e.printStackTrace();
            return new GeneralMessageDTO(599,"ERROR:"+e.getMessage());
        }
    }

    public GeneralMessageDTO getOrderInfo(Integer oid, Integer uid){
        try{
            OrderForm orderForm=orderFormMapper.getById(oid);
//            ObjectMapper objectMapper=new ObjectMapper();
//            LOGGER.info(objectMapper.writeValueAsString(orderForm));
            if(rideMapper.getById(orderForm.getRideId()).getPassengerUid()==uid){
                return new GeneralMessageDTO(0,"Success",new GetOrderInfoResDTO(orderForm));
            }
            else{
                return new GeneralMessageDTO(311,"Permission denied(wrong uid)");
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return  new GeneralMessageDTO(599,"ERROR:"+e.getMessage());
        }
    }
    public  GeneralMessageDTO createPaymentRequest(Integer oid, CreateAnOrderReqDTO createAnOrderReqDTO){
        try {
            return  new GeneralMessageDTO(0,"Success",new Object(){public final String orderStr="";});
        }
        catch (Exception e){
            e.printStackTrace();
            return  new GeneralMessageDTO(599,"ERROR:"+e.getMessage());
        }
    }
}
