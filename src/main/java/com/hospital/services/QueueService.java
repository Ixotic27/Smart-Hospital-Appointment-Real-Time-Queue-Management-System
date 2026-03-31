package com.hospital.services;

import com.hospital.dao.AppointmentDAO;
import com.hospital.models.Appointment;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class to manage the real-time queue logic for the hospital system.
 */
public class QueueService {

    private final AppointmentDAO appointmentDAO;

    public QueueService() {
        this.appointmentDAO = new AppointmentDAO();
    }

    /**
     * Retrieves the live, active queue for a specific doctor.
     * This filters out completed or cancelled appointments,
     * showing only the upcoming 'Pending' or 'Confirmed' appointments.
     *
     * @param doctorId The ID of the doctor
     * @return A list of active appointments representing the current queue
     */
    public List<Appointment> getLiveQueue(int doctorId) {
        ArrayList<Appointment> allAppointments = appointmentDAO.getAppointmentsByDoctor(doctorId);

        // Filter to only include active appointments
        return allAppointments.stream()
                .filter(app -> "Pending".equalsIgnoreCase(app.getStatus())
                        || "Confirmed".equalsIgnoreCase(app.getStatus()))
                .collect(Collectors.toList());
    }

    /**
     * Gets the currently active appointment (the first one in the queue).
     *
     * @param doctorId The ID of the doctor
     * @return The next Appointment in line, or null if the queue is empty
     */
    public Appointment getCurrentAppointment(int doctorId) {
        List<Appointment> queue = getLiveQueue(doctorId);
        if (queue != null && !queue.isEmpty()) {
            return queue.get(0);
        }
        return null;
    }

    /**
     * Advances the queue by marking the specified appointment as completed.
     * 
     * @param appointmentId The ID of the appointment to mark as completed
     * @return true if successful, false otherwise
     */
    public boolean advanceQueue(int appointmentId) {
        // Marking as Completed removes it from the "Live Queue"
        return appointmentDAO.updateAppointmentStatus(appointmentId, "Completed");
    }

    /**
     * Calculates the estimated wait time for a given patient's appointment.
     * Assumes an average time of 15 minutes per appointment.
     * 
     * @param doctorId      The doctor's ID
     * @param appointmentId The specific appointment ID to check in the queue
     * @return Estimated wait time in minutes. Returns -1 if the appointment is not
     *         in the active queue.
     */
    public int getEstimatedWaitTime(int doctorId, int appointmentId) {
        List<Appointment> queue = getLiveQueue(doctorId);
        int position = 0;

        for (Appointment app : queue) {
            if (app.getAppointmentId() == appointmentId) {
                // Assuming 15 minutes per appointment for everyone ahead of this patient
                return position * 15;
            }
            position++;
        }

        return -1; // Appointment not found in active queue
    }
}
