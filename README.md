# Train Booking Springboot Application

### Description:

This project is a Spring Boot application that allows to purchase ticket, modify the seat assignments, viewing ticket details, deleting the ticket, view the user and seat status for chosen section.

### Features:

- Purchase train tickets between two locations
- Modify a user's seat within available sections (A and B).
- View ticket details for a given ticket
- View user and seat allocations based on specific sections.
- Remove users from the train.

### Getting Started:

#### Prerequisites:

- Java Development Kit (JDK) 17

- Apache Maven

- Git

- Any IDE of your choice

### Installation:

Clone the repository:

```git clone https://github.com/Aniruth-raman/TrainBooking```

Navigate to the project directory: 

```cd trainticket```

Build the project: 

```mvn clean install```

### Usage:

Run the application: 
```mvn spring-boot:run```

Access the API at: 

```http://localhost:8080```

```http://localhost:8080/swagger-ui/index.html```

### Endpoints:

Purchase Ticket:
- Method: POST
- URL: /api/tickets/purchase
- Request Body: User details (first name, last name, email), origin, destination, ticketAmount

Modify User's Seat:
- Method: PUT
- URL: /api/tickets/modifySeat/{userId}
- Request Body: Preferred section

View Ticket Details:
- Method: GET
- URL: /api/tickets/{userId}

View Users and Seats by Section:
- Method: GET
- URL: /api/tickets/section/{section} (e.g., /api/tickets/section/A)

Remove User from Train:
- Method: DELETE
- URL: /api/tickets/remove/{userId}

### Additional Notes:
Swagger UI and actuator are included for API documentation and monitoring.

Relevant unit tests are available in the project.

### Assumptions:
- The initial set of seats must be defined. 
_(I have written @PostConstruct method to create 10 seats in Section A and Section B so that no issue occurs during purchase ticket)_ 

- User ID directly retrieves the receipt object.

- User-seat mapping for a section is returned as a HashMap.

- Removing a user updates the seat availability and removes the receipt.

- Modifying a seat involves finding the first available seat in the preferred section.