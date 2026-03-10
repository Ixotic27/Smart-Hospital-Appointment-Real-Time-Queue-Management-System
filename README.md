# Smart Hospital Appointment & Real-Time Queue Management System

## Overview
A Java desktop application built using JavaFX and JDBC that minimizes waiting times, prevents double-booking, and manages real-time patient queues. It provides a structured, automated, and reliable system for hospital operations.

## File Structure
* `src/main/java/com/hospital/main/` - Application entry point
* `src/main/java/com/hospital/models/` - Core OOP classes (User, Patient, Doctor, Admin, Appointment)
* `src/main/java/com/hospital/controllers/` - JavaFX controllers linking UI and logic
* `src/main/java/com/hospital/services/` - Multithreading and queue management logic
* `src/main/java/com/hospital/dao/` - Database access and JDBC queries
* `src/main/java/com/hospital/utils/` - Custom exceptions, DB connection class
* `src/main/resources/views/` - JavaFX FXML files
* `src/main/resources/styles/` - CSS Stylesheets for the UI
* `src/main/resources/database/` - SQL schema structure and seed data

## Technology Stack
* **Language:** Java (JDK 17+)
* **GUI Framework:** JavaFX
* **Database:** PostgreSQL / MySQL
* **Connectivity:** JDBC
* **Concepts Demonstrated:** Object-Oriented Programming (OOP), Collections Framework, Multithreading, Custom Exception Handling, Database Connectivity

### Setting up the Project
1. Clone the repository.
2. Ensure you have Java 17+ and Maven installed.
3. Use your preferred IDE (IntelliJ IDEA or Eclipse).
4. Run `mvn clean install` to install dependencies.
5. Create the database using the SQL file provided in `src/main/resources/database/schema.sql`.

Please see `IMPLEMENTATION_PLAN.md` for the step-by-step roadmap and team distribution.
