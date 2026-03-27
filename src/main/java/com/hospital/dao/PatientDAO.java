package com.hospital.dao;

import com.hospital.models.Patient;
import com.hospital.utils.DatabaseConnection;
import java.sql.*;

public class PatientDAO {

    public Patient getPatientById(int id) {
        String sql = "SELECT u.*, p.bloodGroup, p.contactNumber, p.age FROM Users u JOIN PatientDetails p ON u.id = p.patient_id WHERE u.id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Patient(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("bloodGroup"),
                        rs.getString("contactNumber"),
                        rs.getInt("age")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public java.util.ArrayList<Patient> getAllPatients() {
        java.util.ArrayList<Patient> patients = new java.util.ArrayList<>();
        String sql = "SELECT u.*, p.bloodGroup, p.contactNumber, p.age FROM Users u JOIN PatientDetails p ON u.id = p.patient_id WHERE u.role = 'Patient'";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                patients.add(new Patient(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("bloodGroup"),
                    rs.getString("contactNumber"),
                    rs.getInt("age")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patients;
    }
}