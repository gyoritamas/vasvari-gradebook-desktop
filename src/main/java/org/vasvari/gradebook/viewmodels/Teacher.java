package org.vasvari.gradebook.viewmodels;

import javafx.beans.property.SimpleStringProperty;

public class Teacher {
    private final SimpleStringProperty name;
    private final SimpleStringProperty dateOfBirth;
    private final SimpleStringProperty email;
    private final SimpleStringProperty phone;

    public Teacher(
            String name,
            String dateOfBirth,
            String email,
            String phone
    ) {
        this.name = new SimpleStringProperty(name);
        this.dateOfBirth = new SimpleStringProperty(dateOfBirth);
        this.email = new SimpleStringProperty(email);
        this.phone = new SimpleStringProperty(phone);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getDateOfBirth() {
        return dateOfBirth.get();
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth.set(dateOfBirth);
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getPhone() {
        return phone.get();
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }
}
