package com.example.ubergo.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ConfirmPaymentReqDTO {
    private Integer uid;
    private String platform;

    @JsonProperty("trade_no")
    private String tradeNum;
}
