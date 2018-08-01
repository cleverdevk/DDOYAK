package com.example.ilene.ddoyak_gardiansmode;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] items = {"전희수", "박진선","강인배"};
        final String[] numbers = {"tel:010-%%%%-%%%%","tel:010-&&&&-&&&&","tel:010-****-****"};

        TextView user = (TextView)findViewById(R.id.user);
        TextView textView = (TextView)findViewById(R.id.textView);

        ListAdapter adapter = new ImageAdapter(this, items);
        ListView listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        String item = String.valueOf(parent.getItemAtPosition(position)).toString();
                        String number = numbers[position];
                        Toast.makeText(MainActivity.this, item, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),InfoActivity.class);
                        intent.putExtra("name",item);
                        intent.putExtra("callButton",number);
                        startActivity(intent);

                    }
                }
        );

    }

}