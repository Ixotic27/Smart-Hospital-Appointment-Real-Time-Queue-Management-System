package com.hospital.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL fxmlLocation = getClass().getResource("/views/Login.fxml");
        if (fxmlLocation == null) {
            System.err.println("Could not find /views/Login.fxml!");
            return;
        }
        Parent root = FXMLLoader.load(fxmlLocation);
        primaryStage.setTitle("Smart Hospital Appointment & Queue Management");
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
