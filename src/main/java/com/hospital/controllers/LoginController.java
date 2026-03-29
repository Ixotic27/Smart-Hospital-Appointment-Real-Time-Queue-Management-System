package com.hospital.controllers;

import com.hospital.dao.UserDAO;
import com.hospital.models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    private UserDAO userDAO = new UserDAO();

    @FXML
    public void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        
        if (username.trim().isEmpty() || password.trim().isEmpty()) {
            errorLabel.setStyle("-fx-text-fill: red;");
            errorLabel.setText("Please enter both username and password.");
            return;
        }

        try {
            User user = userDAO.getUserByUsername(username);

            if (user != null && user.getPassword().equals(password)) {
                errorLabel.setText("Login successful! Role: " + user.getRole());

                // Set global session
                com.hospital.utils.Session.setLoggedInUser(user);

                // Load the appropriate dashboard
                String fxmlFile = "";
                if (user.getRole().equalsIgnoreCase("Admin")) {
                    fxmlFile = "/views/AdminDashboard.fxml";
                } else if (user.getRole().equalsIgnoreCase("Doctor")) {
                    fxmlFile = "/views/DoctorDashboard.fxml";
                } else if (user.getRole().equalsIgnoreCase("Patient")) {
                    fxmlFile = "/views/PatientBooking.fxml";
                }

                if (!fxmlFile.isEmpty()) {
                    try {
                        javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource(fxmlFile));
                        javafx.scene.Parent root = loader.load();
                        javafx.scene.Scene scene = new javafx.scene.Scene(root);
                        javafx.stage.Stage stage = (javafx.stage.Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                        stage.setScene(scene);
                        stage.show();
                    } catch (java.io.IOException e) {
                        e.printStackTrace();
                        errorLabel.setStyle("-fx-text-fill: red;");
                        errorLabel.setText("Failed to load dashboard.");
                    }
                }
            } else {
                errorLabel.setStyle("-fx-text-fill: red;");
                errorLabel.setText("Invalid username or password.");
            }
        } catch (Exception e) {
            errorLabel.setStyle("-fx-text-fill: red;");
            errorLabel.setText("Database connection failed. Is PostgreSQL running?");
            e.printStackTrace();
        }
    }
}