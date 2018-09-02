package com.example.asdf;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> { //이 부분이 문제입니다.
    Context context;
    List<User> users;

    public Adapter(Context context, List<User> users) {
        this.context = context;
        this.users = users;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder){
            User user = users.get(position);
        ((ItemViewHolder) holder).name.setText(user.getName());
        ((ItemViewHolder) holder).fre.setText(user.getFrequency());
        ((ItemViewHolder) holder).day.setText(user.getDay());

    }
}

    @Override
    public int getItemCount() {
        return users.size();
    }

public class ItemViewHolder extends RecyclerView.ViewHolder {
    TextView name, fre, day;

    public ItemViewHolder(View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.name);
        fre = itemView.findViewById(R.id.fre);
        day = itemView.findViewById(R.id.day);
    }
}

}


