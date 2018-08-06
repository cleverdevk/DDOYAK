package com.example.caucse.calendarguard;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    CalendarView monthView;
    CalendarAdapter monthViewAdapter;
    TextView monthText;

    int curYear;
    int curMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        monthView = (CalendarView) findViewById(R.id.monthView);
        monthViewAdapter = new CalendarAdapter(this);
        monthView.setAdapter(monthViewAdapter);

        monthText = (TextView)findViewById(R.id.monthText);
        setMonthText();

        //previous month button setting
        Button monthPrevious = (Button)findViewById(R.id.monthPrevious);
        monthPrevious.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                monthViewAdapter.setPreviousMonth();
                monthViewAdapter.notifyDataSetChanged();
                setMonthText();
            }
        });

        //next month button setting
        Button monthNext = (Button)findViewById(R.id.monthNext);
        monthNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                monthViewAdapter.setNextMonth();
                monthViewAdapter.notifyDataSetChanged();
                setMonthText();
            }
        });
    }

    //month text setting
    private void setMonthText(){
        curYear = monthViewAdapter.getCurYear();
        curMonth = monthViewAdapter.getCurMonth();

        monthText.setText(curYear + "년 " + (curMonth+1) + "월");
    }
}