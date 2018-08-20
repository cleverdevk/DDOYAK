package com.example.caucse.asd;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class User {
    public String frequency;
    public String name;


    public User() {

    }
    public User(String name, String frequency) {
        this.frequency=frequency;
        this.name = name;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name",name);
        result.put("frequency",frequency);
        return result;
    }
}
