1. Overview: The system consists of three parts:
   REFERENCE LINK:  https://github.com/hortonworks-gallery/iot-truck-streaming
   A. Web site
      a. a portal to receive the GPS signal and push into Kafka
      b. web pages to track cars and other information(organization, car, driver, route)
      
   B. Simulation signal generator: read csv files and send the signals to web site via HTTP
   
   C. Storm part: read signal data from kafka, process them and push to ActiveMQ
   
2. Basic data model:
   Organization: include id, name, parent organization
   Driver: driver's personal information
   Car: car information and driver's information
   Route: goods' information, car information and related route information
   RoutePoint: from GPS signal: routeid, carid, location, speed, events 
   RouteTrack: enrich information to RoutePoint to save in the ActiveMQ and shown in the web page
   
3. 3rd part tools:
   A. Kafka Server: Tested version: 2.10
     #create kafka topic, retention min = 60min
     /usr/hdp/current/kafka-broker/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 4 --topic bssignal --config retention.ms=60
 
     #check kafka topics
     /usr/hdp/current/kafka-broker/bin/kafka-topics.sh --list --zookeeper localhost:2181

     #describe topic
     /usr/hdp/current/kafka-broker/bin/kafka-topics.sh --describe --zookeeper localhost:2181 --topic bssignal
 
     #check the message in topic
     /usr/hdp/current/kafka-broker/bin/kafka-console-consumer.sh --zookeeper localhost:2181 --topic bssignal --from-beginning
   
   B. ActiveMQ: Tested Version: 5.9.0
     Admin Page: http://localhost:8161/admin/topics.jsp
     
   C. Database: This version does not use any database to simply the development. 
      All the csv files under scripts/phoenix will be parsed into memory. 
      The data can be read and updated. But all the changes will be discarded after the system is restarted. 
   
4. GPS signal simulation generator:  folder: baisha_simulation
   start run.bat
   parameters in application.properties:
   routes.dir: all route csv files will be parsed and sent to website simutaneously
   simulation.interval: interval in milliseconds between two points
   http.endpoint: website url
   * this tool can also send the signals directly to kafka
   
5. Storm:
   To test in the local mode(you can define the time you want to run in seconds):
   com.realtimestudio.transport.storm.GPSSignalTopology localmode 60
   To run remotely:
   transport-storm.sh
   
   The paramters is in storm.properties including kafka and ActiveMQ connection information.
   
   Spout get the signals from Kafka and parse to RoutePoint
   SignalProcessBolt get RoutePoint objects and get route, driver information, to emit RouteTrack object
   WebsocketBolt get RouteTrack objects and push to ActiveMQ (topic: all_signal)
   
 6. Web site:
    A. /signal : no authentication. receive GPS signal string from Simulator
    B. /track: subscribe /topic/all_signal to get data from ActiveMQ via Websocket
    C. Restful webservices: to get static information
      a. get all the categoried data --> return list
       /organizations
       /drivers
       /cars
       /routes
      b. get data by id
        /organization/20285
      c. get data by page
        /organizations/page?page=2&itemsPerPage=10  
      d. create objects  (METHOD=POST)
        pass by the requestBody
      e. update objects (METHOD=PUT)
        /organization/id
        pass by requestBody
           
      
   
      
   