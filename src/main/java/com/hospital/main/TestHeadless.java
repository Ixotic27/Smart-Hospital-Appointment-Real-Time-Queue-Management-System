package com.hospital.main;

import javafx.application.Application;
import javafx.stage.Stage;

public class TestHeadless extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("JavaFX Headless Test Started!");
        // We will just try loading the Patient Dashboard!
        com.hospital.utils.Session.setLoggedInUser(new com.hospital.models.Patient(4, "John Doe", "johndoe", "pass", "O+", "1", 30));
        
        javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/views/PatientBooking.fxml"));
        javafx.scene.Parent root = loader.load();
        System.out.println("PatientBooking FXML Loaded!");
        try { Thread.sleep(2000); } catch(Exception e) {}
        System.out.println("JavaFX Test Successful. Exiting...");
        System.exit(0);
    }
}
