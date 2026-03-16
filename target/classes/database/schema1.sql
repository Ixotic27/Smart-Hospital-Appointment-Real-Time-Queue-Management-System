
CREATE TABLE Users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL, -- Admin, Doctor, Patient

    
    specialization VARCHAR(100),    -- Doctor fields
    experience INT,

    age INT,            -- Patient fields
    gender VARCHAR(10),
    medical_history TEXT,

    phone VARCHAR(15)
);


CREATE TABLE Appointments (
    id SERIAL PRIMARY KEY,
    patient_id INT REFERENCES Users(id),
    doctor_id INT REFERENCES Users(id),
    appointment_date DATE NOT NULL,
    appointment_time TIME NOT NULL,
    status VARCHAR(20) DEFAULT 'Pending'
);


ALTER TABLE Appointments
ADD CONSTRAINT unique_doctor_slot
UNIQUE (doctor_id, appointment_date, appointment_time);