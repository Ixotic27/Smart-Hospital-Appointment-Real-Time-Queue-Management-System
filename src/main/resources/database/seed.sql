INSERT INTO Users (name, username, password, role)
VALUES('System ADmin', 'admin', 'admin123', 'Admin');

INSERT INTO Users(name, username, password, role)
VALUES('Dr. Singh', 'aditya42', '1234', 'Doctor');

INSERT INTO DoctorDetails (doctor_id, specialization, experience, phone)
VALUES(2, 'Cardiologist', 10, '7599123444');

INSERT INTO Users (name, username, password, role)
VALUES ('Shivam Singh', 'shivam', 'brb123', 'Patient');

INSERT INTO PatientDetails(patient_id, age, gender, phone, medicalHist)
VALUES(3, 25, 'Male', '9542311333', 'None')

INSERT INTO Appointments (patient_id, doctor_id, appointment_date, appointment_time)
VALUES (3, 2, '2026-03-25', '10:00:00');