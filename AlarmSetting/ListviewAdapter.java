package com.example.caucse.ddoyak;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListviewAdapter extends BaseAdapter{

    private LayoutInflater inflater;
    private ArrayList<Time> data;
    private int layout;
    private int num;

    public ListviewAdapter(Context context, int layout, ArrayList<Time> data){
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data = data;
        this.layout = layout;
        num=1;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i).getHour();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView=inflater.inflate(layout,parent,false);
        }
        Time time = data.get(position);
        TextView numberText = (TextView)convertView.findViewById(R.id.number);
        TextView timeText=(TextView)convertView.findViewById(R.id.listText);
        numberText.setText(num+"번  ");
        num++;
        timeText.setText(time.getHour()+"시 "+time.getMin()+"분");
        return convertView;
    }
}
