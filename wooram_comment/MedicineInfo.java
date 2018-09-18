package com.example.caucse.ddoyak;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MedicineInfo extends AppCompatActivity {

    private TextView oneday;
    private TextView day;
    private EditText info;


    //하루에 약 복용 횟수
    private Button plusOneday;
    private Button minusOneday;

    //약 복용 기간
    private Button plusDay;
    private Button minusDay;

    private Button save;

    private int onedayNum;
    private int dayNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_info);

        //내부 DB 생성.
        final DBHelper dbHelper = new DBHelper(getApplicationContext(), "DB.db",null,1);

        oneday = (TextView) findViewById(R.id.oneday);
        day = (TextView) findViewById(R.id.day);
        info = (EditText) findViewById(R.id.info);

        onedayNum = 0;
        dayNum = 0;

        //Button을 이용해 약 정보 입력

        plusOneday = (Button) findViewById(R.id.plus);
        plusOneday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onedayNum < 6)
                    onedayNum++;
                oneday.setText(String.valueOf(onedayNum));
            }
        });

        minusOneday = (Button) findViewById(R.id.minus);
        minusOneday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onedayNum > 0)
                    onedayNum--;
                oneday.setText(String.valueOf(onedayNum));
            }
        });

        plusDay = (Button) findViewById(R.id.plus2);
        plusDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dayNum < 9)
                    dayNum++;
                day.setText(String.valueOf(dayNum));
            }
        });

        minusDay = (Button) findViewById(R.id.minus2);
        minusDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dayNum > 0)
                    dayNum--;
                day.setText(String.valueOf(dayNum));
            }
        });


        //약 날짜와 이름을 DB에 저장
        save = (Button) findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MedicineInfo.this, AlarmSetting.class);
                intent.putExtra("info",info.getText().toString());
                intent.putExtra("oneday",String.valueOf(onedayNum));
                intent.putExtra("day",String.valueOf(dayNum));

                startActivity(intent);
                dbHelper.delete();
                //필요한 만큼의 알람설정을 위한 DB 생성
                for(int i = 0; i< onedayNum * dayNum; i++) {
                    dbHelper.insert("noname", "yyyy#MM#dd#hh#mm");
                }
                Log.d("DATA_INSERTION", "onClick: datainsertion success!");
            }
        });
    }
}
