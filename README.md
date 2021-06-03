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
There are other ways to undeploy the verticle but it's the same effectr as killing the jvm runnin


##How to Start The App:
mvn install
then
/../../target>java -jar spring-vertx-1.0-SNAPSHOT.jar

.   ____          _            __ _ _
/\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
\\/  ___)| |_)| | | | | || (_| |  ) ) ) )
'  |____| .__|_| |_|_| |_\__, | / / / /
=========|_|==============|___/=/_/_/_/
:: Spring Boot ::                (v2.5.0)

11:30:29.149 [main] INFO  c.a.v.VertxSpringApplication - Starting VertxSpringApplication using Java 1.8.0_281 on Ashi9laptop with PID 22076 (C:\Users\44795\IdeaProjects\spring-vertx\target\spring-vertx-1.0-SNAPSHOT.jar started by 44795 in C:\Users\44795\IdeaProjects\spring-vertx\target)
11:30:29.152 [main] INFO  c.a.v.VertxSpringApplication - No active profile set, falling back to default profiles: default
11:30:31.126 [vert.x-eventloop-thread-1] INFO  c.a.v.v.TradeVerificationVerticle - Started Service
11:30:31.488 [main] INFO  c.a.v.VertxSpringApplication - Started VertxSpringApplication in 2.954 seconds (JVM running for 3.705)
11:30:31.490 [main] INFO  o.s.b.a.ApplicationAvailabilityBean - Application availability state LivenessState changed to CORRECT
11:30:31.491 [main] INFO  o.s.b.a.ApplicationAvailabilityBean - Application availability state ReadinessState changed to ACCEPTING_TRAFFIC

##How to scale the Vertx app
1) Change the send method on vertx to publish.
2) separate the sender and receiver into two separate jar files
3) add a cluster manager dependency like Hazlecast.
4) run the client and receiver in different consoles to see the clustering.
Given time, this is an easy change to make as Vert is designed to cluster from the ground up so it's a simple tweak.
