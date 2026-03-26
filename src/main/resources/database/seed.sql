TRUNCATE TABLE Appointments, DoctorDetails, PatientDetails, Users RESTART IDENTITY CASCADE;

INSERT INTO Users (name, username, password, role) VALUES ('System Admin', 'admin', 'admin123', 'Admin');

INSERT INTO Users (name, username, password, role) VALUES ('Dr. Sarah Jenkins', 'dr_jenkins', 'doctorPass', 'Doctor');
INSERT INTO DoctorDetails (doctor_id, specialization, consultationFee) VALUES (2, 'Cardiologist', 800.00);

INSERT INTO Users (name, username, password, role) VALUES ('Dr. Robert Chen', 'dr_chen', 'docpass456', 'Doctor');
INSERT INTO DoctorDetails (doctor_id, specialization, consultationFee) VALUES (3, 'Neurologist', 900.00);

INSERT INTO Users (name, username, password, role) VALUES ('John Doe', 'johndoe', 'patient123', 'Patient');
INSERT INTO PatientDetails (patient_id, bloodGroup, contactNumber, age) VALUES (4, 'O+', '1234567890', 30);

INSERT INTO Users (name, username, password, role) VALUES ('Jane Smith', 'janesmith', 'health2024', 'Patient');
INSERT INTO PatientDetails (patient_id, bloodGroup, contactNumber, age) VALUES (5, 'A-', '0987654321', 25);

INSERT INTO Appointments (patient_id, doctor_id, appointment_date, appointment_time) VALUES (4, 2, '2026-03-25', '10:00:00');
INSERT INTO Appointments (patient_id, doctor_id, appointment_date, appointment_time) VALUES (5, 3, '2026-03-26', '14:30:00');