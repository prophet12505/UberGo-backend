-- User registration:
INSERT INTO User (Mobile_phone_number, Identity, Key, User_name, License_plate_number, Car_type, Total_length_of_historical_trip, Regional_province, City_of_region)
VALUES ('mobile_number', 'driver/passenger', 'password', 'user_name', 'license_plate_number', 'car_type', total_length, 'province', 'city');

-- The user creates an itinerary:
INSERT INTO Ride (Passenger_UID, Channel_name_of_MQTT, Itinerary_type, Departure_latitude, Departure_longitude, Departure_address, End_latitude, End_longitude, End_address, Status)
VALUES ('passenger_uid', 'channel_name', 'itinerary_type', departure_latitude, departure_longitude, 'departure_address', end_latitude, end_longitude, 'end_address', 'create');

-- The driver takes the order:
UPDATE Ride SET Status = 'order_taken', Order-taking_driver_UID = 'driver_uid', Driver_take_order_time = NOW() WHERE ID = 'ride_id';

-- The user cancels the trip:
UPDATE Ride SET Status = 'canceled', Trip_cancellation_time = NOW() WHERE ID = 'ride_id';

-- The driver picks up the passenger:
UPDATE Ride SET Status = 'pick_up_passenger', Passenger_pickup_time = NOW() WHERE ID = 'ride_id';

-- The driver arrives at the end point:
UPDATE Ride SET Status = 'reach_end', Arrival_time_to_end_point = NOW() WHERE ID = 'ride_id';

-- Initiate settlement order generation:
INSERT INTO Order_Form (trip_ID, creation_time, total_price, starting_price, tour_fees, fuel_costs, time_fee, special_location_fee, dynamic_prices, status, payment_platform, payment_serial_number, payment_result)
VALUES ('ride_id', NOW(), total_price, starting_price, tour_fees, fuel_costs, time_fee, special_location_fee, dynamic_prices, 'unpaid', '', '', '');

-- Tracking generates trajectory data:
INSERT INTO Track (trip_ID, time_series, gps_trajectory, speed_trajectory, altitude_trajectory)
VALUES ('ride_id', NOW(), 'gps_data', speed_data, altitude_data);

-- The user completes the payment:
UPDATE Order_Form SET status = 'paid' WHERE ID = 'order_id';
