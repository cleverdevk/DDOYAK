package com.example.caucse.ddoyak;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ImageButton takeButton = (ImageButton)findViewById(R.id.take);
        takeButton.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(HomeActivity.this, "복용 현황",Toast.LENGTH_SHORT).show();
                //Intent intent = new Intent(getApplicationContext(),nextActivity.class);
                //startActivity(intent);
            }
        });

        ImageButton alarmButton = (ImageButton)findViewById(R.id.alarm);
        alarmButton.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(HomeActivity.this, "알람 설정",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),MedicineInfo.class);
                startActivity(intent);
            }
        });

        ImageButton scheduleButton = (ImageButton)findViewById(R.id.schedule);
        scheduleButton.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(HomeActivity.this, "스케줄 관리",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),ScheduleActivity.class);
                startActivity(intent);
            }
        });

        ImageButton chickenButton = (ImageButton)findViewById(R.id.chicken);
        chickenButton.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(HomeActivity.this,"또약 또약",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),DDoyakActivity.class);
                startActivity(intent);
            }
        });
    }


}
