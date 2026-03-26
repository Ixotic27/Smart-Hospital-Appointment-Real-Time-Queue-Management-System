package com.hospital.dao;

import com.hospital.models.Doctor;
import com.hospital.utils.DatabaseConnection;
import java.sql.*;

public class DoctorDAO {

    public Doctor getDoctorById(int id) {
        String sql = "SELECT u.*, d.specialization, d.consultationFee FROM Users u JOIN DoctorDetails d ON u.id = d.doctor_id WHERE u.id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Doctor(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("specialization"),
                        rs.getDouble("consultationFee")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public java.util.ArrayList<Doctor> getAllDoctors() {
        java.util.ArrayList<Doctor> doctors = new java.util.ArrayList<>();
        String sql = "SELECT u.*, d.specialization, d.consultationFee FROM Users u JOIN DoctorDetails d ON u.id = d.doctor_id WHERE u.role = 'Doctor'";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                doctors.add(new Doctor(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("specialization"),
                    rs.getDouble("consultationFee")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return doctors;
    }
}