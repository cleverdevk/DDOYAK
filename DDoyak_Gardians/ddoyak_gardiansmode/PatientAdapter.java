package com.example.ilene.ddoyak_gardiansmode;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class PatientAdapter extends ArrayAdapter<String> {

    PatientAdapter(Context context, int resource, ArrayList items) {
        super(context, R.layout.patientitem_layout, items);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater imageInflater = LayoutInflater.from(getContext());
        View view = imageInflater.inflate(R.layout.patientitem_layout,parent,false);
        String item = getItem(position);

        TextView name = (TextView)view.findViewById(R.id.name);
        ImageView imageView = (ImageView)view.findViewById(R.id.imageView);

        name.setText(item);
        imageView.setImageResource(R.mipmap.person);

        return view;
    }
}