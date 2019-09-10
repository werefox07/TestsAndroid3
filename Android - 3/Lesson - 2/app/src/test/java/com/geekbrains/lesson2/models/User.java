package com.geekbrains.lesson2.models;

import androidx.annotation.NonNull;

public class User {
    private String name;
    private String gender;
    private String email;
    private Address address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @NonNull
    @Override
    public String toString() {
        return "Name: " + getName() + "; Gender: " + getGender() + "; Email: " + getEmail() + "; Address" + getAddress().getAddress();
    }
}
