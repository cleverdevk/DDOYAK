package com.example.caucse.nonono;

public class User {
    public String name;
    public String day;
    public String fre;

    public User() {

    }

    public User(String name,String day, String frequency) {
        this.fre = fre;
        this.day = day;
        this.name=name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {

        return name;
    }

    public String getDay() {

        return day;
    }

    public String getFre() {
        return fre;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setFre(String fre) {
        this.fre = fre;
    }
}