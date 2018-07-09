package com.example.caucse.calendar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView monthText;
    GridView monthView;
    MonthAdapter adapter;
    int curYear;
    int curMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        monthText = (TextView) findViewById(R.id.monthText);
        monthView = (GridView) findViewById(R.id.monthView);

        adapter = new MonthAdapter(this);
        monthView.setAdapter(adapter);
        setMonthText();

        Button monthPrevious = (Button) findViewById(R.id.monthPrevious);
        monthPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.setPreviousMonth();
                adapter.notifyDataSetChanged();

                setMonthText();
            }
        });

        Button monthNext = (Button) findViewById(R.id.monthNext);
        monthNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.setNextMonth();
                adapter.notifyDataSetChanged();

                setMonthText();
            }
        });
    }

    private void setMonthText(){
        curYear = adapter.getCurYear();
        curMonth = adapter.getCurMonth();

        monthText.setText(curYear+"년 "+(curMonth+1)+"월");
    }
}
