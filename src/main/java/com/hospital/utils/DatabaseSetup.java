package com.hospital.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseSetup {
    public static void main(String[] args) {
        String insertSQL = "INSERT INTO Users (name, username, password, role) VALUES (?, ?, ?, ?) ON CONFLICT (username) DO NOTHING";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(insertSQL)) {
             
            System.out.println("Connecting to Database...");
            
            // Admin
            stmt.setString(1, "System Admin");
            stmt.setString(2, "admin");
            stmt.setString(3, "admin123");
            stmt.setString(4, "Admin");
            stmt.addBatch();

            // Doctor 1
            stmt.setString(1, "Dr. Sarah Jenkins");
            stmt.setString(2, "dr_jenkins");
            stmt.setString(3, "doctorPass");
            stmt.setString(4, "Doctor");
            stmt.addBatch();

            // Patient 1
            stmt.setString(1, "John Doe");
            stmt.setString(2, "johndoe");
            stmt.setString(3, "patient123");
            stmt.setString(4, "Patient");
            stmt.addBatch();

            stmt.executeBatch();
            System.out.println("Successfully generated the test accounts in the PostgreSQL Database!");
            System.out.println("You can now login to the JavaFX app using these credentials.");
            
        } catch (SQLException e) {
            System.err.println("Failed to connect to the database. Error: " + e.getMessage());
            System.err.println("Did you create the 'hospitaldb' database and run schema.sql inside pgAdmin?");
            e.printStackTrace();
        }
    }
}
