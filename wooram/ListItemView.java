package com.example.caucse.nonono;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.jar.Attributes;

public class ListItemView extends LinearLayout {
    TextView vname;
    TextView vday;
    TextView vfre;

    public ListItemView(Context context) {
        super(context);
        init(context);
    }
    public ListItemView(Context context,AttributeSet attrs) {
        super(context, attrs);
    }
    public void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.list, this, true);

        vname = (TextView) findViewById(R.id.nameview);
        vday = (TextView) findViewById(R.id.dayview);
        vfre = (TextView) findViewById(R.id.freview);
    }
    public void setName(String name) {
        vname.setText(name);
    }
    public void setDay(String day) {
        vday.setText(day);
    }
    public void setFre(String fre) {
        vfre.setText(fre);
    }
}
