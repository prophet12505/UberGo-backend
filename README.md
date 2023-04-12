Error Code:

1XX: Auth/User issues

101 -- signup Role missing        
102 -- signup password missing          
103 -- signup role model missing              

111 -- login User does not exist         
112 -- login password error          

129 -- general user put request error         

2xx: Ride Distribution issues        
20x -- create ride error          
201 -- Order not paid             
209 -- general create ride error        

21x -- driver take order error         
211 -- Ride has been accepted by others        

22x -- cancel ride error          
221 -- ride has already been cancelled           

3xx: Order/Checkout issues         
311 -- Permission denied(wrong uid)         
331 -- Payment failed            

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

## Login
login
<!-- // I didn’t differentia driver and rider yet -->
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

## updateUser
updateUser
<!-- haven’t done city validation -->
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

PUT /ride/{rid}/takeOrder

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

## cancelRide

Both the user and the driver can cancel the order during the trip, if it is in the trip, call the Tracking module to save, if it is not in the trip, directly end the library, and the Order module will not be called for billing.

<!-- I put another sector of endpoint after cancel because it has duplicate endpoint as takeOrder  -->
<!-- I haven't called tracking model to save it -->

### HTTP REQUEST

PUT /ride/{rid}/cancel

### REQUEST BODY

| Name   | Type    | Description    |
| ------ | ------- | -------------- |
| uid    | integer | user's own UID |
| cancel | Boolean |                |

### RESPONSE BODY(generalMessageDTO)

| Name   | Type    | Description            |
| ------ | ------- | ---------------------- |
| status | Integer | error code(0==success) |
| msg    | String  | "success"/ "errorMsg"  |
| data   | String  | {"channel":}           |

## getRideInfo

Used for passengers to poll the status of orders and query order details

<!-- haven't made usage of given long and lat  -->
<!-- I directly calculated estimatedmiledge instead of map api, will change it later  -->

### HTTP REQUEST

GET /ride/rid

### REQUEST Params

| Name | Type   | Description |
| ---- | ------ | ----------- |
| long | Double |             |
| lat  | Double |             |

### RESPONSE BODY(generalMessageDTO)

| Name   | Type    | Description            |
| ------ | ------- | ---------------------- |
| status | Integer | error code(0==success) |
| msg    | String  | "success"/ "errorMsg"  |
| data   | String  | getRideInforResDTO     |


## createAnOrder
Only one RID is required, and Order automatically calculates the price by accessing the data in the Ride and SNAP tables, generates a bill and returns the ID of the bill.
<!-- Haven't "automatically calculates the price by accessing the data in the Ride and SNAP tables, generates a bill and returns the ID of the bill." -->
### HTTP REQUEST
POST /order

### REQUEST Body
| Name | Type   | Description |
| ---- | ------ | ----------- |
| rid  | Double |             |

### RESPONSE BODY(generalMessageDTO)
| Name   | Type    | Description            |
| ------ | ------- | ---------------------- |
| status | Integer | error code(0==success) |
| msg    | String  | "success"/ "errorMsg"  |
| data   | String  | {"oid": ""}     |


## getOrderInfo
Query order data
### HTTP REQUEST
GET /order/{oid}
### REQUEST Params
| Name | Type   | Description |
| ---- | ------ | ----------- |
| uid | Integer |             |
### RESPONSE BODY(generalMessageDTO)
| Name   | Type    | Description            |
| ------ | ------- | ---------------------- |
| status | Integer | error code(0==success) |
| msg    | String  | "success"/ "errorMsg"  |
| data   | String  | {
		"oid": "",
		"rid": "",
		"createTime": "",
		"price": 20.00
	}
     |


## createPaymentRequest

### HTTP REQUEST
/order/{oid}/createPaymentRequest

### REQUEST Body
| Name | Type   | Description |
| ---- | ------ | ----------- |
| uid  | Integer |             |
| platform  | String |        |

### RESPONSE BODY(generalMessageDTO)
| Name   | Type    | Description            |
| ------ | ------- | ---------------------- |
| status | Integer | error code(0==success) |
| msg    | String  | "success"/ "errorMsg"  |
| data   | String  | 

## confirmPayment

### HTTP REQUEST
/order/{oid}/confirmPayment

### REQUEST Body
| Name | Type   | Description |
| ---- | ------ | ----------- |
| uid  | Integer |             |
| platform  | String |        |
| track_no  | String |        |

### RESPONSE BODY(generalMessageDTO)
| Name   | Type    | Description            |
| ------ | ------- | ---------------------- |
| status | Integer | error code(0==success) |
| msg    | String  | "success"/ "errorMsg"  |
| data   | String  | 
