package com.example.ubergo.utils;

public class Constants {
    //USER IDENTITY
    //{"user": "driver"
    // Before the driver successfully receives the passenger, the passenger also needs to send his own positioning information regularly, after the driver picks up the passenger, only the driver needs to send the positioning "long": 0.00, "lat": 0.00, "speed": 58} {
    // Transform the state using action, define the behavior corresponding to different action codes "action": 1, "time": "","long": 0.00, "lat": 0.00, "speed": 2}//
    public static final String USER_DRIVER="DRIVER";
    public static final String USER_RIDER="RIDER";

    //RIDE Type
    public static final Integer ECONOMIC=0;
    public static final Integer COMFORT=1;
    public static final Integer LUXURY=2;

    //RIDE STATUS
    public static final String CREATED="CREATED";
    public static final String GOING_TO_DEPARTURE_POINT="GOING_TO_DEPARTURE_POINT";
    public static final String ARRIVING="ARRIVING";
    public static final String  ARRIVED="ARRIVED";
    public static final String  CANCELLED="CANCELLED";
    public static final String  ALARM_DURING_TRIP="ALARM_DURING_TRIP";


    //ORDER STATUS
    public static final String UNPAID="UNPAID";
    public static final String PAID="PAID";
    public static final String REFUNDING="REFUNDING";
    public static final String REFUNDED="REFUNDED";



}
