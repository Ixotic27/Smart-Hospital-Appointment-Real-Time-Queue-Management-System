package com.hospital.services;
import java.util.*;
import com.hospital.models.Appointment;
import com.hospital.utils.SlotConflictException;

public class QueueService {
    Queue<Appointment> daily = new ArrayDeque<>();

  public void addAppointment(Appointment app) throws SlotConflictException {

    for (Appointment a : daily) {
        if (a.equals(app)) {
            throw new SlotConflictException("Appointment already exists");
        }
    }

    daily.add(app);
} 
  

    public Appointment getNextPatient() {

        if(daily.isEmpty()) {
        System.out.println("No patients in queue");
        return null;
    }


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

    public void displayQueue() {
    if(daily.isEmpty()) {
        System.out.println("Queue is empty");
        return;
    }

    int pos = 1;
    for (Appointment a : daily) {
        System.out.println("Position " + pos + ": " + a);
        pos++;
    }
}

}
