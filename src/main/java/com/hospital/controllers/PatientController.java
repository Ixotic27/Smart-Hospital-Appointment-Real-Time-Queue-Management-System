package com.hospital.controllers;

import com.hospital.dao.DoctorDAO;
import com.hospital.dao.AppointmentDAO;
import com.hospital.models.Doctor;
import com.hospital.models.Patient;
import com.hospital.models.Appointment;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.util.List;
import javafx.util.StringConverter;

public class PatientController {

    @FXML
    private ComboBox<Doctor> doctorBox;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ComboBox<String> timeSlot;

    private DoctorDAO doctorDAO = new DoctorDAO();
    private AppointmentDAO appointmentDAO = new AppointmentDAO();

    @FXML
    public void initialize() {
        // Load doctors
        List<Doctor> doctors = doctorDAO.getAllDoctors();
        doctorBox.getItems().addAll(doctors);

        // Customize display of Doctor in ComboBox
        doctorBox.setConverter(new StringConverter<Doctor>() {
            @Override
            public String toString(Doctor doc) {
                if (doc == null) return null;
                return doc.getName() + " - " + doc.getSpecialization();
            }
            @Override
            public Doctor fromString(String string) {
                return null;
            }
        });

        // Add some dummy time slots
        timeSlot.getItems().addAll("10:00:00", "10:30:00", "11:00:00", "11:30:00", "12:00:00");
    }

    @FXML
    public void bookAppointment() {
        Doctor selectedDoctor = doctorBox.getValue();
        java.time.LocalDate selectedDate = datePicker.getValue();
        String selectedTime = timeSlot.getValue();

        if (selectedDoctor == null || selectedDate == null || selectedTime == null) {
            System.out.println("Please select all fields.");
            Alert alert = new Alert(AlertType.WARNING, "Please select Doctor, Date, and Time.");
            alert.show();
            return;
        }

        // Using dummy patient ID 4 from seed data for now
        Patient dummyPatient = new Patient(4, "John Doe", "johndoe", "patient123", "O+", "1234567890", 30);
        
        // Appointment expects date and time as strings in constructor
        Appointment app = new Appointment(
            0, dummyPatient, selectedDoctor, 
            java.sql.Date.valueOf(selectedDate).toString(), 
            java.sql.Time.valueOf(selectedTime).toString(), 
            "Pending"
        );

        boolean success = appointmentDAO.bookAppointment(app);
        if (success) {
            System.out.println("Appointment Booked Successfully");
            Alert alert = new Alert(AlertType.INFORMATION, "Appointment Booked Successfully!");
            alert.show();
        } else {
            System.out.println("Failed to book appointment.");
            Alert alert = new Alert(AlertType.ERROR, "Failed to book appointment.");
            alert.show();
        }
    }
}