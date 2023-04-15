package com.example.ubergo.DTO.RestDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ConfirmPaymentReqDTO {
    private Long uid;
    private String platform;

    @JsonProperty("trade_no")
    private String tradeNum;
}
