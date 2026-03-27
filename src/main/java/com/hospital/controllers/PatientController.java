package com.hospital.controllers;

import com.hospital.dao.DoctorDAO;
import com.hospital.dao.AppointmentDAO;
import com.hospital.models.Doctor;
import com.hospital.models.Patient;
import com.hospital.models.Appointment;
import com.hospital.utils.Session;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import java.util.List;
import javafx.util.StringConverter;

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

    private Patient getLoggedInPatient() {
        com.hospital.models.User sessionUser = Session.getLoggedInUser();
        System.out.println("[Session] Logged in user: " + (sessionUser != null ? sessionUser.getUsername() + " (" + sessionUser.getClass().getSimpleName() + ")" : "null"));
        if (sessionUser instanceof Patient) {
            return (Patient) sessionUser;
        }
        // Fallback dummy ID=4 if session is missing or not a Patient type
        System.out.println("[Session] WARNING: Not a Patient instance, using dummy patient ID=4");
        return new Patient(4, "John Doe", "johndoe", "patient123", "O+", "1234567890", 30);
    }

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
        System.out.println("[Booking] patient_id=" + patient.getId() + ", doctor_id=" + selectedDoctor.getId() +
                ", date=" + selectedDate + ", time=" + selectedTime);

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
                // Reset form
                doctorBox.getSelectionModel().clearSelection();
                datePicker.setValue(null);
                timeSlot.getSelectionModel().clearSelection();
            } else {
                Alert alert = new Alert(AlertType.ERROR,
                        "Failed to book appointment.\n" +
                        "The slot may be already taken (unique constraint) or DB is unreachable.\n" +
                        "Try choosing a different time slot or date.");
                alert.setTitle("Booking Failed");
                alert.setHeaderText("Could Not Book Appointment");
                alert.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(AlertType.ERROR, "Unexpected error: " + e.getMessage());
            alert.show();
        }
    }

    @FXML
    public void showBookingArea() {
        headerLabel.setText("Book an Appointment");
        subHeaderLabel.setText("Schedule your next visit cleanly and easily without any hassle.");
        
        contentArea.getChildren().clear();
        contentArea.getChildren().addAll(headerLabel, subHeaderLabel, cardArea);
    }

    @FXML
    public void showMyAppointments() {
        headerLabel.setText("My Appointments");
        subHeaderLabel.setText("View your upcoming and past medical visits.");
        
        contentArea.getChildren().clear();
        contentArea.getChildren().addAll(headerLabel, subHeaderLabel);

        TableView<Appointment> table = new TableView<>();
        table.getStyleClass().add("table-view");
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Appointment, String> doctorCol = new TableColumn<>("Doctor Name");
        doctorCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDoctor().getName()));

        TableColumn<Appointment, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<Appointment, String> timeCol = new TableColumn<>("Time");
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));

        TableColumn<Appointment, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        table.getColumns().addAll(doctorCol, dateCol, timeCol, statusCol);

        Patient patient = getLoggedInPatient();
        List<Appointment> apps = appointmentDAO.getAppointmentsByPatient(patient.getId());
        table.getItems().addAll(apps);

        VBox card = new VBox();
        card.getStyleClass().add("card");
        VBox.setVgrow(card, javafx.scene.layout.Priority.ALWAYS);
        
        card.getChildren().add(table);
        VBox.setVgrow(table, javafx.scene.layout.Priority.ALWAYS);

        contentArea.getChildren().add(card);
    }
}