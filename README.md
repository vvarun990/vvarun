# WalmartTicketService

README FILE

Directory structure

APP: Contains configuration class AppConfig.java which extends ResourceConfig. Set up Application path to “/api” where Jersey will listen.

REST: Contains REST controller TicketServiceController.java. This class contains methods which will interact with TicketService and send response to client.

Exception: Contains created custom exception classes. 
Types of custom exceptions:
1. InvalidEmail exception - Email is necessary while holding seats. If null value is sent, InvalidEmail exception is thrown.
2. LevelOutofBound exeption – There are only 4 levels in this venue. If a number above 4 is entered, LevelOutofBound exception is thrown.
3. SeatCountExceeded exception – There are only 6250 seats in this venue. If more than 6250 seats are requested, SeatCountExceeded exception is thrown.

model: This package contains classes for models Level, Seat, SeatHold and SeatReserve. 

service: This package contains the provided TicketInterface interface and its implementation TicketService.java

Test packages: There are 2 test classes. TicketServiceTest.java for testing normal service TicketService.java. TicketServiceControllerTest.java class is used for testing REST controller.

IDE used: NetBeans

Design and implementation: 

•	Started with Maven java application and created the TicketService.java and TicketServiceTest.java (test class for testing this service). No database. Afterwards converted this project to web application and created REST API.

•	For initializing, TicketService constructor is used. HashMap <Integer, Level> is used for storing levels. Level object has attributes like id, name, numOfRows, seatsPerRow, price and a PriorityQueue for storing available seats of that level. Also created seat comparator for assigning seats as per level. 

•	TicketService class also has a PriorityQueue <SeatHold> for storing held seats. SeatHold object has attributes like id, email, holdTime and a set of Seats. Seat object has attributes like level, row and seat number.

•	HashMap <String, SeatReserve> for storing reserved seats. SeatReserve object has attributes like id, email, date and a set of seats.

Assumptions: 

•	As per the data provided, there are only 4 levels. If level number provided to numSeatsAvailable method is greater than 4 then LevelOutofBound exception is thrown.

•	For findAndHoldSeats method, level values are optional. If lower level value is null then it is set as 1 while holding seats. If higher level value is null then it is set as 4 while holding seats. Also email value can’t be null. If null email is sent, InvalidEmail exception is thrown. 

•	For findAndHoldSeats method, if seat count is greater than 6250 then SeatCountExceeded exception is thrown as venue capacity is 6250.

•	Logger is used for logging important events like initialization, exception handling. This will help developers while going through server side logs.

•	Method removeHold checks the seatHold creation time. If the seat is held for more than 2 seconds then it removes that object from the queue. This method needs to be called explicitly. It does not remove seats automatically.

•	For REST API, as HTTP is stateless protocol, the connection between client and server is lost once the request is complete. So because of this, while making POST request for reserving seats, same parameters are sent as that of hold request and then internally hold method gets called and generated hold id is then used for reserving seats.

REST API:

RESTful services are built using Jersey and Jackson and for testing these methods JerseyTest framework is used. JesrseyTest framework internally starts the server and hence we don’t have to do anything. HTTP methods can be used in following format.

•	GET http://localhost:8080/WalmartTicketServiceREST/api/ticketService - This will get number of available seats across all levels.

•	GET http://localhost:8080/WalmartTicketServiceREST/api/ticketService/2 - This will get number of seats available at level 2.

•	POST http://localhost:8080/WalmartTicketServiceREST/api/ticketService/holdSeats?numOfSeats=15&lowerLevel=1&higherLevel=3&email=abc@xyz.com – This will hold 15 seats. Here attributes lowerLevel and higherLevel are optional. Email cannot be null.

•	POST http://localhost:8080/WalmartTicketServiceREST/api/ticketService/reserve?numOfSeats=15&lowerLevel=1&higherLevel=3&email=abc@xyz.com – This will reserve 15 seats and get the confirmation code.

