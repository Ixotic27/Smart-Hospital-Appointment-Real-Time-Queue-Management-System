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
                errorLabel.setStyle("-fx-text-fill: green;");
                errorLabel.setText("Login successful! Role: " + user.getRole());
                
                // TODO: Load the appropriate dashboard (AdminDashboard.fxml, etc.)
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