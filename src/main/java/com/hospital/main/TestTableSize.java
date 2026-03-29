package com.hospital.main;

import javafx.application.Application;
import javafx.stage.Stage;
import com.hospital.controllers.PatientController;

public class TestTableSize extends Application {
    public static void main(String[] args) {
        System.out.println("Starting JavaFX Test...");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("JavaFX Headless Test Started!");
        com.hospital.utils.Session
                .setLoggedInUser(new com.hospital.models.Patient(4, "Aditya Singh", "aditya", "pass", "O+", "1", 30));

        javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(
                getClass().getResource("/views/PatientBooking.fxml"));
        javafx.scene.Parent root = loader.load();

        javafx.application.Platform.runLater(() -> {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
            }
            com.hospital.controllers.PatientController pc = loader.getController();
            System.out.println("Controller loaded.");
            System.exit(0);
        });
    }
}
