package com.example.caucse.calendarcheck;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

public class GridView extends android.widget.GridView {
    private OnDataSelectionListener selectionListener;
    GridAdapter adapter;

    public GridView(Context context) {
        super(context);
        init();
    }

    public GridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        setBackgroundColor(Color.DKGRAY);
        setVerticalSpacing(1);
        setHorizontalSpacing(1);
        setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        setNumColumns(7);

        setOnItemClickListener(new OnItemClickAdapter());
    }

    class OnItemClickAdapter implements OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            if(adapter != null){
                adapter.setSelectedPosition(position);
                adapter.notifyDataSetInvalidated();
            }

            if(selectionListener != null){
                selectionListener.onDataSelected(parent,view,position,id);
            }
        }
    }

    public void setAdapter(BaseAdapter adapter){
        super.setAdapter(adapter);
        this.adapter = (GridAdapter) adapter;
    }

    public BaseAdapter getAdapter(){
        return (BaseAdapter) super.getAdapter();
    }

    public void setOnDataSelectionListener(OnDataSelectionListener listener){
        this.selectionListener = listener;
    }

    public OnDataSelectionListener getOnDataSelectionListener(){
        return selectionListener;
    }
}
