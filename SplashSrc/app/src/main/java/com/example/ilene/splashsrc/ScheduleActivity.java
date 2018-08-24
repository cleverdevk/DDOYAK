package com.example.ilene.splashsrc;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ScheduleActivity extends AppCompatActivity{

    RecyclerView scheduleList;
    RecyclerView.LayoutManager layoutManager;
    ImageView title, yellowbar;
    ImageButton addButton;
    Calendar calendar;
    String dateTemp;
    String starttimeTemp;
    String endtimeTemp;
    String scheduleName;

    //출력 형식 설정
    public SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.mm.dd.E");
    public SimpleDateFormat timeFormat = new SimpleDateFormat("aa hh:mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        //Title 생성
        title = (ImageView)findViewById(R.id.title);
        yellowbar=(ImageView)findViewById(R.id.yellowbar);

        //calendar 초기화
        calendar = Calendar.getInstance();

        //스케줄리스트_RecyclerView
        scheduleList = (RecyclerView)findViewById(R.id.schedule_list);
        scheduleList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        scheduleList.setLayoutManager(layoutManager);

        final ArrayList<ScheduleInfo> scheduleitems = new ArrayList<>();

        final ListAdapter myAdapter = new ListAdapter(getApplicationContext(), scheduleitems);
        scheduleList.setAdapter(myAdapter);

        //일정 추가 버튼
        addButton = (ImageButton)findViewById(R.id.addButton);
        addButton.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v){
                final Dialog dialog = new Dialog(ScheduleActivity.this);
                dialog.setContentView(R.layout.dialog);
                dialog.setTitle("일정 등록");
                final EditText text = (EditText)dialog.findViewById(R.id.enterScaheduleName);
                Button OKbutton = (Button)dialog.findViewById(R.id.OKbutton);
                OKbutton.setOnClickListener(new Button.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        //string 저장
                        scheduleName = text.getText().toString();
                        text.setText("");
                        scheduleitems.add(new ScheduleInfo(dateTemp, starttimeTemp,endtimeTemp,scheduleName));
                        myAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
                dialog.show();

                new TimePickerDialog(ScheduleActivity.this, endtimeSetListener, calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),true).show();
                new TimePickerDialog(ScheduleActivity.this, starttimeSetListener, calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),true).show();
                new DatePickerDialog(ScheduleActivity.this, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });



    }


    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setDateTemp();
        }
    };

    TimePickerDialog.OnTimeSetListener starttimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
            calendar.set(Calendar.MINUTE,minute);
            calendar.set(Calendar.SECOND,0);
            setStartTimeTemp();
        }
    };

    TimePickerDialog.OnTimeSetListener endtimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
            calendar.set(Calendar.MINUTE,minute);
            calendar.set(Calendar.SECOND,0);
            setEndtimeTemp();
        }
    };

    private void setStartTimeTemp(){
        starttimeTemp = timeFormat.format(calendar.getTime());
    }
    private void setEndtimeTemp(){
        endtimeTemp = timeFormat.format(calendar.getTime());
    }
    private void setDateTemp(){
        dateTemp = dateFormat.format(calendar.getTime());
    }

}
