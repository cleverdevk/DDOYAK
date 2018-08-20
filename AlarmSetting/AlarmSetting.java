package com.example.caucse.ddoyak;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import java.util.ArrayList;

public class AlarmSetting extends AppCompatActivity{

    private int hour, min;
    private DatabaseReference timeRef;
    private int num;

    private String info;
    private String dayNum;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_layout);

        Intent intent =getIntent();
        info = intent.getStringExtra("info");
        dayNum = intent.getStringExtra("day");

        timeRef = MedicineInfo.database.getReference("timeInfo");
        num = 0;

        Button switchButton = (Button)findViewById(R.id.switchButton);
        switchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                ArrayList<Time> data = new ArrayList<>();
                ListView listView = (ListView) findViewById(R.id.alarmList);
                TimePicker timePicker = (TimePicker) findViewById(R.id.timePicker);

                hour= timePicker.getHour();
                min = timePicker.getMinute();
                Time time = new Time(hour, min);
                data.add(time);

                ListviewAdapter adapter = new ListviewAdapter(AlarmSetting.this, R.layout.item,data);
                listView.setAdapter(adapter);
                num++;
                writeNewTime(hour,min);
                ToastMessage();
            }
        });
    }

    public void ToastMessage() {
        if (num < Integer.valueOf(dayNum)) {
            Toast toast = Toast.makeText(getApplicationContext(), num+"회 알람 설정", Toast.LENGTH_SHORT);
            toast.show();
        }
        else {
            Toast toast = Toast.makeText(getApplicationContext(), "알람 설정이 완료되었습니다.", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    private void writeNewTime(int hour, int min){
        Time time2 = new Time(hour,min);
        timeRef.child(info).child("time "+num).setValue(time2);
    }
}
