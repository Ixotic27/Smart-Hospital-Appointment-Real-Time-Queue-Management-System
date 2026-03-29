package com.hospital.models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public abstract class User {
    private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    private SimpleStringProperty username;
    private SimpleStringProperty password;
    private SimpleStringProperty role; // Admin, Doctor, Patient

    public User(int id, String name, String username, String password, String role) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.role = new SimpleStringProperty(role);
    }

    public int getId() { return id.get(); }
    public void setId(int id) { this.id.set(id); }
    public SimpleIntegerProperty idProperty() { return id; }

    public String getName() { return name.get(); }
    public void setName(String name) { this.name.set(name); }
    public SimpleStringProperty nameProperty() { return name; }

    public String getUsername() { return username.get(); }
    public void setUsername(String username) { this.username.set(username); }
    public SimpleStringProperty usernameProperty() { return username; }

    public String getPassword() { return password.get(); }
    public void setPassword(String password) { this.password.set(password); }
    public SimpleStringProperty passwordProperty() { return password; }

    public String getRole() { return role.get(); }
    public void setRole(String role) { this.role.set(role); }
    public SimpleStringProperty roleProperty() { return role; }
}
