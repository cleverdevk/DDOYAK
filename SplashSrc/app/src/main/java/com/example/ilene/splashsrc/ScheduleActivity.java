package com.example.ilene.splashsrc;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TimePicker;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
    String start, end;


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("OUTING");

    //출력 형식 설정
    public SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd.E");
    public SimpleDateFormat timeFormat = new SimpleDateFormat("aa hh:mm");
    //전달 형식 설정
    public SimpleDateFormat dateDatabaseFormat = new SimpleDateFormat("yyyy#MM#dd");
    public SimpleDateFormat timeDatabaseFormat = new SimpleDateFormat("#HH#mm");

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

                        changeDataFormat();
                        myRef.child(scheduleName).child("0").setValue("s#"+start);
                        myRef.child(scheduleName).child("1").setValue("e#"+end);

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

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d("TAG", "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
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

    private void changeDataFormat(){
        try{
            Date date = dateFormat.parse(dateTemp);
            String newdate = dateDatabaseFormat.format(date);
            date = timeFormat.parse(starttimeTemp);
            String newtime = timeDatabaseFormat.format(date);
            start = newdate+newtime;
            date = timeFormat.parse(endtimeTemp);
            newtime = timeDatabaseFormat.format(date);
            end = newdate+newtime;
        }catch(ParseException e){
            e.printStackTrace();
        }
    }

}
