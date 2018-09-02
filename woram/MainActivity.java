package com.example.asdf;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Button move_btn;
    private Button add_btn;
    private Button ad;
    private EditText name;
    private EditText fre;
    private EditText day;

    // Write a message to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Schedule");

    int cnt; // TEST 용


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = (EditText) findViewById(R.id.name);
        fre = (EditText) findViewById(R.id.fre);
        day = (EditText) findViewById(R.id.day);
        final Intent intent = new Intent(this, ShowRecycler.class);

        ad = (Button) findViewById(R.id.re);
        ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User(String.valueOf(name.getText()),String.valueOf(fre.getText()),String.valueOf(day.getText()));
            }
        });

        move_btn = (Button) findViewById(R.id.move);
        move_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });

        add_btn = (Button) findViewById(R.id.add);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writeNewUser(String.valueOf(name.getText()),String.valueOf(fre.getText()),String.valueOf(day.getText()));
                cnt++;
            }
        });

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
             //writeNewUser method 를 쓰기 위해 수정 함
                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                Log.d("TAG", "Value is: " + map);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
    }
    private void writeNewUser (String name, String day, String frequency){
        User user = new User(name,day, frequency);
        myRef.child("cold").setValue(user);
    }

}
