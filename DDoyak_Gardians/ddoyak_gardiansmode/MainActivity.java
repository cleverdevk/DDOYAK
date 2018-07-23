package com.example.ilene.ddoyak_gardiansmode;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final ArrayList<String> items = new ArrayList<String>();
        final ArrayList<String> numbers = new ArrayList<String>();
        items.add("jinjin");
        items.add("bimbin");


        final ArrayAdapter adapter = new PatientAdapter(this, android.R.layout.simple_list_item_single_choice,items);
        final ListView listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);


        TextView textView = (TextView)findViewById(R.id.textView);
        final EditText newName = (EditText)findViewById(R.id.newName);
        final EditText newNumber = (EditText)findViewById(R.id.newNumber);

        Button addButton = (Button)findViewById(R.id.addButton);
        addButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item = newName.getText().toString();
                String number = newNumber.getText().toString();
                if(item != null || item.trim().length()>0) {
                    items.add(item);
                    numbers.add(number);

                    adapter.notifyDataSetChanged();
                    newName.setText("");
                    newNumber.setText("");
               }
            }
        });

        Button deleteButton = (Button)findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position;
                position = listView.getCheckedItemPosition();
                if(position != ListView.INVALID_POSITION) {
                    items.remove(position);
                    numbers.remove(position);
                    listView.clearChoices();
                    adapter.notifyDataSetChanged();
                }
            }
        });

        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        String item = String.valueOf(parent.getItemAtPosition(position)).toString();
                        String number = String.valueOf(numbers.get(position).toString());

                        Toast.makeText(MainActivity.this, item, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),InfoActivity.class);
                        intent.putExtra("name",item);
                        intent.putExtra("callButton",number);
                        startActivity(intent);

                    }
                }
        );

    }





}