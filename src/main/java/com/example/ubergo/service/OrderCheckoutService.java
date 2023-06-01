package com.example.ubergo.service;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.util.ResponseChecker;
import com.alipay.easysdk.payment.facetoface.models.AlipayTradePrecreateResponse;
import com.example.ubergo.DTO.RestDTO.CreateAnOrderReqDTO;
import com.example.ubergo.DTO.RestDTO.GeneralMessageDTO;
import com.example.ubergo.DTO.RestDTO.GetOrderInfoResDTO;
import com.example.ubergo.entity.OrderForm;
import com.example.ubergo.entity.Ride;
import com.example.ubergo.mapper.OrderFormMapper;
import com.example.ubergo.mapper.RideMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Duration;

import static com.example.ubergo.utils.Constants.*;

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

    public GeneralMessageDTO createAnOrder(Long rid){
        try{
            OrderForm orderForm=new OrderForm(rid);
            Ride ride=rideMapper.getById(rid);
            //set various prices
            orderForm.setStartingPrice(STARTING_PRICE);
            orderForm.setSpecialLocationFee(0d);
            orderForm.setTourFees(TOUR_FEE_PER_KM*ride.getTotalLength()/1000.0);//total length is in meters
            orderForm.setFuelCosts(FUEL_FEE_PER_KM*ride.getTotalLength()/1000.0);
            Long tripMinutes= Duration.between(ride.getArrivalTime(),ride.getPickUpTime()).toMinutes();
            orderForm.setTimeFee(TIME_FEE_PER_MIN*tripMinutes);

            Integer n=rideMapper.getRideByPassengerId(ride.getPassengerUid()).size();// how many times the user has taken ride
            Double p=orderForm.getStartingPrice()+
                    orderForm.getFuelCosts()+
                    orderForm.getTourFees()+
                    orderForm.getSpecialLocationFee()+
                    orderForm.getTimeFee(); //p refers to all the static prices in total
            Double dynamicPrice=0.05*Math.min(n,5)*p;
            orderForm.setDynamicPrices(dynamicPrice);
            orderForm.setTotalPrice(dynamicPrice+p);
            orderFormMapper.createAnOrder(orderForm);
            return new GeneralMessageDTO(0,"Success",new Object(){public final Integer oid=orderFormMapper.getNextOrderId();});

        }
        catch (Exception e){
            e.printStackTrace();
            return new GeneralMessageDTO(599,"ERROR:"+e.getMessage());
        }
    }

    public GeneralMessageDTO getOrderInfo(Long oid, Long uid){
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
    public  GeneralMessageDTO createPaymentRequest(Long oid, CreateAnOrderReqDTO createAnOrderReqDTO){

            try {
                OrderForm orderForm=orderFormMapper.getById(oid);
                // 2. 发起API调用（以创建当面付收款二维码为例）
                AlipayTradePrecreateResponse response = Factory.Payment.FaceToFace()
                        .preCreate("PC", orderForm.getPaymentSerialNumber(), String.valueOf(orderForm.getTotalPrice()));
                // 3. 处理响应或异常
                if (ResponseChecker.success(response)) {
                    LOGGER.info("调用成功");
                    return  new GeneralMessageDTO(0,"Success",response.getOutTradeNo());
                } else {
                    LOGGER.info("调用失败，原因：" + response.msg + "，" + response.subMsg);
                }
            } catch (Exception e) {
                LOGGER.error("调用遭遇异常，原因：" + e.getMessage());
                return  new GeneralMessageDTO(599,"ERROR:"+e.getMessage());
            }

    }
}
