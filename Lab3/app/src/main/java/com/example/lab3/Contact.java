package com.example.lab3;

public class Contact {
    private String name;
    private String badgeNumber;
    private String email;

    public Contact(String name, String badgeNumber, String email) {
        this.name = name;
        this.badgeNumber = badgeNumber;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getBadgeNumber() {
        return badgeNumber;
    }

    public String getEmail() {
        return email;
    }
}

