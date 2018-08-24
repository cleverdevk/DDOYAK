package com.example.caucse.ddoyak;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.text.SimpleDateFormat;

public class AlarmSetting extends AppCompatActivity{

    TimePicker timePicker;

    Button remove_data_btn;
    Button save_data_btn;
    Button finish_setting_btn;
    TextView result;
    TextView tv1;

    AlarmManager alarmManager;
    Calendar calendar_time;

    private int hour, min;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    static DatabaseReference MediRef;
    private int num;
    public int count=0;
    public int sqlite_id=1;


    private String info;
    private String onedayNum;
    private String dayNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_layout);

        final DBHelper dbHelper = new DBHelper(getApplicationContext(),"DB.db",null,1);

        //alarmManager, calendar_time initialize
        alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        calendar_time = Calendar.getInstance();

        new DatePickerDialog(AlarmSetting.this, dateSetListener, calendar_time.get(Calendar.YEAR), calendar_time.get(Calendar.MONTH),calendar_time.get(Calendar.DAY_OF_MONTH)).show();

        Intent intent =getIntent();
        info = intent.getStringExtra("info");
        onedayNum = intent.getStringExtra("oneday");
        dayNum = intent.getStringExtra("day");

        MediRef = database.getReference("DOSE");
        num = 0;

        //등록한 시간 삭제하는 기능 구현
        remove_data_btn = (Button) findViewById(R.id.remove_data);
        remove_data_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper dbHelper = new DBHelper(getApplicationContext(), "DB.db",null,1);
                dbHelper.delete();
            }
        });

        result = (TextView) findViewById(R.id.get_all_data);
        tv1 = (TextView) findViewById(R.id.textView);
        updateTV();
        //데이터 저장버튼
        save_data_btn = (Button)findViewById(R.id.save_select_data);
        save_data_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                calendar_time = Calendar.getInstance();
                timePicker = (TimePicker)findViewById(R.id.timePicker);

                calendar_time.set(Calendar.YEAR,calendar_time.get(Calendar.YEAR));
                calendar_time.set(Calendar.YEAR,calendar_time.get(Calendar.MONTH)+1);
                calendar_time.set(Calendar.YEAR,calendar_time.get(Calendar.DAY_OF_MONTH));

                calendar_time.set(Calendar.HOUR_OF_DAY,timePicker.getHour());
                calendar_time.set(Calendar.MINUTE, timePicker.getMinute());
                calendar_time.set(Calendar.SECOND,0);

                setAlarm();
                updateTV();

                result.setText(dbHelper.getAllData());
                Log.d("TAG", "onClick: get all data successed");
            }
        });

        //finish setting 클릭리스너 구현 ((미완))
        finish_setting_btn = (Button) findViewById(R.id.finish_setting);
        finish_setting_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.writeNewData(info);
            }
        });
    }

    //출력 형식 설정
    public SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy#MM#dd#hh#mm");

    void setAlarm() {
        Intent intent = new Intent(this,AlarmReceiver.class);

        PendingIntent[] servicePendings = new PendingIntent[100];
        servicePendings[count+1]=PendingIntent.getBroadcast(AlarmSetting.this,count+1, intent, PendingIntent.FLAG_UPDATE_CURRENT );
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar_time.getTimeInMillis(), servicePendings[count+1]);

        Toast.makeText(getBaseContext(),num+"회 알람 설정: "+ calendar_time.get(Calendar.HOUR_OF_DAY) +":" + calendar_time.get(Calendar.MINUTE),Toast.LENGTH_SHORT).show();
        String time = calendar_time.get(Calendar.YEAR)+":" + (calendar_time.get(Calendar.MONTH)+1)+":"+calendar_time.get(Calendar.DAY_OF_MONTH) +":"+calendar_time.get(Calendar.HOUR_OF_DAY)+":"+calendar_time.get(Calendar.MINUTE);

        DBHelper dbHelper = new DBHelper(getApplicationContext(), "DB.db",null,1);
        dbHelper.update(info, time, sqlite_id++);
    }

    private void updateTV(){
        tv1.setText(simpleDateFormat.format(calendar_time.getTime()));
    }

    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
            calendar_time.set(Calendar.YEAR, year);
            calendar_time.set(Calendar.MONTH,month);
            calendar_time.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        }
    };
}
