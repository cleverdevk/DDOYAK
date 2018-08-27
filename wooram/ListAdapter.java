package com.example.caucse.nonono;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends BaseAdapter {
    private Context context;
    public ListAdapter(Context context) {
        this.context=context;
    }
    ArrayList<User> items = new ArrayList<User>();
    @Override
    public  int getCount() {
        return items.size();
    }
    public void addItem(ListItem item) {
        items.add(item);
    }
    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ListItemView view = new ListItemView(context.getApplicationContext());
        User user = items.get(position);

        view.setName(user.getName());
        view.setDay(user.getDay());
        view.setFre(user.getFre());


        return view;
    }
}
