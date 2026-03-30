package com.hospital.controllers;

import com.hospital.dao.AppointmentDAO;
import com.hospital.dao.PatientDAO;
import com.hospital.models.Appointment;
import com.hospital.models.Doctor;
import com.hospital.models.Patient;
import com.hospital.utils.Session;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class DoctorController {

    @FXML private Label headerLabel;
    @FXML private Label subHeaderLabel;
    @FXML private VBox cardArea;

    private AppointmentDAO appointmentDAO = new AppointmentDAO();
    private PatientDAO patientDAO = new PatientDAO();
    private boolean isViewingSchedule = true;
    private Thread liveQueueThread;
    private volatile boolean running = true;

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

    @FXML
    public void initialize() {
        System.out.println("Doctor Dashboard Loaded");
        startLiveQueueThread();
    }

    private int getLoggedInDoctorId() {
        if (Session.getLoggedInUser() != null) {
            return Session.getLoggedInUser().getId();
        }
        return 2; // Fallback
    }

    private List<Appointment> getSampleDoctorAppointments() {
        List<Appointment> list = new ArrayList<>();
        Patient p1 = new Patient(1, "Aditya Singh", "aditya", "", "O+", "1234567890", 30);
        Patient p2 = new Patient(2, "Vivek Bora", "vivek", "", "A-", "0987654321", 25);
        Doctor d = new Doctor(2, "Dr. Sonam Dubey", "dr_dubey", "", "Cardiologist", 800.00);
        list.add(new Appointment(1, p1, d, "2026-03-25", "10:00:00", "Pending"));
        list.add(new Appointment(2, p2, d, "2026-03-26", "14:30:00", "Confirmed"));
        return list;
    }

    private List<Patient> getSamplePatients() {
        List<Patient> list = new ArrayList<>();
        list.add(new Patient(1, "Aditya Singh", "aditya", "", "O+", "1234567890", 30));
        list.add(new Patient(2, "Vivek Bora", "vivek", "", "A-", "0987654321", 25));
        list.add(new Patient(3, "Alice Johnson", "alicej", "", "B+", "5551234567", 45));
        return list;
    }

    private void refreshAppointments() {
        int docId = getLoggedInDoctorId();
        List<Appointment> apps = null;
        try {
            apps = appointmentDAO.getAppointmentsByDoctor(docId);
        } catch (Exception e) {}

        if (apps == null || apps.isEmpty()) {
            apps = getSampleDoctorAppointments();
        }

        final List<Appointment> finalApps = apps;
        Platform.runLater(() -> {
            if (isViewingSchedule) {
                renderAppointments(finalApps);
            }
        });
    }

    private void renderAppointments(List<Appointment> apps) {
        cardArea.getChildren().clear();
        Label title = new Label("Upcoming Appointments (Live)");
        title.getStyleClass().add("field-label");
        title.setStyle("-fx-font-size: 16px;");

        VBox table = new VBox();
        table.setStyle("-fx-border-color: #E5E7EB; -fx-border-radius: 6; -fx-background-color: white; -fx-background-radius: 6;");
        table.getChildren().add(makeHeaderRow("Patient Name", "Time", "Status"));

        for (Appointment a : apps) {
            String pName = a.getPatient() != null ? a.getPatient().getName() : "N/A";
            table.getChildren().add(makeRow(pName, a.getTime(), a.getStatus()));
        }

        cardArea.getChildren().addAll(title, table);
    }

    private void startLiveQueueThread() {
        liveQueueThread = new Thread(() -> {
            while (running) {
                try {
                    refreshAppointments();
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        liveQueueThread.setDaemon(true);
        liveQueueThread.start();
    }

    @FXML
    public void showSchedule() {
        isViewingSchedule = true;
        headerLabel.setText("Doctor Dashboard");
        subHeaderLabel.setText("View and manage your daily appointments.");
        
        refreshAppointments();
    }

    @FXML
    public void showPatients() {
        isViewingSchedule = false;
        headerLabel.setText("My Patients");
        subHeaderLabel.setText("Directory of patients registered in the hospital.");
        
        cardArea.getChildren().clear();

        List<Patient> patients = null;
        try {
            patients = patientDAO.getAllPatients();
        } catch (Exception e) {}

        if (patients == null || patients.isEmpty()) {
            patients = getSamplePatients();
        }

        VBox table = new VBox();
        table.setStyle("-fx-border-color: #E5E7EB; -fx-border-radius: 6; -fx-background-color: white; -fx-background-radius: 6;");
        table.getChildren().add(makeHeaderRow("Name", "Blood Group", "Contact Number"));

        for (Patient p : patients) {
            String bg = p.getBloodGroup() != null ? p.getBloodGroup() : "N/A";
            String cnt = p.getContactNumber() != null ? p.getContactNumber() : "N/A";
            table.getChildren().add(makeRow(p.getName(), bg, cnt));
        }

        cardArea.getChildren().add(table);
    }

    public void stopThread() {
        running = false;
        if (liveQueueThread != null) {
            liveQueueThread.interrupt();
        }
    }
}