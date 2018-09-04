package com.example.caucse.calendarcheck;

import android.content.Context;
import android.graphics.Color;
import android.text.format.Time;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.util.Calendar;

public class GridAdapter extends BaseAdapter {

    Context context;
    TextView tv;
    TextView tv2;

    int layout;
    //임시 text OOX
    String text;
    LayoutInflater inflater;

    private MonthItem[] items;
    private int countColumn=7;

    int curYear;
    int curMonth;
    int firstDay;
    int lastDay;
    int startDay;

    Calendar calendar;
    private int selectedPosition = -1;

    public GridAdapter(Context context){
        super();
        this.context= context;
        init();
    }

    public GridAdapter(Context context, int layout, String text){
        this.context=context;
        this.layout = layout;
        this.text = text;
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        init();
    }

    private void init(){
        items = new MonthItem[7*6];

        calendar = Calendar.getInstance();
        recalculate();
        resetDayNumbers();
    }


    private void recalculate() {
        calendar.set(Calendar.DAY_OF_MONTH,1);

        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        firstDay = getFirstDay(dayOfWeek);

        curYear = calendar.get(Calendar.YEAR);
        curMonth = calendar.get(Calendar.MONTH);
        lastDay = getMonthLastDay(curYear, curMonth);
        startDay = getFirstDayOfWeek();
    }

    private int getFirstDay(int dayOfWeek) {
        int result = 0;
        if(dayOfWeek == Calendar.SUNDAY){
            result = 0;
        }else if (dayOfWeek == Calendar.MONDAY){
            result = 1;
        }else if (dayOfWeek == Calendar.TUESDAY){
            result = 2;
        }else if (dayOfWeek == Calendar.WEDNESDAY){
            result = 3;
        }else if (dayOfWeek == Calendar.THURSDAY){
            result = 4;
        }else if (dayOfWeek == Calendar.FRIDAY){
            result = 5;
        }else if (dayOfWeek == Calendar.SATURDAY){
            result = 6;
        }

        return result;
    }

    private int getFirstDayOfWeek() {
        int startDay = Calendar.getInstance().getFirstDayOfWeek();
        if(startDay == Calendar.SATURDAY){
            return Time.SATURDAY;
        } else if(startDay == Calendar.MONDAY){
            return Time.MONDAY;
        } else{
            return  Time.SUNDAY;
        }
    }

    private int getMonthLastDay(int curYear, int curMonth) {
        switch(curMonth){
            case 0:
            case 2:
            case 4:
            case 6:
            case 7:
            case 9:
            case 11:
                return(31);
            case 3:
            case 5:
            case 8:
            case 10:
                return(30);

            default:
                if(((curYear%4==0)&&(curYear%100!=0)) ||(curYear%400==0)){
                    return (29);
                }else{
                    return (28);
                }

        }
    }

    private void resetDayNumbers() {
        int set = 1;
        for(int i = 0; i<42;i++){
            int overNumber = 0;
            int dayNumber = (i+1) - firstDay;
            if(dayNumber < 1){
                if(curMonth-1 < 0){
                    overNumber = getMonthLastDay(curYear-1,12+curMonth)+dayNumber;
                } else {
                    overNumber = getMonthLastDay(curYear,curMonth-1)+dayNumber;
                }
                dayNumber = 0;
            } else if(dayNumber > lastDay){
                overNumber = dayNumber-lastDay;
                dayNumber = 0;
            }
            items[i] = new MonthItem(dayNumber,overNumber);
        }
    }

    @Override
    public int getCount() {
        return 7*6;
    }

    @Override
    public Object getItem(int i) {
        return items[i];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setItem(MonthItem item){
        int day = item.getDay();
        if(day != 0){
            tv.setText(String.valueOf(day));
        } else{
            tv.setText(String.valueOf(item.getOverValue()));
        }
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        //view가 최종적으로 띄울 MonthItemView임

        if(view ==null) {
            view = inflater.inflate(layout, null);
        }

        tv = (TextView)view.findViewById(R.id.day_num);
        tv2 = (TextView)view.findViewById(R.id.check);
        tv2.setText(text);

        GridView.LayoutParams params = new GridView.LayoutParams(GridView.LayoutParams.MATCH_PARENT, 130);

        int rowIndex = position/countColumn;
        int columnIndex = position%countColumn;

        setItem(items[position]);
        view.setLayoutParams(params);
        view.setPadding(2,2,2,2);

        tv.setGravity(Gravity.LEFT);
        tv2.setGravity(Gravity.LEFT);
        if(columnIndex==0){
            tv.setTextColor(Color.RED);
        } else if(columnIndex==6){
            tv.setTextColor(Color.BLUE);
        } else{
            tv.setTextColor(Color.BLACK);
        }

        if(position == getSelectedPosition()){
            view.setBackgroundColor(Color.rgb(242,203,97));
        } else if(items[position].getDay()==0){
            view.setBackgroundColor(Color.rgb(238,229,222));
        } else{
            view.setBackgroundColor(Color.WHITE);
        }

        tv2.setTextColor(Color.BLACK);
        return view;
    }

    public void setPreviousMonth(){
        calendar.add(Calendar.MONTH,-1);
        recalculate();
        resetDayNumbers();
        selectedPosition = -1;
    }

    public void setNextMonth(){
        calendar.add(Calendar.MONTH,1);
        recalculate();
        resetDayNumbers();
        selectedPosition = -1;
    }

    public void setSelectedPosition(int selectedPosition){
        this.selectedPosition = selectedPosition;
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public int getCurYear(){
        return curYear;
    }

    public int getCurMonth(){
        return curMonth;
    }

}
