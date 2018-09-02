package com.example.caucse.homesweethome;

public class User {
    public String name;
    public String frequency;
    public String day;

    public User () {

    }

    public User(String name, String frequency, String day) {
        this.name = name;
        this.frequency = frequency;
        this.day = day;
    }


    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}