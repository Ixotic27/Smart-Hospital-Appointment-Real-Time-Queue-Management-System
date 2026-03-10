CREATE TABLE Users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL -- 'Admin', 'Doctor', 'Patient'
);

CREATE TABLE Appointments (
    id SERIAL PRIMARY KEY,
    patient_id INT REFERENCES Users(id),
    doctor_id INT REFERENCES Users(id),
    appointment_date DATE NOT NULL,
    appointment_time TIME NOT NULL,
    status VARCHAR(20) DEFAULT 'Pending' -- 'Pending', 'Ongoing', 'Completed', 'Cancelled'
);
