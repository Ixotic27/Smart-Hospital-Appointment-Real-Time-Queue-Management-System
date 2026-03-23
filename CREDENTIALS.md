# System Credentials

Below are the default sample credentials that you can use to log into the Smart Hospital Appointment Application. These credentials correspond to the different roles defined in the `Users` database table.

## Administrators
Admins have full access to manage doctors, patients, and system settings.
* **Name:** System Admin
* **Username:** `admin`
* **Password:** `admin123`
* **Role:** `Admin`

## Doctors
Doctors can view their scheduled appointments, patient details, and manage their availability.
* **Name:** Dr. Sarah Jenkins
* **Username:** `dr_jenkins`
* **Password:** `doctorPass`
* **Role:** `Doctor`

* **Name:** Dr. Robert Chen
* **Username:** `dr_chen`
* **Password:** `docpass456`
* **Role:** `Doctor`

## Patients
Patients can book appointments, view their history, and update their health details.
* **Name:** John Doe
* **Username:** `johndoe`
* **Password:** `patient123`
* **Role:** `Patient`

* **Name:** Jane Smith
* **Username:** `janesmith`
* **Password:** `health2024`
* **Role:** `Patient`

---
*Note: Make sure these records exist in your PostgreSQL `Users` table so that the unified login can successfully authenticate these accounts!*
