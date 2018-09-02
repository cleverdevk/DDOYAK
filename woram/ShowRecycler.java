package com.example.asdf;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;


public class ShowRecycler extends AppCompatActivity {
    RecyclerView recyclerView;
    Adapter adapter;
    ArrayList<User> users = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_list);
        recyclerView = findViewById(R.id.recyclerview);
        setData();
        setRecyclerView();
    }
    void setRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new Adapter(getApplicationContext(), users);
        recyclerView.setAdapter(adapter);
    }
    void setData() {
        users= new ArrayList<>();
        users.add(new User("plz","help","me"));
        users.add(new User("plz","help","me"));
        users.add(new User("plz","help","me"));
        users.add(new User("plz","help","me"));
    }

}
