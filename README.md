# DBAssignment

This project is developed using below platform/libraries:
- Windows 10
- java 8
- Eclipse oxygen
- gradle 
- Github
- spring boot 1.5.4.RELEASE
- jetty server
- swagger 2.6.1 for APi doc

The "retail" folder can be imported into Eclipse as eclipse project. 

Below are the directory structure of source code:
- All domain classes inside package com.db.retail.domain
- Spring boot and swagger helper class  inside com.db.retail package
- DAO classes inside com.db.retail.dao package
- error handling classes inside com.db.retail.exception package
- geocode google api client inside com.db.retail.googleservice.client package
- Rest APi classes inside com.db.retail.rest.service package
- service layer classes inside com.db.retail.service package
- utility classes inside com.db.retail.utils package
- test classes inside src/test/java directory


How to build and run:
1) Go to "retail" folder from command prompt.
2) execute the command "gradlew build". It will build the application and run the unit test.
3) execute the command "gradlew test". It will run the unit test.
4) execute the command "java -jar build\libs\retail-0.0.1-SNAPSHOT.jar". It will run the application inside the jetty server.

To generate the Rest API doc, access the below URL from the browser:
http://localhost:8090/swagger-ui.html

How to run the Rest APIs:
---------------------------
1) Add shop API
   URL: http://localhost:8090/shop
   
   Method: POST
   
   Request body:
       {
          "shopName":"abc",
          "shopAddress":{"number":"test", "postCode":"abc"}
        }
        
   Response code: CREATED(201)   
   
   Response body:
   Blank if added first time
   Previous version of shop if replacing. The format is as below:
   {
      "shopName": "abc",
      "shopAddress": {
          "number": "test",
          "postCode": "abc"
      },
      "shopGeoDetails": {
          "latitude": 33.7372177,
          "longitude": -117.8135579
      }
  }
  
  2) Get nearest shop API:
     URL(with sample value for path param: http://localhost:8090/shop/nearest?latitude=1.23&longitude=2.34
     
     Method: GET
     
     Response code : OK(200)
     
     Response body:
     {
        "shopName": "abc",
        "shopAddress": {
            "number": "test",
            "postCode": "abc"
        },
        "shopGeoDetails": {
            "latitude": 33.7372177,
            "longitude": -117.8135579
        }
    }

   In case of error below common response structure will be followed by all the Rest APIs:
   
   {
       "message": "error message",
       "status": "fail"
   }
