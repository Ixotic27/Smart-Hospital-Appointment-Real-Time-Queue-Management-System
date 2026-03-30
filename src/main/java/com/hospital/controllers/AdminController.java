package com.hospital.controllers;

import com.hospital.dao.DoctorDAO;
import com.hospital.dao.PatientDAO;
import com.hospital.dao.AppointmentDAO;
import com.hospital.models.Doctor;
import com.hospital.models.Patient;
import com.hospital.models.Appointment;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class AdminController {

    @FXML
    private Label headerLabel;
    @FXML
    private Label subHeaderLabel;
    @FXML
    private VBox cardArea;

    private DoctorDAO doctorDAO = new DoctorDAO();
    private PatientDAO patientDAO = new PatientDAO();
    private AppointmentDAO appointmentDAO = new AppointmentDAO();

    // Fallback sample data
    private List<Doctor> getSampleDoctors() {
        List<Doctor> list = new ArrayList<>();
        list.add(new Doctor(1, "Dr. Sonam Dubey", "dr_dubey", "", "Cardiologist", 800.00));
        list.add(new Doctor(2, "Dr. Shivam Singh", "dr_singh", "", "Neurologist", 900.00));
        list.add(new Doctor(3, "Dr. Emily Watson", "dr_watson", "", "Dermatologist", 650.00));
        list.add(new Doctor(4, "Dr. Michael Brown", "dr_brown", "", "Orthopedic", 750.00));
        return list;
    }

    private List<Patient> getSamplePatients() {
        List<Patient> list = new ArrayList<>();
        list.add(new Patient(1, "Aditya Singh", "aditya", "", "O+", "1234567890", 30));
        list.add(new Patient(2, "Vivek Bora", "vivek", "", "A-", "0987654321", 25));
        list.add(new Patient(3, "Alice Johnson", "alicej", "", "B+", "5551234567", 45));
        list.add(new Patient(4, "Bob Williams", "bobw", "", "AB+", "5559876543", 38));
        return list;
    }

    private List<Appointment> getSampleAppointments() {
        List<Appointment> list = new ArrayList<>();
        Patient p1 = new Patient(1, "Aditya Singh", "aditya", "", "O+", "1234567890", 30);
        Patient p2 = new Patient(2, "Vivek Bora", "vivek", "", "A-", "0987654321", 25);
        Doctor d1 = new Doctor(1, "Dr. Sonam Dubey", "dr_dubey", "", "Cardiologist", 800.00);
        Doctor d2 = new Doctor(2, "Dr. Shivam Singh", "dr_singh", "", "Neurologist", 900.00);
        list.add(new Appointment(1, p1, d1, "2026-03-25", "10:00:00", "Pending"));
        list.add(new Appointment(2, p2, d2, "2026-03-26", "14:30:00", "Confirmed"));
        list.add(new Appointment(3, p1, d2, "2026-03-28", "09:00:00", "Completed"));
        return list;
    }

    // Helpers to build rows visually

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
        row.setStyle(
                "-fx-background-color: white; -fx-border-color: transparent transparent #F3F4F6 transparent; -fx-border-width: 1;");
        row.setAlignment(Pos.CENTER_LEFT);
        for (String v : values) {
            row.getChildren().add(makeDataCell(v));
        }
        return row;
    }

    private HBox makeHeaderRow(String... titles) {
        HBox row = new HBox();
        row.setStyle(
                "-fx-background-color: #F9FAFB; -fx-border-color: transparent transparent #E5E7EB transparent; -fx-border-width: 1;");
        row.setAlignment(Pos.CENTER_LEFT);
        for (String t : titles) {
            row.getChildren().add(makeHeaderCell(t));
        }
        return row;
    }

    // Initialize

    @FXML
    public void initialize() {
        System.out.println("Admin Dashboard Loaded — auto-loading doctors");
        showDoctors();
    }

    // Show Doctors

    @FXML
    public void showDoctors() {
        headerLabel.setText("Doctors Overview");
        subHeaderLabel.setText("Manage hospital doctors and their specializations.");
        cardArea.getChildren().clear();

        List<Doctor> doctors = null;
        try {
            doctors = doctorDAO.getAllDoctors();
            System.out.println("[Admin] Doctors from DB: " + (doctors == null ? "null" : doctors.size()));
        } catch (Exception e) {
            System.err.println("[Admin] DB error: " + e.getMessage());
        }
        if (doctors == null || doctors.isEmpty()) {
            doctors = getSampleDoctors();
            System.out.println("[Admin] Using sample doctors (" + doctors.size() + ")");
        }

        // Build table manually with Labels
        VBox table = new VBox();
        table.setStyle(
                "-fx-border-color: #E5E7EB; -fx-border-radius: 6; -fx-background-color: white; -fx-background-radius: 6;");

        table.getChildren().add(makeHeaderRow("Name", "Specialization", "Fee"));

        for (Doctor doc : doctors) {
            table.getChildren().add(makeRow(
                    doc.getName(),
                    doc.getSpecialization(),
                    "$" + doc.getConsultationFee()));
        }

        Label countLabel = new Label("Total: " + doctors.size() + " doctors");
        countLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #9CA3AF; -fx-padding: 10 0 0 0;");

        cardArea.getChildren().addAll(table, countLabel);
    }

    // Show Patients

    @FXML
    public void showPatients() {
        headerLabel.setText("Patients Directory");
        subHeaderLabel.setText("View registered patients and details.");
        cardArea.getChildren().clear();

        List<Patient> patients = null;
        try {
            patients = patientDAO.getAllPatients();
            System.out.println("[Admin] Patients from DB: " + (patients == null ? "null" : patients.size()));
        } catch (Exception e) {
            System.err.println("[Admin] DB error: " + e.getMessage());
        }
        if (patients == null || patients.isEmpty()) {
            patients = getSamplePatients();
            System.out.println("[Admin] Using sample patients (" + patients.size() + ")");
        }

        VBox table = new VBox();
        table.setStyle(
                "-fx-border-color: #E5E7EB; -fx-border-radius: 6; -fx-background-color: white; -fx-background-radius: 6;");

        table.getChildren().add(makeHeaderRow("Name", "Blood Group", "Contact", "Age"));

        for (Patient p : patients) {
            table.getChildren().add(makeRow(
                    p.getName(),
                    p.getBloodGroup() != null ? p.getBloodGroup() : "N/A",
                    p.getContactNumber() != null ? p.getContactNumber() : "N/A",
                    String.valueOf(p.getAge())));
        }

        Label countLabel = new Label("Total: " + patients.size() + " patients");
        countLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #9CA3AF; -fx-padding: 10 0 0 0;");

        cardArea.getChildren().addAll(table, countLabel);
    }

    // Show Appointments

    @FXML
    public void showAppointments() {
        headerLabel.setText("Global Appointments");
        subHeaderLabel.setText("Review all hospital appointments.");
        cardArea.getChildren().clear();

        List<Appointment> apps = null;
        try {
            apps = appointmentDAO.getAllAppointments();
            System.out.println("[Admin] Appointments from DB: " + (apps == null ? "null" : apps.size()));
        } catch (Exception e) {
            System.err.println("[Admin] DB error: " + e.getMessage());
        }
        if (apps == null || apps.isEmpty()) {
            apps = getSampleAppointments();
            System.out.println("[Admin] Using sample appointments (" + apps.size() + ")");
        }

        VBox table = new VBox();
        table.setStyle(
                "-fx-border-color: #E5E7EB; -fx-border-radius: 6; -fx-background-color: white; -fx-background-radius: 6;");

        table.getChildren().add(makeHeaderRow("Patient", "Doctor", "Date", "Time", "Status"));

        for (Appointment a : apps) {
            String pName = a.getPatient() != null ? a.getPatient().getName() : "N/A";
            String dName = a.getDoctor() != null ? a.getDoctor().getName() : "N/A";
            table.getChildren().add(makeRow(pName, dName, a.getDate(), a.getTime(), a.getStatus()));
        }

        Label countLabel = new Label("Total: " + apps.size() + " appointments");
        countLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #9CA3AF; -fx-padding: 10 0 0 0;");

        cardArea.getChildren().addAll(table, countLabel);
    }
}