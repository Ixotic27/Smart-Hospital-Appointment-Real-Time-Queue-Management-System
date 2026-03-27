package com.hospital.controllers;

import com.hospital.dao.AppointmentDAO;
import com.hospital.dao.PatientDAO;
import com.hospital.models.Appointment;
import com.hospital.models.Patient;
import com.hospital.utils.Session;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.util.List;

public class DoctorController {

    @FXML private TableView<Appointment> appointmentTable;
    @FXML private Label headerLabel;
    @FXML private Label subHeaderLabel;
    @FXML private VBox cardArea;

    private AppointmentDAO appointmentDAO = new AppointmentDAO();
    private PatientDAO patientDAO = new PatientDAO();
    private boolean isViewingSchedule = true;
    private Thread liveQueueThread;
    private volatile boolean running = true;

    @FXML
    public void initialize() {
        System.out.println("Doctor Dashboard Loaded");

        if (appointmentTable != null && appointmentTable.getColumns().size() >= 3) {
            @SuppressWarnings("unchecked")
            TableColumn<Appointment, String> nameCol = (TableColumn<Appointment, String>) appointmentTable.getColumns().get(0);
            @SuppressWarnings("unchecked")
            TableColumn<Appointment, String> timeCol = (TableColumn<Appointment, String>) appointmentTable.getColumns().get(1);
            @SuppressWarnings("unchecked")
            TableColumn<Appointment, String> statusCol = (TableColumn<Appointment, String>) appointmentTable.getColumns().get(2);

            nameCol.setCellValueFactory(cellData -> 
                new SimpleStringProperty(cellData.getValue().getPatient() != null ? cellData.getValue().getPatient().getName() : "Unknown")
            );
            timeCol.setCellValueFactory(cellData -> 
                new SimpleStringProperty(cellData.getValue().getTime())
            );
            statusCol.setCellValueFactory(cellData -> 
                new SimpleStringProperty(cellData.getValue().getStatus())
            );
        }

        startLiveQueueThread();
    }

    private int getLoggedInDoctorId() {
        if (Session.getLoggedInUser() != null) {
            return Session.getLoggedInUser().getId();
        }
        return 2; // Fallback to test ID
    }

    private void refreshAppointments() {
        int docId = getLoggedInDoctorId();
        List<Appointment> apps = appointmentDAO.getAppointmentsByDoctor(docId);
        
        Platform.runLater(() -> {
            if (isViewingSchedule && appointmentTable != null) {
                appointmentTable.getItems().setAll(apps);
            }
        });
    }

    private void startLiveQueueThread() {
        liveQueueThread = new Thread(() -> {
            while (running) {
                try {
                    refreshAppointments();
                    // Refreshes every 5 seconds
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    // Thread interrupted, likely exiting
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        liveQueueThread.setDaemon(true); // Allow JVM to exit if this is the only running thread
        liveQueueThread.start();
    }

    @FXML
    public void showSchedule() {
        isViewingSchedule = true;
        headerLabel.setText("Doctor Dashboard");
        subHeaderLabel.setText("View and manage your daily appointments.");
        
        cardArea.getChildren().clear();
        Label title = new Label("Upcoming Appointments (Live)");
        title.getStyleClass().add("field-label");
        title.setStyle("-fx-font-size: 16px;");
        
        cardArea.getChildren().add(title);
        cardArea.getChildren().add(appointmentTable);
        
        refreshAppointments();
    }

    @FXML
    public void showPatients() {
        isViewingSchedule = false;
        headerLabel.setText("My Patients");
        subHeaderLabel.setText("Directory of patients registered in the hospital.");
        
        cardArea.getChildren().clear();

        TableView<Patient> table = new TableView<>();
        table.getStyleClass().add("table-view");
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Patient, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Patient, String> bloodCol = new TableColumn<>("Blood Group");
        bloodCol.setCellValueFactory(new PropertyValueFactory<>("bloodGroup"));

        TableColumn<Patient, String> contactCol = new TableColumn<>("Contact Number");
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));

        table.getColumns().addAll(nameCol, bloodCol, contactCol);

        List<Patient> patients = patientDAO.getAllPatients();
        table.getItems().addAll(patients);

        cardArea.getChildren().add(table);
        VBox.setVgrow(table, javafx.scene.layout.Priority.ALWAYS);
    }

    // Call this if navigating away from dashboard to clean up gracefully
    public void stopThread() {
        running = false;
        if (liveQueueThread != null) {
            liveQueueThread.interrupt();
        }
    }
}