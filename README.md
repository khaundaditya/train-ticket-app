# Train Ticket Application

## Overview

This is a simple Spring Boot application for managing train tickets. It includes RESTful APIs for purchasing tickets, viewing receipts, getting users by section, removing users, and modifying user seats.

## Features

- Purchase train tickets with validation
- View receipts for a user
- Get users and seats allocated by section
- Remove a user from the train
- Modify a user's seat

## Technologies Used

- Java
- Spring Boot
- Spring MVC
- Mockito (for testing)
- Jackson (for JSON processing)

## Getting Started

### Prerequisites

- Java Development Kit (JDK) installed
- Maven build tool
- Eclipse

### Running the Application

Clone the repository and navigate to the project directory:

```bash
git clone https://github.com/your-username/train-ticket-app.git
cd train-ticket-app
```
### Run the App
```bash
mvn spring-boot:run
```
### API Endpoints
+ POST /api/tickets/purchase: Purchase a train ticket.
+ GET /api/tickets/receipt/{userEmail}: View the details of the receipt for a user.
+ GET /api/tickets/users/{section}: View users and seats allocated by section.
+ DELETE /api/tickets/remove/{userEmail}: Remove a user from the train.
+ PUT /api/tickets/modify-seat/{userEmail}/{newSeat}: Modify a user's seat.

### Testing
```bash
mvn test
```
### Contributing
Contributions are welcome! Please feel free to branch out and raise Pull Request

