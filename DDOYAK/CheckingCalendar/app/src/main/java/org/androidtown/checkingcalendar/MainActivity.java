package org.androidtown.checkingcalendar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{

    TextView month_tv;
    Button prev_btn;
    Button next_btn;
    GridView gridView;
    GridAdapter gridAdapter;


    int curYear;
    int curMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = (GridView)findViewById(R.id.gridView);
        gridAdapter = new GridAdapter(getApplicationContext(),R.layout.month_item, "OOX");
        gridView.setAdapter(gridAdapter);

        month_tv = findViewById(R.id.month_tv);
        setMonthText();

        prev_btn = findViewById(R.id.prev_btn);
        prev_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gridAdapter.setPreviousMonth();
                gridAdapter.notifyDataSetChanged();
                setMonthText();
            }
        });


        next_btn = findViewById(R.id.next_btn);
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gridAdapter.setNextMonth();
                gridAdapter.notifyDataSetChanged();
                setMonthText();
            }
        });
    }


    private void setMonthText(){
        curYear = gridAdapter.getCurYear();
        curMonth = gridAdapter.getCurMonth();

        month_tv.setText(curYear + "년 " + (curMonth+1) + "월");
    }
}
