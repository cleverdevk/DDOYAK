package com.example.caucse.ddoyak;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewAdapter extends RecyclerView.Adapter<ViewAdapter.ViewHolder> {

    private ArrayList<Time> data;
    private Context context;

    public ViewAdapter(Context context, ArrayList<Time> data){
        this.data = data;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView number;
        TextView time;

        public ViewHolder(View v) {
            super(v);

            number = (TextView) v.findViewById(R.id.number);
            time = (TextView) v.findViewById(R.id.listText);
        }
    }

    public ViewAdapter(ArrayList<Time> myDataset) {
        data = myDataset;
    }

    //뷰홀더
    @Override
    public ViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ViewHolder myHolder = (ViewHolder) holder;

        myHolder.number.setText((position+1)+"번째 알람  ");
        myHolder.time.setText(data.get(position).getHour()+"시 "+data.get(position).getMin()+"분");
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}