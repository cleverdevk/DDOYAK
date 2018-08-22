package com.example.caucse.asd;

public class list_item {
    private String name;
    private String frequency;

    public list_item(String name, String frequency) {
        this.name = name;
        this.frequency = frequency;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getName() {
        return name;
    }

    public String getFrequency() {
        return frequency;
    }
}

