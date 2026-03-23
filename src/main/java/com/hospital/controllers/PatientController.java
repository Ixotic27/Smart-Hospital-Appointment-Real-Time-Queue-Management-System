<<<<<<< Updated upstream
package com.hospital.controllers;
=======
package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
>>>>>>> Stashed changes

public class PatientController {

    @FXML
    private ComboBox doctorBox;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ComboBox timeSlot;

    @FXML
    public void bookAppointment() {

        System.out.println("Appointment Booked");

    }

}