package com.hospital.services;

import java.util.*;
import com.hospital.models.Appointment;

public class QueueService {
    Queue<Appointment> daily = new ArrayDeque<>();

    public void addAppointment(Appointment app) {
        daily.add(app);
    }

    public Appointment nextPatient() {
        return daily.poll();
    }

    public int getPosition(Appointment app) {
        int pos = 1;
        for (Appointment a : daily) {
            if (a.equals(app)) {
                return pos;
            }
            pos++;
        }
        return -1;
    }
}
