package com.hospital.dao;

import com.hospital.models.Appointment;
import com.hospital.utils.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;

public class AppointmentDAO {

    public boolean bookAppointment(Appointment appointment) {
        String sql = "INSERT INTO Appointments (patient_id, doctor_id, appointment_date, appointment_time, status) VALUES (?, ?, ?, ?, 'Pending')";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, appointment.getPatient().getId());
            stmt.setInt(2, appointment.getDoctor().getId());
            stmt.setDate(3, Date.valueOf(appointment.getDate()));
            stmt.setTime(4, Time.valueOf(appointment.getTime()));
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Appointment> getAppointmentsByDoctor(int doctorId) {
        ArrayList<Appointment> list = new ArrayList<>();
        String sql = "SELECT a.*, u.name as patient_name FROM Appointments a JOIN Users u ON a.patient_id = u.id WHERE a.doctor_id = ? ORDER BY a.appointment_date, a.appointment_time";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, doctorId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    com.hospital.models.Patient p = new com.hospital.models.Patient(
                        rs.getInt("patient_id"), rs.getString("patient_name"), "", "", "", "", 0
                    );
                    Appointment app = new Appointment(
                        rs.getInt("id"),
                        p,
                        null,
                        rs.getDate("appointment_date").toString(),
                        rs.getTime("appointment_time").toString(),
                        rs.getString("status")
                    );
                    list.add(app);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean updateAppointmentStatus(int appointmentId, String newStatus) {
        String sql = "UPDATE Appointments SET status = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, newStatus);
            stmt.setInt(2, appointmentId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}