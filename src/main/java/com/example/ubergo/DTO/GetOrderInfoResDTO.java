package com.example.ubergo.DTO;

import com.example.ubergo.entity.OrderForm;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GetOrderInfoResDTO {
    private Integer oid;
    private Integer rid;
    private String creationTime;
    private Double price;
    public GetOrderInfoResDTO(OrderForm orderForm){
        this.oid=orderForm.getId();
        this.rid=orderForm.getRideId();
        this.price= orderForm.getTotalPrice();
        this.creationTime= String.valueOf(orderForm.getCreationTime());
    }
}
