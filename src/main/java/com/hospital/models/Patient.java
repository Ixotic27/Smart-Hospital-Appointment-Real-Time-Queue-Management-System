package com.hospital.models;

public class Patient extends User {
    private String bloodGroup;
    private String contactNumber;
    private int age;

    public Patient(int id, String name, String username, String password, String bloodGroup, String contactNumber,
            int age) {
        super(id, name, username, password, "Patient");
        this.bloodGroup = bloodGroup;
        this.contactNumber = contactNumber;
        this.age = age;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}
