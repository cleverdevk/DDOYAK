package com.example.caucse.calendarcheck;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class CheckingAdapter extends RecyclerView.Adapter<CheckingAdapter.CheckingHolder>{

    ArrayList<History> data;
    private Context context;

    public CheckingAdapter(Context context, ArrayList<History> data){
        this.data = data;
        this.context = context;
    }

    public static class CheckingHolder extends RecyclerView.ViewHolder {
        TextView checkTime;
        TextView checkText;
        TextView checkName;

        public CheckingHolder(View v) {
            super(v);

            checkName =(TextView) v.findViewById(R.id.checking_name);
            checkTime= (TextView) v.findViewById(R.id.checking_time);
            checkText = (TextView) v.findViewById(R.id.checking_text);
        }
    }

    public CheckingAdapter(ArrayList data) {
        this.data = data;
    }

    //뷰홀더
    @Override
    public CheckingAdapter.CheckingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.checking_item, parent, false);
        return new CheckingHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(CheckingHolder holder, int position) {
        CheckingHolder myHolder = (CheckingHolder) holder;

        myHolder.checkName.setText(data.get(position).getName());
        myHolder.checkTime.setText(data.get(position).getTime());
        if(Integer.parseInt(data.get(position).getCheck())==0)
            myHolder.checkText.setText("미복용");
        else
            myHolder.checkText.setText("복용 완료");
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}