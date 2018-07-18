package com.example.ilene.ddoyak_gardians;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;




public class ImageAdapter extends ArrayAdapter<String> {

    ImageAdapter(Context context, String[] items) {
        super(context, R.layout.image_layout, items);
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater imageInflater = LayoutInflater.from(getContext());
        View view = imageInflater.inflate(R.layout.image_layout,parent,false);
        String item = getItem(position);

        TextView name = (TextView)view.findViewById(R.id.name);
        ImageView imageView = (ImageView)view.findViewById(R.id.imageView);

        name.setText(item);
        imageView.setImageResource(R.mipmap.person);

        return view;
    }
}