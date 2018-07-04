package org.androidtown.myapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    Button btn;
    Button btn2;
    Button btn3;
    Button btn4;
    Button btn5;
    TextView tv;
    TextView tv2;
    TextView tv3;
    Context context;        //이게 왜필요하지????
    TimePicker mTimePicker;
    AlarmManager alarm_manager;
    PendingIntent pending_intent;

    //  TCP연결 관련
    private Socket clientSocket;
    private BufferedReader socketIn;
    private PrintWriter socketOut;
    private int port = 35357;
    private final String ip = "192.168.1.2";
    private MyHandler myHandler;
    private MyThread myThread;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.context = this;
        // StrictMode는 개발자가 실수하는 것을 감지하고 해결할 수 있도록 돕는 일종의 개발 툴
        // - 메인 스레드에서 디스크 접근, 네트워크 접근 등 비효율적 작업을 하려는 것을 감지하여
        //   프로그램이 부드럽게 작동하도록 돕고 빠른 응답을 갖도록 함, 즉  Android Not Responding 방지에 도움
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            clientSocket = new Socket(ip, port);
            socketIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            socketOut = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        myHandler = new MyHandler();
        myThread = new MyThread();
        myThread.start();

        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, mTimePicker.getCurrentHour());
        c.set(Calendar.MINUTE,mTimePicker.getCurrentMinute());
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        btn = (Button) findViewById(R.id.btn);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btn5 = (Button) findViewById(R.id.btn5);
        tv = (TextView) findViewById(R.id.tv);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);

        //mTimePicker 초기화
        mTimePicker = (TimePicker) findViewById(R.id.timePicker);

        //alarm_manager 초기화
        alarm_manager = (AlarmManager)getSystemService(ALARM_SERVICE);

        //create an intent to the Alarm Receiver class
        Intent receiver_intent = new Intent(this.context, Alarm_Receiver.class);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                socketOut.println(1);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                socketOut.println(2);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                socketOut.println(3);
            }
        });

        //ALARM_SET 버튼 누르면 해당 시간 라파이로 전송 후 Alarm_Receiver로
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                socketOut.println(mTimePicker.getCurrentHour() + ":" + mTimePicker.getCurrentMinute());
            }
        });

        //ALARM_STOP 버튼 누르면 alarm stop
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarm_manager.cancel(pending_intent);
            }
        });

        //기다렸다가 alarm manager에 해당 시간에 작동하게 하는 pendingintent
        pending_intent = PendingIntent.getBroadcast(MainActivity.this,0,receiver_intent,PendingIntent.FLAG_UPDATE_CURRENT);

        //set the alarm manager
        alarm_manager.set(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),pending_intent);
    }

    class MyThread extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    // InputStream의 값을 읽어와서 data에 저장
                    String data = socketIn.readLine();
                    // Message 객체를 생성, 핸들러에 정보를 보낼 땐 이 메세지 객체를 이용
                    Message msg = myHandler.obtainMessage();
                    msg.obj = data;
                    myHandler.sendMessage(msg);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg)
        {
            if(msg.obj.toString().equals("1"))
                tv.setText(msg.obj.toString());
            else if(msg.obj.toString().equals("2"))
                tv2.setText(msg.obj.toString());
            else
                tv3.setText(msg.obj.toString());
        }
    }
}