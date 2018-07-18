
package com.example.ilene.ddoyak_gardiansmode;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;


public class InfoActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.patient_info);

        TextView name = (TextView)findViewById(R.id.name);

        Intent intent = getIntent();
        name.setText(intent.getStringExtra("name"));
        final String thisNumber = intent.getStringExtra("callButton");

        TextView information = (TextView)findViewById(R.id.information);

        ImageButton callButton = (ImageButton)findViewById(R.id.callButton);
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(thisNumber));
                startActivity(callIntent);

            }
        });


    }




}
