package org.androidtown.alarm_real;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 진행상황: 알람 두개 띄우기는 성공 (여러개는 가능할 듯 - 방법: requestcode 바꾸고, pending intent는 receiver에서 각각 새로 생성해야 함)
 * 부족한 거 ->  임의로 정한 아 점 저의 경계 있어야 함
 *            + 해당 tv에, 아침알람 후 점심알람 설정해도 아침tv에는 아침 정보 뜨도록 하는 거 로직 수정
 *            + powermanager관련? 테스트 필요. 백그라운드 일 때, 홀드 화면일 때 등    +     내 폰에서는 어떻게 되는지 테스트 필요
 *            + firebase realtime database 연동 후 setalarm 할 때 바로 db로 연동
 *            + 빈빈이랑 합치기! (db 연동 & 합치기 한명씩 해야할둣?)
 */


public class MainActivity extends AppCompatActivity {

    Context context;
    Button set_alarm_btn;
    Button pick_time_btn;
    TextView textView;
    Button set_alarm_btn2;
    Button pick_time_btn2;
    TextView textView2;
    Calendar calendar_time;
    AlarmManager alarmManager;
    PendingIntent servicePending;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //아침복용
        set_alarm_btn = (Button) findViewById(R.id.setAlarm);
        pick_time_btn = (Button) findViewById(R.id.setTime);
        textView = (TextView) findViewById(R.id.setTimeTextView);

        //점심복용
        set_alarm_btn2 = (Button) findViewById(R.id.setAlarm2);
        pick_time_btn2 = (Button) findViewById(R.id.setTime2);
        textView2 = (TextView) findViewById(R.id.setTimeTextView2);

        alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        calendar_time = Calendar.getInstance();

        //아침복용관련 버튼
        set_alarm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAlarm1();
            }
        });
        pick_time_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(MainActivity.this, timeSetListener, calendar_time.get(Calendar.HOUR_OF_DAY),calendar_time.get(Calendar.MINUTE),true).show();
                new DatePickerDialog(MainActivity.this, dateSetListener, calendar_time.get(Calendar.YEAR), calendar_time.get(Calendar.MONTH), calendar_time.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //점심복용관련 버튼
        set_alarm_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAlarm2();
            }
        });
        pick_time_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(MainActivity.this, timeSetListener, calendar_time.get(Calendar.HOUR_OF_DAY),calendar_time.get(Calendar.MINUTE),true).show();
                new DatePickerDialog(MainActivity.this, dateSetListener, calendar_time.get(Calendar.YEAR), calendar_time.get(Calendar.MONTH), calendar_time.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        updateTextView();

    }

    public SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm");

    //복용 날짜(year, month, day)를 설정
    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            calendar_time.set(Calendar.YEAR,year);
            calendar_time.set(Calendar.MONTH,month);
            calendar_time.set(Calendar.DAY_OF_MONTH,dayOfMonth);
            //updateTextView();
            //updateTextView2();
        }
    };


    //복용 시간(hh,mm)을 설정
    TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            calendar_time.set(Calendar.HOUR_OF_DAY,hourOfDay);
            calendar_time.set(Calendar.MINUTE, minute);
            calendar_time.set(Calendar.SECOND,0);

            //임의로 아침 점심 복용의 경계를 11시로(이뮬레이터기준) 잡아놓음 일단
            if(Calendar.HOUR_OF_DAY < 11)
                updateTextView();
            else
                updateTextView2();
        }
    };

    //update textView(설정한 알람 시간 보여줌)
    private void updateTextView(){
        textView.setText(simpleDateFormat.format(calendar_time.getTime()));
    }

    private void updateTextView2(){
        textView2.setText(simpleDateFormat.format(calendar_time.getTime()));
    }

    //setAlarm 알람 설정 버튼 누르면 intent, pendingintent 만들고 해당 시간까지 대기
    void setAlarm1(){
        Intent intent = new Intent(this,AlarmReceiver.class);
        servicePending = PendingIntent.getBroadcast(MainActivity.this, 111, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar_time.getTimeInMillis(),servicePending);

        Toast.makeText(getBaseContext(),"아침 알람 설정: "+ calendar_time.get(Calendar.HOUR_OF_DAY) +":" + calendar_time.get(Calendar.MINUTE),Toast.LENGTH_SHORT).show();
    }

    void setAlarm2(){
        Intent intent = new Intent(this,AlarmReceiver.class);
        servicePending = PendingIntent.getBroadcast(MainActivity.this, 121, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar_time.getTimeInMillis(),servicePending);

        Toast.makeText(getBaseContext(),"점심 알람 설정: "+ calendar_time.get(Calendar.HOUR_OF_DAY) +":" + calendar_time.get(Calendar.MINUTE),Toast.LENGTH_SHORT).show();
    }
}
