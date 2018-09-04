package com.example.caucse.calendarcheck;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity{
    TextView month_tv;

    Button prev_btn;
    Button next_btn;

    GridView gridView;
    GridAdapter gridAdapter;

    RecyclerView checkingView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<History> data = new ArrayList<>();
    ArrayList<History> checkingData = new ArrayList<>();
    CheckingAdapter adapter;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("HISTORY");

    int curYear;
    int curMonth;
    int curDay;
    int count = 0;

    private void setMonthText(){
        curYear = gridAdapter.getCurYear();
        curMonth = gridAdapter.getCurMonth();

        month_tv.setText(curYear + "년 " + (curMonth+1) + "월");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = (GridView)findViewById(R.id.gridView);
        gridAdapter = new GridAdapter(getApplicationContext(), R.layout.month_item, "OOX");
        gridView.setAdapter(gridAdapter);

        checkingView = (RecyclerView) findViewById(R.id.checking_view);
        checkingView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        checkingView.setLayoutManager(layoutManager);

        adapter = new CheckingAdapter(getApplicationContext(), checkingData);

        data.clear();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                count=0;
                for(DataSnapshot historyData : dataSnapshot.getChildren()){
                    String history = historyData.getValue().toString();
                    StringTokenizer st = new StringTokenizer(history,"#");
                    String year, month, day, hour, min, check, name;
                    year = st.nextToken();
                    month=st.nextToken();
                    day = st.nextToken();
                    hour = st.nextToken();
                    min = st.nextToken();
                    check = st.nextToken();
                    name = st.nextToken();
                    data.add(new History(year, month, day, hour, min, check, name));
                    count++;
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        gridView.setOnDataSelectionListener(new OnDataSelectionListener() {
            @Override
            public void onDataSelected(AdapterView parent, View v, int position, long id) {
                UpdateAdapter(position);
            }
        });

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

    public void UpdateAdapter(int position){
        MonthItem item = (MonthItem) gridAdapter.getItem(position);
        curDay = item.getDay();
        checkingData.clear();
        for(int i=0;i<count;i++) {
            if (Integer.parseInt(data.get(i).getYear()) == curYear)
                if (Integer.parseInt(data.get(i).getMonth()) == (curMonth) + 1)
                    if (Integer.parseInt(data.get(i).getDay()) == curDay)
                        checkingData.add(data.get(i));
        }
        checkingView.setAdapter(adapter);
    }
}
