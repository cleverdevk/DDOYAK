package com.example.caucse.ddoyak;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.StringTokenizer;


//복용한 약들을 RecyclerView로 구현
public class MedicineList extends AppCompatActivity {

    ImageButton next;
    int count;

    RecyclerView medicineView;
    RecyclerView.LayoutManager layoutManager;

    ArrayList<History> data = new ArrayList<>();
    ArrayList<MedicineData> medidata = new ArrayList<>();
    ArrayList<String> mediName = new ArrayList<>();
    ArrayList<String> temp = new ArrayList<>();


    //Realtime Database에 'HISTORY' Reference를 만들어 줌
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("HISTORY");

    MedicineAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_list);

        medicineView=(RecyclerView)findViewById(R.id.medicineList_view);
        medicineView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        medicineView.setLayoutManager(layoutManager);

        adapter = new MedicineAdapter(getApplicationContext(), medidata);

        data.clear();
        mediName.clear();
        medidata.clear();


        //HISTORY에 있는 데이터를 전부 받아 year, month,day, hour, min ,check, name 으로 나누어서 저장
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                count = 0;
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String history = ds.getValue().toString();
                    StringTokenizer st = new StringTokenizer(history, "#");
                    String year, month, day, hour, min, check, name;
                    year = st.nextToken();
                    month = st.nextToken();
                    day = st.nextToken();
                    hour = st.nextToken();
                    min = st.nextToken();
                    check = st.nextToken();
                    name = st.nextToken();
                    data.add(new History(year, month, day, hour, min, check, name));
                    count++;
                }
                updateAdapter();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        //RecyclerView 구현된 약 복용 목록에서 새로운 약 추가 기능
        next = (ImageButton) findViewById(R.id.medicine_plus);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MedicineInfo.class);
                startActivity(intent);
            }
        });
    }

    //addValueEventListener로 받은 모든 데이터들을 가공
    public void updateAdapter(){
        mediName.clear();
        mediName.add(data.get(0).getName());

        int num=0;

        //이름이 같은것을 지움.
        for(int i=0;i<count;i++){
            if(!data.get(i).getName().equals(mediName.get(num))){
                num++;
                mediName.add(data.get(i).getName());
            }
        }
        //임시 저장 ArrayList
        ArrayList<String> medi = new ArrayList<>();


        for(int i=0;i<mediName.size();i++) {
            medi.clear();
            for (int j = 0; j < count; j++) {
                //이름이 같은 것의 날짜 저장.
                if(data.get(j).getName().equals(mediName.get(i)))
                    medi.add(data.get(j).getDays());
            }
            //저장된 데이터를 처음날짜와 마지막 날짜만 저장.
            if(medi.size()!=0)
                medidata.add(new MedicineData(mediName.get(i),medi.get(0),medi.get(medi.size()-1)));
        }
        medicineView.setAdapter(adapter);
    }
}
