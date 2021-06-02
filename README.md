## RESTful service implemented using Spring Vert.x

This module contains a RESTFUL service implemented using Vertx.io and Spring.

ashmpatel@gmail.com

The test class will run all the tests to verify valid and invalid FX Spot trades, FWDS , European and American options.

The code was built using :
Apache Maven 3.8.1 (05c21c65bdfed0f71a2f2ada8b84da59348c4c5d)
Maven home: c:\apache-maven-3.8.1\bin\..
Java version: 1.8.0_281, vendor: Oracle Corporation, runtime: C:\Program Files\Java\jdk1.8.0_281\jre
Default locale: en_GB, platform encoding: Cp1252
OS name: "windows 10", version: "10.0", arch: "amd64", family: "windows"

To test if the service is up:
1) Run the class VertxSpringApplication - that has a "main" method that will fire up the restful service server using Vertx.
2) Hit the following in your browser: http://localhost:8069/
   You should see a message saying:
   Service is UP
3) Send POST requests to the api end point:
   http://localhost:8069/api/v1/verifytrade
   
To stop the service,simply kill the console running the main class.
There are other ways to undeply the verticle but it's the same effectr as killing the jvm running oin the console.