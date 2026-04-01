package com.hospital.services;

import com.hospital.dao.AppointmentDAO;
import com.hospital.models.Appointment;

import java.util.ArrayList;
import java.util.List;

public class QueueService {

    private AppointmentDAO appointmentDAO;

    public QueueService() {
        appointmentDAO = new AppointmentDAO();
    }

    // Get active queue (Pending + Confirmed)
    public List<Appointment> getLiveQueue(int doctorId) {

        ArrayList<Appointment> allAppointments = appointmentDAO.getAppointmentsByDoctor(doctorId);
        List<Appointment> activeQueue = new ArrayList<>();

        for (int i = 0; i < allAppointments.size(); i++) {

            Appointment app = allAppointments.get(i);

            String status = app.getStatus();

            // check status safely
            if (status != null) {
                if (status.equalsIgnoreCase("Pending") || status.equalsIgnoreCase("Confirmed")) {
                    activeQueue.add(app);
                }
            }
        }

        return activeQueue;
    }

    // Get current patient (first in queue)
    public Appointment getCurrentAppointment(int doctorId) {

        List<Appointment> queue = getLiveQueue(doctorId);

        if (queue != null && queue.size() > 0) {
            return queue.get(0);
        }

        return null;
    }

    // Complete appointment (move queue forward)
    public boolean advanceQueue(int appointmentId) {

        // Mark as completed
        return appointmentDAO.updateAppointmentStatus(appointmentId, "Completed");
    }

    // Calculate wait time
    public int getEstimatedWaitTime(int doctorId, int appointmentId) {

        List<Appointment> queue = getLiveQueue(doctorId);

        int position = 0;

        for (int i = 0; i < queue.size(); i++) {

            Appointment app = queue.get(i);

            if (app.getAppointmentId() == appointmentId) {
                return position * 15; // 15 min per patient
            }

            position++;
        }

        return -1; // not found
    }
}
