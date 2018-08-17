package com.example.caucse.asd;

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

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button submit_btn;
    Button add_btn;
    EditText name_text,frequency_text;
    // Write a message to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Medicine List");
    DatabaseReference secRef;
    int cnt = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name_text = (EditText) findViewById(R.id.name_edit);
        frequency_text = (EditText) findViewById(R.id.frequency_edit);


        submit_btn = (Button) findViewById(R.id.submit);
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeNewUser("1번",String.valueOf(name_text.getText()),String.valueOf(frequency_text.getText()));
                submit_btn.setClickable(false);
                add_btn.setClickable(true);
            }
        });

        add_btn = (Button) findViewById(R.id.add_btn);
        add_btn.setClickable(false);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 secRef= myRef.child(cnt+"번");
                 Map<String,Object> Updates= new HashMap<>();
                 Updates.put("name",String.valueOf(name_text.getText()));
                 Updates.put("frequency",String.valueOf(frequency_text.getText()));
                 secRef.updateChildren(Updates);
                 cnt++;
            }
        });


        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
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
    private void writeNewUser(String userId, String name, String frequency) {
        User user = new User(name,frequency);
        myRef.child(userId).setValue(user);
    }


}
