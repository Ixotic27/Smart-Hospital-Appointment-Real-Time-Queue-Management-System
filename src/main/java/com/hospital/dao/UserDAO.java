package com.hospital.dao;

import com.hospital.models.Doctor;
import com.hospital.models.Patient;
import com.hospital.models.User;
import com.hospital.utils.DatabaseConnection;
import java.sql.*;

public class UserDAO {

    /**
     * Returns the correctly typed User subclass (Patient, Doctor, or anonymous User for Admin)
     * so that Session.getLoggedInUser() instanceof Patient / Doctor works correctly.
     */
    public User getUserByUsername(String username) {
        String sql = "SELECT u.*, " +
                     "d.specialization, d.consultationFee, " +
                     "p.bloodGroup, p.contactNumber, p.age " +
                     "FROM Users u " +
                     "LEFT JOIN DoctorDetails d  ON u.id = d.doctor_id " +
                     "LEFT JOIN PatientDetails p ON u.id = p.patient_id " +
                     "WHERE u.username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int    id       = rs.getInt("id");
                    String name     = rs.getString("name");
                    String uname    = rs.getString("username");
                    String password = rs.getString("password");
                    String role     = rs.getString("role");

                    if ("Doctor".equalsIgnoreCase(role)) {
                        return new Doctor(id, name, uname, password,
                                rs.getString("specialization"),
                                rs.getDouble("consultationFee"));
                    } else if ("Patient".equalsIgnoreCase(role)) {
                        return new Patient(id, name, uname, password,
                                rs.getString("bloodGroup"),
                                rs.getString("contactNumber"),
                                rs.getInt("age"));
                    } else {
                        // Admin — return anonymous subclass of abstract User
                        return new User(id, name, uname, password, role) {};
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean insertUser(User user) {
        String sql = "INSERT INTO Users (name, username, password, role) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getUsername());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getRole());

            int affected = stmt.executeUpdate();
            if (affected > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        user.setId(rs.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}