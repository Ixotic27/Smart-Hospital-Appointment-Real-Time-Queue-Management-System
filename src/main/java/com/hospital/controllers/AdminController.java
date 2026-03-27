package com.hospital.controllers;

import com.hospital.dao.DoctorDAO;
import com.hospital.dao.PatientDAO;
import com.hospital.dao.AppointmentDAO;
import com.hospital.models.Doctor;
import com.hospital.models.Patient;
import com.hospital.models.Appointment;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
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

    @FXML
    public void initialize() {
        System.out.println("Admin Dashboard Loaded");
    }

    @FXML
    public void showDoctors() {
        headerLabel.setText("Doctors Overview");
        subHeaderLabel.setText("Manage hospital doctors and their specializations.");
        cardArea.getChildren().clear();

        TableView<Doctor> table = new TableView<>();
        table.getStyleClass().add("table-view");
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Doctor, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Doctor, String> specCol = new TableColumn<>("Specialization");
        specCol.setCellValueFactory(new PropertyValueFactory<>("specialization"));

        TableColumn<Doctor, String> feeCol = new TableColumn<>("Fee");
        feeCol.setCellValueFactory(cellData -> new SimpleStringProperty("$" + cellData.getValue().getConsultationFee()));

        table.getColumns().add(nameCol);
        table.getColumns().add(specCol);
        table.getColumns().add(feeCol);

        List<Doctor> doctors = doctorDAO.getAllDoctors();
        table.getItems().addAll(doctors);

        cardArea.getChildren().add(table);
        VBox.setVgrow(table, javafx.scene.layout.Priority.ALWAYS);
    }

    @FXML
    public void showPatients() {
        headerLabel.setText("Patients Directory");
        subHeaderLabel.setText("View registered patients and details.");
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

        table.getColumns().add(nameCol);
        table.getColumns().add(bloodCol);
        table.getColumns().add(contactCol);

        List<Patient> patients = patientDAO.getAllPatients();
        table.getItems().addAll(patients);

        cardArea.getChildren().add(table);
        VBox.setVgrow(table, javafx.scene.layout.Priority.ALWAYS);
    }

    @FXML
    public void showAppointments() {
        headerLabel.setText("Global Appointments");
        subHeaderLabel.setText("Review all hospital appointments.");
        cardArea.getChildren().clear();

        TableView<Appointment> table = new TableView<>();
        table.getStyleClass().add("table-view");
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Appointment, String> patientCol = new TableColumn<>("Patient Name");
        patientCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPatient().getName()));

        TableColumn<Appointment, String> doctorCol = new TableColumn<>("Doctor Name");
        doctorCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDoctor().getName()));

        TableColumn<Appointment, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<Appointment, String> timeCol = new TableColumn<>("Time");
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));

        TableColumn<Appointment, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        table.getColumns().add(patientCol);
        table.getColumns().add(doctorCol);
        table.getColumns().add(dateCol);
        table.getColumns().add(timeCol);
        table.getColumns().add(statusCol);

        List<Appointment> apps = appointmentDAO.getAllAppointments();
        table.getItems().addAll(apps);

        cardArea.getChildren().add(table);
        VBox.setVgrow(table, javafx.scene.layout.Priority.ALWAYS);
    }
}