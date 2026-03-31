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
            System.err.println("[AppointmentDAO] SQL Error: " + e.getMessage() + " (SQLState=" + e.getSQLState() + ")");
            e.printStackTrace();
            throw new RuntimeException("Database error: " + e.getMessage(), e);
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
                            rs.getInt("patient_id"), rs.getString("patient_name"), "", "", "", "", 0);
                    Appointment app = new Appointment(
                            rs.getInt("id"),
                            p,
                            null,
                            rs.getDate("appointment_date").toString(),
                            rs.getTime("appointment_time").toString(),
                            rs.getString("status"));
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

    public ArrayList<Appointment> getAllAppointments() {
        ArrayList<Appointment> list = new ArrayList<>();
        String sql = "SELECT a.*, p_u.name as patient_name, d_u.name as doctor_name FROM Appointments a " +
                "JOIN Users p_u ON a.patient_id = p_u.id " +
                "JOIN Users d_u ON a.doctor_id = d_u.id " +
                "ORDER BY a.appointment_date, a.appointment_time";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                com.hospital.models.Patient p = new com.hospital.models.Patient(
                        rs.getInt("patient_id"), rs.getString("patient_name"), "", "", "", "", 0);
                com.hospital.models.Doctor d = new com.hospital.models.Doctor(
                        rs.getInt("doctor_id"), rs.getString("doctor_name"), "", "", "", 0.0);
                Appointment app = new Appointment(
                        rs.getInt("id"), p, d,
                        rs.getDate("appointment_date").toString(),
                        rs.getTime("appointment_time").toString(),
                        rs.getString("status"));
                list.add(app);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<Appointment> getAppointmentsByPatient(int patientId) {
        ArrayList<Appointment> list = new ArrayList<>();
        String sql = "SELECT a.*, p_u.name as patient_name, d_u.name as doctor_name FROM Appointments a " +
                "JOIN Users p_u ON a.patient_id = p_u.id " +
                "JOIN Users d_u ON a.doctor_id = d_u.id " +
                "WHERE a.patient_id = ? " +
                "ORDER BY a.appointment_date, a.appointment_time";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, patientId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    com.hospital.models.Patient p = new com.hospital.models.Patient(
                            rs.getInt("patient_id"), rs.getString("patient_name"), "", "", "", "", 0);
                    com.hospital.models.Doctor d = new com.hospital.models.Doctor(
                            rs.getInt("doctor_id"), rs.getString("doctor_name"), "", "", "", 0.0);
                    Appointment app = new Appointment(
                            rs.getInt("id"), p, d,
                            rs.getDate("appointment_date").toString(),
                            rs.getTime("appointment_time").toString(),
                            rs.getString("status"));
                    list.add(app);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}