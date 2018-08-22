package com.example.caucse.asd;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DataList extends AppCompatActivity {
    private Button btn;
    private ListView dataListView;
    private MyListAdapter myListAdapter;
    private ArrayList<list_item> list_itemArrayList;


    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("Medicine List");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sss);
        myRef.setValue("cc");

    btn = (Button) findViewById(R.id.button2);
    btn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    });
    }

}