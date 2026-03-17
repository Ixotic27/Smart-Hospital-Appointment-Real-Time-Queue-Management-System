package com.hospital.models;

public class Doctor extends User {
    private String specialization;
    private double consultationFee;

    public Doctor(int id, String name, String username, String password, String specialization,
            double consultationFee) {
        super(id, name, username, password, "Doctor");
        this.specialization = specialization;
        this.consultationFee = consultationFee;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public double getConsultationFee() {
        return consultationFee;
    }

    public void setConsultationFee(double consultationFee) {
        this.consultationFee = consultationFee;
    }
}
