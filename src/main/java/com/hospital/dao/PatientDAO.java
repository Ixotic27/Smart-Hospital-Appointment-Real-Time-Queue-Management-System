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
}