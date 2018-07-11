package com.example.caucse.calendar;

import android.support.v7.widget.AppCompatTextView;

import java.util.ArrayList;

public class DayList {

    private String day;
    private ArrayList<ScheduleVo> scheduleList;

    public String getDay(){
        return day;
    }

    public void setDay(String day){
        this.day = day;
    }

    public ArrayList<ScheduleVo> getScheduleList(){
        return scheduleList;
    }

    public void setScheduleList(ArrayList<ScheduleVo> scheduleList){
        this.scheduleList = scheduleList;
    }

}

class ScheduleVo{
    private String type;
    private String name;

    public ScheduleVo(String type, String name){
        this.type = type;
        this.name = name;
    }

    public String getType(){
        return type;
    }

    public String getName(){
        return name;
    }
}
