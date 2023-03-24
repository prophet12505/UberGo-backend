CREATE TABLE User (
                      UID INT NOT NULL AUTO_INCREMENT,
                      mobile_number VARCHAR(20) NOT NULL,
                      identity VARCHAR(20) NOT NULL,
                      `key` VARCHAR(20) NOT NULL,
                      user_name VARCHAR(50) NOT NULL,
                      license_plate_number VARCHAR(20),
                      car_type VARCHAR(20),
                      total_trip_length FLOAT NOT NULL,
                      regional_province VARCHAR(50) NOT NULL,
                      city VARCHAR(50) NOT NULL,
                      PRIMARY KEY (UID)
);
CREATE TABLE Ride (
                      ID INT NOT NULL AUTO_INCREMENT,
                      creation_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                      passenger_UID INT NOT NULL,
                      driver_UID INT NOT NULL,
                      mqtt_channel VARCHAR(50) NOT NULL,
                      itinerary_type VARCHAR(20) NOT NULL,
                      departure_latitude FLOAT NOT NULL,
                      departure_longitude FLOAT NOT NULL,
                      departure_address VARCHAR(255) NOT NULL,
                      end_latitude FLOAT NOT NULL,
                      end_longitude FLOAT NOT NULL,
                      end_address VARCHAR(255) NOT NULL,
                      status VARCHAR(50) NOT NULL,
                      order_take_time DATETIME NOT NULL,
                      pickup_time DATETIME,
                      arrival_time DATETIME,
                      cancel_time DATETIME,
                      total_length FLOAT NOT NULL,
                      itinerary_order_ID INT NOT NULL,
                      alarm_status VARCHAR(50),
                      after_sales_status VARCHAR(50),
                      trip_evaluation_score FLOAT,
                      itinerary_evaluation_content TEXT,
                      PRIMARY KEY (ID),
                      FOREIGN KEY (passenger_UID) REFERENCES User(UID),
                      FOREIGN KEY (driver_UID) REFERENCES User(UID)
);
CREATE TABLE Order_Form (
                            ID INT NOT NULL AUTO_INCREMENT,
                            trip_ID INT NOT NULL,
                            creation_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            total_price FLOAT NOT NULL ,
                            starting_price FLOAT NOT NULL DEFAULT 0,
                            tour_fees FLOAT NOT NULL DEFAULT 0,
                            fuel_costs FLOAT NOT NULL DEFAULT 0,
                            time_fee FLOAT NOT NULL DEFAULT 0,
                            special_location_fee FLOAT NOT NULL DEFAULT 0,
                            dynamic_prices FLOAT NOT NULL DEFAULT 0,
                            status VARCHAR(50) NOT NULL,
                            payment_platform VARCHAR(50) NOT NULL,
                            payment_serial_number VARCHAR(50) NOT NULL,
                            payment_result VARCHAR(255) NOT NULL,
                            PRIMARY KEY (ID),
                            FOREIGN KEY (trip_ID) REFERENCES Ride(ID)
);
CREATE TABLE Track (
                       ID INT NOT NULL AUTO_INCREMENT,
                       trip_ID INT NOT NULL,
                       time_series DATETIME NOT NULL,
                       gps_trajectory VARCHAR(255) NOT NULL,
                       speed_trajectory FLOAT NOT NULL,
                       altitude_trajectory FLOAT NOT NULL,
                       PRIMARY KEY (ID),
                       FOREIGN KEY (trip_ID) REFERENCES Ride(ID)
);
CREATE TABLE Log (
                     time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                     source_module VARCHAR(50) NOT NULL,
                     level VARCHAR(10) NOT NULL,
                     log_content TEXT NOT NULL
);
