package com.hospital.models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Appointment {
    private SimpleIntegerProperty appointmentId;
    private Patient patient;
    private Doctor doctor;
    private SimpleStringProperty date;
    private SimpleStringProperty time;
    private SimpleStringProperty status;

    public Appointment(int appointmentId, Patient patient, Doctor doctor, String date, String time, String status) {
        this.appointmentId = new SimpleIntegerProperty(appointmentId);
        this.patient = patient;
        this.doctor = doctor;
        this.date = new SimpleStringProperty(date);
        this.time = new SimpleStringProperty(time);
        this.status = new SimpleStringProperty(status);
    }

    public int getAppointmentId() { return appointmentId.get(); }
    public void setAppointmentId(int id) { this.appointmentId.set(id); }
    public SimpleIntegerProperty appointmentIdProperty() { return appointmentId; }

    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }

    public Doctor getDoctor() { return doctor; }
    public void setDoctor(Doctor doctor) { this.doctor = doctor; }

    public String getDate() { return date.get(); }
    public void setDate(String date) { this.date.set(date); }
    public SimpleStringProperty dateProperty() { return date; }

    public String getTime() { return time.get(); }
    public void setTime(String time) { this.time.set(time); }
    public SimpleStringProperty timeProperty() { return time; }

    public String getStatus() { return status.get(); }
    public void setStatus(String status) { this.status.set(status); }
    public SimpleStringProperty statusProperty() { return status; }
}
