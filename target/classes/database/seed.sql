INSERT INTO Users (name, username, password, role)
VALUES('System Admin', 'admin', 'admin123', 'Admin');

INSERT INTO Users(name, username, password, role)
VALUES('Dr. Singh', 'aditya42', '1234', 'Doctor');

INSERT INTO DoctorDetails (doctor_id, specialization, consultationFee)
VALUES(2, 'Cardiologist', 800.00);

INSERT INTO Users (name, username, password, role)
VALUES ('Shivam Singh', 'shivam', 'brb123', 'Patient');

INSERT INTO PatientDetails(patient_id, bloodGroup, contactNumber, age)
VALUES(3, 'B+', '9542311333', 25);

INSERT INTO Appointments (patient_id, doctor_id, appointment_date, appointment_time)
VALUES (3, 2, '2026-03-25', '10:00:00');