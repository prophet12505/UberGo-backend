package com.example.ubergo.utils;

public class Constants {
    //USER IDENTITY
    //{"user": "driver"
    // Before the driver successfully receives the passenger, the passenger also needs to send his own positioning information regularly, after the driver picks up the passenger, only the driver needs to send the positioning "long": 0.00, "lat": 0.00, "speed": 58} {
    // Transform the state using action, define the behavior corresponding to different action codes "action": 1, "time": "","long": 0.00, "lat": 0.00, "speed": 2}//
    public static final String USER_DRIVER="DRIVER";
    public static final String USER_RIDER="RIDER";

    //RIDE Type/CAR TYPE
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

    //MQTT Distribution action types
    public static final String DISTRIBUTION_BROADCAST="DISTRIBUTION_BROADCAST";
    public static final String DISTRIBUTION_RIDE_CREATED="DISTRIBUTION_RIDE_CREATED";
    public static final String DISTRIBUTION_RIDE_CANCELLED="DISTRIBUTION_RIDE_CANCELLED";
    public static final String DISTRIBUTION_RIDE_PICKED_UP="DISTRIBUTION_RIDE_PICKED_UP";


    //MQTT track action types
    public static final String ORDER_HAS_BEEN_TAKEN="ORDER_HAS_BEEN_TAKEN";
    public static final String PASSENGER_HAS_BEEN_PICKED_UP="PASSENGER_HAS_BEEN_PICKED_UP";
    public static final String DESTINATION_ARRIVED="DESTINATION_ARRIVED";
    public static final String RIDE_CANCELLED="RIDE_CANCELLED";
    public static final String DRIVER_LOCATION_REFRESH="DRIVER_LOCATION_REFRESH";
    public static final String RIDER_LOCATION_REFRESH="RIDER_LOCATION_REFRESH";
    public static final String UPDATE_TRACK="UPDATE_TRACK";

    //log level
    public static final String INFO="INFO";
    public static final String DEBUG="DEBUG";
    public static final String WARNING="WARNING";
    public static final String ERROR="ERROR";
}
