# Member 2: Core Java & OOP Logic Guide

As **Member 2**, your entire domain is the structural **backbone** of the Smart Hospital system. While Member 1 handles the raw database rows and Member 3/4 handles the visuals and live updates, your job is to translate raw data into **Intelligent Java Objects** and organize them using Data Structures.

---

## 1. What Your Role Includes
Your domain dictates how entities are shaped in the code and how lists of those entities are temporarily held in computer memory.
1. **Object-Oriented Design**: Building the foundational blueprints for users and appointments using Inheritance, Encapsulation, and Polymorphism.
2. **Data Packaging**: Bundling diverse data (strings, integers, floats, dates) into clean objects so the rest of the application can safely pass it around without losing information.
3. **Data Structures**: Deciding *how* to store groups of objects in memory (e.g., using `ArrayLists` for tables, or `Queues` for real-time tracking).

---

## 2. What Has Been Built (The Architecture)

### The Models Package (`com.hospital.models`)
You have constructed a highly normalized Object-Oriented hierarchy:

* **`User.java` (The Parent Class)**
  * Contains all shared, base traits: `id`, `name`, `username`, `password`, `role`.
  * Protects its data using strict `private` fields and provides `public` getters/setters (Encapsulation).
* **`Doctor.java` & `Patient.java` (The Children)**
  * Both explicitly `extends User` (Inheritance).
  * `Doctor` adds specialized fields: `specialization` and `consultationFee`.
  * `Patient` adds specialized fields: `bloodGroup`, `contactNumber`, and `age`.
* **`Appointment.java` (The Transaction Object)**
  * This uses Object Composition. Instead of storing a doctor's string name and a patient's string name, it holds **actual instances** of the `Doctor` object and `Patient` object recursively inside it, along with the `date`, `time`, and `status`.

### The Data Structures (Collections)
* **`ArrayList<T>`**: Whenever a DAO (Member 1's Database code) finishes pulling 100 rows from PostgreSQL, it relies entirely on your Models. It creates a `new ArrayList<>()`, loops through the SQL rows, instantiates your `Doctor` or `Patient` objects, and inserts them into the list. This `ArrayList` is what gets handed upward to the User Interface.

---

## 3. The Front-to-Back Workflow Context
Here is exactly where your OOP files come into play geographically during a real system event (e.g., An Admin viewing the Doctor's list):

1. **Frontend (Member 3)**: The user clicks "Doctors Overview". The Java UI says, "I need data to paint on the screen."
2. **The DAO (Member 1)**: The system pings the Database via `SELECT * FROM Users`. 
3. **Your Role (Member 2)**: The Database returns raw scrambled text. The DAO says, "I don't know what to do with this text. Give me **Member 2's Doctor blueprint**." The code uses your `new Doctor(id, name, ...)` constructor to build safe java objects and packs them neatly into an `ArrayList<Doctor>`.
4. **The UI Again**: The `ArrayList` full of your OOP objects travels back to the Frontend. The screen uses your `.getName()` and `.getSpecialization()` methods to extract cleanly formatted text and paint it into the tables.

Without your POJOs (Plain Old Java Objects) and ArrayLists, the UI would be forced to manually parse raw commas and text lines, which would instantly crash the program.

---

## 4. What Was "Left" or Migrated

Initially, there were two standalone utility files designated for your role (`DataStorageService.java` and `QueueService.java`):
* `QueueService` utilized an `ArrayDeque` (Queue data structure) to keep a physical lineup of appointments.
* `DataStorageService` utilized `Collections.sort` to chronologically organize lists.

**Why they were migrating/left behind:**
While excellent isolated theory, standard UI architectures rarely use detached middle-man lists unless you are building a highly cached local server.

Instead, your Data Structures (`Lists`/`Sorts`) were natively merged directly into the `DAO` classes and JavaFX `TableView / VBox` nodes. The live multithreading queue tracker (Member 4's task) now uses `ArrayList<Appointment>` combined with dynamic SQL filtering to maintain exactly who is next in line natively, preventing out-of-sync memory cache issues that `QueueService` would have inevitably caused.
