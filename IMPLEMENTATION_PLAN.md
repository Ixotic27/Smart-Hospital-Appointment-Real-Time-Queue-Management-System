# Project Implementation Plan
**Smart Hospital Appointment & Real-Time Queue Management System**

## Team Distribution (4 Members)
To work efficiently without AI, each member is assigned a core domain. You will all integrate your work at the end of each step.
* **Member 1 (Database & DAO)**: Responsible for PostgreSQL/MySQL database setup, writing SQL queries, and the JDBC connection.
* **Member 2 (Core Java & OOP)**: Responsible for Models (`User`, `Doctor`, `Patient`, `Appointment`), interfaces, and lists/collections logic.
* **Member 3 (Frontend & JavaFX)**: Responsible for UI design, FXML views, CSS styling, and controller event handling.
* **Member 4 (Concurrency & Logic)**: Responsible for Real-Time Queue management (Multithreading), Custom Exceptions, and system workflow.

---

## Step-by-Step Execution Plan

### Step 1: Foundation & Architecture (0% ➔ 20%)
*Goal: Set up the project structure, database tables, and core Java classes.*
* **Member 1 (DB)**: Design the ER diagram. Create `schema.sql` with tables for Users (Admin, Doctor, Patient) and Appointments.
* **Member 2 (OOP)**: Create the core OOP classes. E.g., a `User` base class, with `Doctor` and `Patient` extending it. Set up attributes, getters, setters, and constructors.
* **Member 3 (UI)**: Design paper wireframes or define requirements for the Login, Admin Dashboard, Doctor Dashboard, and Patient Booking screens.
* **Member 4 (Logic)**: Define custom exception classes (e.g., `SlotConflictException`, `InvalidLoginException`) and verify the Maven/Java project builds correctly on all members' machines.
* **Completion Milestone**: 20% Project Completion.

### Step 2: Backend Connectivity & CRUD Operations (20% ➔ 40%)
*Goal: Connect Java to the database and ensure data can be created, read, updated, and deleted.*
> ⚠️ **IMPORTANT: This step MUST be completed before the 25th.**
* **Member 1 (DB)**: Implement the `DatabaseConnection` utility class. Write DAO (Data Access Object) classes to run `INSERT`, `SELECT`, `UPDATE` queries for appointments.
* **Member 2 (OOP)**: Use Java Collections (`ArrayList`, `HashMap`) to temporarily store and manage retrieved database records in memory for quick access.
* **Member 3 (UI)**: Build the basic JavaFX `Login.fxml` and `Dashboard.fxml` files without complex logic yet. Ensure the screens launch successfully.
* **Member 4 (Logic)**: Write the login authentication logic (verifying username/password hashes with DB via Member 1's DAO). Apply role-based redirection to the appropriate dashboard.
* **Completion Milestone**: 40% Project Completion. *(Target: Before the 25th)*.

### Step 3: UI Integration & Appointment Booking (40% ➔ 70%)
*Goal: Bring the app to life. Users should be able to log in, view schedules, and book appointments.*
* **Member 1 (DB)**: Write specific queries to validate slots and ensure no double-booking occurs at the database level.
* **Member 2 (OOP)**: Implement searching and sorting capabilities for appointments using Java Collections.
* **Member 3 (UI)**: Connect the FXML buttons to the Controller methods. Bind Lists and Tables to show dynamic data (e.g., TableViews for Doctor schedules).
* **Member 4 (Logic)**: Handle the actual appointment booking flow. Validate user input and `throw` custom exceptions if a patient picks an occupied slot or invalid time.
* **Completion Milestone**: 70% Project Completion.

### Step 4: Real-Time Queue, Multithreading & Testing (70% ➔ 100%)
*Goal: Finalize the "Real-Time Queue Management" feature, handle errors smoothly, and test the whole application.*
* **Member 1 (DB)**: Ensure appointment statuses (Pending, Completed, Ongoing) update safely in the database. Help format the final database schema for submission.
* **Member 2 (OOP)**: Use a `Queue` data structure (e.g., `PriorityQueue` or `LinkedList`) to manage the current day's patient lineup effectively.
* **Member 3 (UI)**: Design a "Live Queue Tracker" screen that patients or receptionists can view to see who is currently with the doctor and who is next.
* **Member 4 (Logic)**: **Crucial Step:** Implement Java `Thread` or `Runnable` to periodically refresh and update the live queue UI without freezing the application. Add file logging for exception handling.
* **Completion Milestone**: 100% Project Completion. Ready for final integration and presentation.
