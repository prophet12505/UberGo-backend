Error Code:

1XX: Auth/User issues

101 -- signup Role missing
102 -- signup password missing
103 -- signup role model missing

111 -- login User does not exist
112 -- login password error

121 --
129 -- general user put request error

2xx: Ride Distribution issues
    20x -- create ride error
        201 -- Order not paid
        209 -- general create ride error
    21x -- driver take order error
        211 -- Ride has been accepted by others

599 -- uncategoried error

# API documentation

=====User Module===============

## User

create a user

### HTTP REQUEST

POST /user
back end signup up user

### REQUEST BODY

| Name                 | Type   | Description          | Required(default yes) |
| -------------------- | ------ | -------------------- | --------------------- |
| mobile_number        | String | name of the user     |                       |
| identity             | String | email of the user    | no                    |
| password             | String | password of the user |                       |
| user_name            | String | name of the user     |                       |
| license_plate_number | String | email of the user    |                       |
| car_type             | String | password of the user |                       |
| total_trip_length    | float  | name of the user     |                       |
| regional_province    | String | email of the user    |                       |
| city                 | String | password of the user |                       |

### RESPONSE BODY(signupDTO)

| Name   | Type    | Description            |
| ------ | ------- | ---------------------- |
| status | Integer | error code(0==success) |
| msg    | String  | "success"/ "errorMsg"  |

## LoginParams

login

### HTTP REQUEST

Get /loginParams
back end signup up user

### REQUEST BODY

| Name  | Type   | Description          | Required(default yes) |
| ----- | ------ | -------------------- | --------------------- |
| phone | String | name of the user     |                       |
| token | String | password of the user |                       |

### RESPONSE BODY(generalMessageDTO)

| Name   | Type    | Description            |
| ------ | ------- | ---------------------- |
| status | Integer | error code(0==success) |
| msg    | String  | "success"/ "errorMsg"  |

## uidSend

uidSend

### HTTP REQUEST

Put /user/{uid}

### REQUEST BODY

| Name | Type   | Description                                |
| ---- | ------ | ------------------------------------------ |
| key  | string | keys you want to modify(could be multiple) |

### RESPONSE BODY(generalMessageDTO)

| Name   | Type    | Description            |
| ------ | ------- | ---------------------- |
| status | Integer | error code(0==success) |
| msg    | String  | "success"/ "errorMsg"  |
| data   | String  | optional               |

=====Distribution module=============

## createARide

ride -- user publishes the order

### HTTP REQUEST

Post /ride

### REQUEST BODY

| Name                  | Type    | Description         |
| --------------------- | ------- | ------------------- |
| uid                   | integer | passenger's own UID |
| pickUpLong            | float   |                     |
| pickUpLat             | float   |                     |
| pickUpResolvedAddress | String  |                     |
| destResolvedAddress   | String  |                     |
| type                  | Integer |                     |
| province              | String  |                     |
| city                  | String  |                     |

### RESPONSE BODY(generalMessageDTO)

| Name   | Type    | Description            |
| ------ | ------- | ---------------------- |
| status | Integer | error code(0==success) |
| msg    | String  | "success"/ "errorMsg"  |
| data   | String  | optional {"rid": ""}   |

## takeOrder

driver take the order and go to the database to change the status of the relevant entries in the ride table
<!-- I wander if it's OK to use int to store UID instead of varchar  -->
<!-- I haven't notified tracking service -->


### HTTP REQUEST

PUT /ride/{rid}

### REQUEST BODY

| Name        | Type    | Description      |
| ----------- | ------- | ---------------- |
| driverUid   | integer | driver's own UID |
| long        | float   |                  |
| lat         | float   |                  |
| numberPlate | String  |                  |
| vehicleInfo | String  |                  |

### RESPONSE BODY(generalMessageDTO)

| Name   | Type    | Description            |
| ------ | ------- | ---------------------- |
| status | Integer | error code(0==success) |
| msg    | String  | "success"/ "errorMsg"  |
| data   | String  | {"channel":}           |
