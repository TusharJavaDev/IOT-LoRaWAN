# IOT-LoRaWAN


Java Interview Questions 

Technical Architecture Requirements 

Overview: 

Develop a live and historical dashboard to display data from multiple IoT devices. 

IoT devices will be connected to a LoRaWAN gateway. 

The application should subscribe to the gateway to read and process the data. 

 

Dashboard Requirements:  

a. Live Data Display: 

The dashboard should display live data without any time delay.  

b. Data Parsing: 

Data should be parsed before being displayed on the dashboard.  

c. Data Storage: 

Data should be stored in a database accessible from the dashboard. 

 

Technical Specifications:  

a. Data Retrieval: 

Use the MQTT protocol to read data from the LoRaWAN gateway.  

b. Front-End Communication: 

Use  WebSockets in the front end to ensure real-time data updates. 

 

Database Requirements:  

a. Database Selection: 

Choose a database capable of handling large volumes of data. 

The database should efficiently manage time series events. 

Ensure the database is highly scalable to accommodate growing data needs. 

 

 

Coding Requirements 

 

User Authentication: 

Implement user authentication using Java Web Token (JWT). 

Integrate Spring Security to handle authentication and authorization. 

Ensure secure handling of user credentials and tokens. 

Technology Stack: 

Backend: Java Spring Boot 3.2 

Security: Spring Security with JWT 

  

  

Note: To push MQTT  messages to the broker for testing, install Docker and run following command 

  

docker run -it --rm efrecon/mqtt-client pub -h 34.93.115.115 -p 1883 -t "sample-topic" -m "message” 

 

 

 

 
