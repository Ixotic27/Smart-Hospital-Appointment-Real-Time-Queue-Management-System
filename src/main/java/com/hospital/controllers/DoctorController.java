package com.hospital.controllers;

import com.hospital.dao.AppointmentDAO;
import com.hospital.models.Appointment;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;

public class DoctorController {

    @FXML
    private TableView<Appointment> appointmentTable;

    private AppointmentDAO appointmentDAO = new AppointmentDAO();

    @FXML
    public void initialize() {
        System.out.println("Doctor Dashboard Loaded");

        // Assuming doctor ID 2 is a generic doctor for testing as logon session is missing
        int loggedInDoctorId = 2; // Can change based on DB seed

        if (appointmentTable != null && appointmentTable.getColumns().size() >= 3) {
            // Extract UI columns by index since explicit fx:ids are omitted from frontend
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

            List<Appointment> apps = appointmentDAO.getAppointmentsByDoctor(loggedInDoctorId);
            appointmentTable.getItems().addAll(apps);
        }
    }
}