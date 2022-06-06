# power-plant

# REST API

power-plant rest APIs

### 1. save battery details

### Request

`POST`

    localhost:9999/virtual-power-plant/api/v1/batteries/create

### Response

    {
    "message": "Created",
    "statusCode": 201,
    "data": {
        "batteryId": 7,
        "name": "Midlands",
        "postcode": 6057,
        "wattCapacity": 50500
    }
    }

### 2. get battery details based on postcode range

### Request

`GET`

    localhost:9999/virtual-power-plant/api/v1/batteries/create

### Response

    {
    "message": "OK",
    "statusCode": 200,
    "data": {
        "batteries": [
            "Cannington",
            "Hay Street",
            "Midland",
            "Midlands"
        ],
        "averageWattCapacity": 34250,
        "totalWattCapacity": 137000
    }
    }




## Status Codes

| Status Code | Description               |
|:------------|:--------------------------|
| 200         | `OK`                      |
| 201         | `CREATED`                 |
| 400         | `BAD REQUEST`             |
| 404         | `NOT FOUND`               |
| 409         | `CONFLICT`                |
| 415         | `UNSUPPORTED MEADIA TYPE` |
| 500         | `INTERNAL SERVER ERROR`   |


## Requirements

For building and running the application you need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 4](https://maven.apache.org)
