package com.hospital.services;

import java.util.*;
import com.hospital.models.Doctor;
import com.hospital.models.Patient;
import com.hospital.models.Appointment;

public class DataStorageService {
    private ArrayList<Doctor> doctors = new ArrayList<>();
    private ArrayList<Patient> patients = new ArrayList<>();
    private ArrayList<Appointment> appointments = new ArrayList<>();
}

    public void loadDoctors(ArrayList<Doctor> doctors) {
        this.doctors = doctors;
    }

    public void loadPatients(ArrayList<Patient> patients) {
        this.patients = patients;
    }

    public void loadAppointments(ArrayList<Appointment> appointments) {
        this.appointments = appointments;
    }

    public ArrayList<Appointment> getAppointmentsforDoctor(int doctorId) {
        ArrayList<Appointment> doctorAppointments = new ArrayList<>();
        for (Appointment app : appointments) {
            if (app.getDoctor().getId() == doctorId) {
                doctorAppointments.add(app);
            }
        }
        return doctorAppointments;
    }

    public void sortAppointmentBytime(ArrayList<Appointment> appointments){
        Collections.sort(appointments, Comparator.comparing(Appointment::getTime));
    }