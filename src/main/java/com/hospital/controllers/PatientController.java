package com.hospital.controllers;

import com.hospital.dao.DoctorDAO;
import com.hospital.dao.AppointmentDAO;
import com.hospital.models.Doctor;
import com.hospital.models.Patient;
import com.hospital.models.Appointment;
import com.hospital.utils.Session;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

import java.util.ArrayList;
import java.util.List;

public class PatientController {

    @FXML private ComboBox<Doctor> doctorBox;
    @FXML private DatePicker datePicker;
    @FXML private ComboBox<String> timeSlot;

    @FXML private Label headerLabel;
    @FXML private Label subHeaderLabel;
    @FXML private VBox cardArea;
    @FXML private VBox contentArea;

    private DoctorDAO doctorDAO = new DoctorDAO();
    private AppointmentDAO appointmentDAO = new AppointmentDAO();

    // ─── Fallback sample data ───────────────────────────────────────────

    private List<Doctor> getSampleDoctors() {
        List<Doctor> list = new ArrayList<>();
        list.add(new Doctor(2, "Dr. Sonam Dubey", "dr_dubey", "", "Cardiologist", 800.00));
        list.add(new Doctor(3, "Dr. Shivam Singh", "dr_singh", "", "Neurologist", 900.00));
        list.add(new Doctor(4, "Dr. Aarti Sharma", "dr_sharma", "", "Dermatologist", 650.00));
        return list;
    }

    private List<Appointment> getSamplePatientAppointments() {
        List<Appointment> list = new ArrayList<>();
        Doctor d1 = new Doctor(2, "Dr. Sonam Dubey", "dr_dubey", "", "Cardiologist", 800.00);
        Doctor d2 = new Doctor(3, "Dr. Shivam Singh", "dr_singh", "", "Neurologist", 900.00);
        Patient self = getLoggedInPatient();
        list.add(new Appointment(1, self, d1, "2026-03-25", "10:00:00", "Pending"));
        list.add(new Appointment(2, self, d2, "2026-03-28", "14:30:00", "Confirmed"));
        return list;
    }

    // ─── Helpers to build rows visually ─────────────────────────────────

    private Label makeHeaderCell(String text) {
        Label l = new Label(text);
        l.setStyle("-fx-font-weight: bold; -fx-font-size: 13px; -fx-text-fill: #6B7280; -fx-padding: 10;");
        l.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(l, Priority.ALWAYS);
        return l;
    }

    private Label makeDataCell(String text) {
        Label l = new Label(text);
        l.setStyle("-fx-font-size: 14px; -fx-text-fill: #1F2937; -fx-padding: 10;");
        l.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(l, Priority.ALWAYS);
        return l;
    }

    private HBox makeRow(String... values) {
        HBox row = new HBox();
        row.setStyle("-fx-background-color: white; -fx-border-color: transparent transparent #F3F4F6 transparent; -fx-border-width: 1;");
        row.setAlignment(Pos.CENTER_LEFT);
        for (String v : values) {
            row.getChildren().add(makeDataCell(v));
        }
        return row;
    }

    private HBox makeHeaderRow(String... titles) {
        HBox row = new HBox();
        row.setStyle("-fx-background-color: #F9FAFB; -fx-border-color: transparent transparent #E5E7EB transparent; -fx-border-width: 1;");
        row.setAlignment(Pos.CENTER_LEFT);
        for (String t : titles) {
            row.getChildren().add(makeHeaderCell(t));
        }
        return row;
    }

    // ─── Initialize ─────────────────────────────────────────────────────

    @FXML
    public void initialize() {
        // Load doctors — DB first, then fallback
        List<Doctor> doctors = null;
        try {
            doctors = doctorDAO.getAllDoctors();
        } catch (Exception e) {
            System.err.println("[PatientController] DB error loading doctors: " + e.getMessage());
        }

        if (doctors == null || doctors.isEmpty()) {
            doctors = getSampleDoctors();
        }

        doctorBox.getItems().addAll(doctors);

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

        timeSlot.getItems().addAll("10:00:00", "10:30:00", "11:00:00", "11:30:00", "12:00:00",
                                   "14:00:00", "14:30:00", "15:00:00", "15:30:00", "16:00:00");

        javafx.application.Platform.runLater(this::showMyAppointments);
    }

    private Patient getLoggedInPatient() {
        com.hospital.models.User sessionUser = Session.getLoggedInUser();
        if (sessionUser instanceof Patient) {
            return (Patient) sessionUser;
        }
        return new Patient(4, "Aditya Singh", "aditya", "patient123", "O+", "1234567890", 30);
    }

    // ─── Book Appointment ───────────────────────────────────────────────

    @FXML
    public void bookAppointment() {
        Doctor selectedDoctor = doctorBox.getValue();
        java.time.LocalDate selectedDate = datePicker.getValue();
        String selectedTime = timeSlot.getValue();

        if (selectedDoctor == null || selectedDate == null || selectedTime == null) {
            Alert alert = new Alert(AlertType.WARNING, "Please select Doctor, Date, and Time.");
            alert.show();
            return;
        }

        Patient patient = getLoggedInPatient();
        try {
            Appointment app = new Appointment(
                0, patient, selectedDoctor,
                java.sql.Date.valueOf(selectedDate).toString(),
                java.sql.Time.valueOf(selectedTime).toString(),
                "Pending"
            );

            boolean success = appointmentDAO.bookAppointment(app);
            if (success) {
                Alert alert = new Alert(AlertType.INFORMATION, "Appointment Booked Successfully!");
                alert.show();
                doctorBox.getSelectionModel().clearSelection();
                datePicker.setValue(null);
                timeSlot.getSelectionModel().clearSelection();
                showMyAppointments();
            } else {
                Alert alert = new Alert(AlertType.ERROR, "Failed to book appointment.");
                alert.show();
            }
        } catch (Exception e) {
            String msg = e.getMessage() != null ? e.getMessage() : "Unknown error";
            if (msg.contains("unique_doctor_slot")) {
                Alert alert = new Alert(AlertType.WARNING, "This doctor is already booked for this date/time.\nPlease choose a different slot.");
                alert.show();
            } else {
                Alert alert = new Alert(AlertType.ERROR, "Unexpected error: " + msg);
                alert.show();
            }
        }
    }

    // ─── Navigation ─────────────────────────────────────────────────────

    @FXML
    public void showBookingArea() {
        headerLabel.setText("Book an Appointment");
        subHeaderLabel.setText("Schedule your next visit cleanly and easily.");
        
        contentArea.getChildren().clear();
        contentArea.getChildren().addAll(headerLabel, subHeaderLabel, cardArea);
    }

    @FXML
    public void showMyAppointments() {
        try {
            headerLabel.setText("My Appointments");
            subHeaderLabel.setText("View your upcoming and past medical visits.");
            
            contentArea.getChildren().clear();
            contentArea.getChildren().addAll(headerLabel, subHeaderLabel);

            Patient patient = getLoggedInPatient();

            List<Appointment> apps = null;
            try {
                apps = appointmentDAO.getAppointmentsByPatient(patient.getId());
            } catch (Exception e) {}

            if (apps == null || apps.isEmpty()) {
                apps = getSamplePatientAppointments();
            }

            VBox table = new VBox();
            table.setStyle("-fx-border-color: #E5E7EB; -fx-border-radius: 6; -fx-background-color: white; -fx-background-radius: 6;");

            table.getChildren().add(makeHeaderRow("Doctor Name", "Date", "Time", "Status"));

            for (Appointment a : apps) {
                String dName = a.getDoctor() != null ? a.getDoctor().getName() : "N/A";
                table.getChildren().add(makeRow(dName, a.getDate(), a.getTime(), a.getStatus()));
            }

            subHeaderLabel.setText("View your upcoming and past medical visits. Found " + apps.size() + " records.");

            VBox card = new VBox();
            card.getStyleClass().add("card");
            VBox.setVgrow(card, Priority.ALWAYS);
            
            card.getChildren().add(table);
            contentArea.getChildren().add(card);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}