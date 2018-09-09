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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;

public class GridAdapter extends BaseAdapter {

    Context context;
    LinearLayout linear1;
    TextView tv;
    String packName;

    String medicine_taken = "takencircle";
    String medicine_not_taken = "nottaken";
    public int taken_resID;
    public int nottaken_resID;

    int layout;
    //임시 text OOX
    String text;
    LayoutInflater inflater;

    private MonthItem[] items;
    private int countColumn = 7;

    int curYear;
    int curMonth;
    int firstDay;
    int lastDay;
    int startDay;

    Calendar calendar;
    private int selectedPosition = -1;


    public GridAdapter(Context context) {
        super();
        this.context = context;
        init();
    }

    public GridAdapter(Context context, int layout, String text) {
        this.context = context;
        this.layout = layout;
        this.text = text;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        init();

    }

    private void init() {
        items = new MonthItem[7 * 6];

        calendar = Calendar.getInstance();
        recalculate();
        resetDayNumbers();
    }

    private void recalculate() {
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        firstDay = getFirstDay(dayOfWeek);

        curYear = calendar.get(Calendar.YEAR);
        curMonth = calendar.get(Calendar.MONTH);
        lastDay = getMonthLastDay(curYear, curMonth);
        startDay = getFirstDayOfWeek();
    }

    private int getFirstDay(int dayOfWeek) {
        int result = 0;
        if (dayOfWeek == Calendar.SUNDAY) {
            result = 0;
        } else if (dayOfWeek == Calendar.MONDAY) {
            result = 1;
        } else if (dayOfWeek == Calendar.TUESDAY) {
            result = 2;
        } else if (dayOfWeek == Calendar.WEDNESDAY) {
            result = 3;
        } else if (dayOfWeek == Calendar.THURSDAY) {
            result = 4;
        } else if (dayOfWeek == Calendar.FRIDAY) {
            result = 5;
        } else if (dayOfWeek == Calendar.SATURDAY) {
            result = 6;
        }

        return result;
    }

    private int getFirstDayOfWeek() {
        int startDay = Calendar.getInstance().getFirstDayOfWeek();
        if (startDay == Calendar.SATURDAY) {
            return Time.SATURDAY;
        } else if (startDay == Calendar.MONDAY) {
            return Time.MONDAY;
        } else {
            return Time.SUNDAY;
        }
    }

    private int getMonthLastDay(int curYear, int curMonth) {
        switch (curMonth) {
            case 0:
            case 2:
            case 4:
            case 6:
            case 7:
            case 9:
            case 11:
                return (31);
            case 3:
            case 5:
            case 8:
            case 10:
                return (30);

            default:
                if (((curYear % 4 == 0) && (curYear % 100 != 0)) || (curYear % 400 == 0)) {
                    return (29);
                } else {
                    return (28);
                }

        }
    }

    private void resetDayNumbers() {
        int set = 1;
        for (int i = 0; i < 42; i++) {
            int overNumber = 0;
            int dayNumber = (i + 1) - firstDay;
            if (dayNumber < 1) {
                if (curMonth - 1 < 0) {
                    overNumber = getMonthLastDay(curYear - 1, 12 + curMonth) + dayNumber;
                } else {
                    overNumber = getMonthLastDay(curYear, curMonth - 1) + dayNumber;
                }
                dayNumber = 0;
            } else if (dayNumber > lastDay) {
                overNumber = dayNumber - lastDay;
                dayNumber = 0;
            }
            items[i] = new MonthItem(dayNumber, overNumber);

        }
    }

    @Override
    public int getCount() {
        return 7 * 6;
    }

    @Override
    public Object getItem(int i) {
        return items[i];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setItem(MonthItem item) {
        int day = item.getDay();
        if (day != 0) {
            tv.setText(String.valueOf(day));
        } else {
            tv.setText(String.valueOf(item.getOverValue()));
        }
    }

    //캘린더 위에 동그라미로 띄우기, int position 함으로써 한 날에만 이 계산 수행하는 것 -- 하루만 계산하기 고려할 필요 없다
    public void UpdateImageonCalendar(int position) {
        MonthItem item = (MonthItem) MainActivity.gridAdapter.getItem(position);
        int curDay = item.getDay();
        int perDayCount = 0;
        packName = context.getPackageName();
        taken_resID = context.getResources().getIdentifier(medicine_taken, "drawable", packName);
        nottaken_resID = context.getResources().getIdentifier(medicine_not_taken, "drawable", packName);

        ImageView takenimage = new ImageView(context);
        takenimage.setImageResource(taken_resID);
        takenimage.setAdjustViewBounds(true);

        ImageView nottakenimage = new ImageView(context);
        nottakenimage.setImageResource(nottaken_resID);
        nottakenimage.setAdjustViewBounds(true);

        linear1.removeAllViews();


        //해당 날에 몇 회분 복용해야 하는지(동그라미 몇개 필요한지) perDayCount로 받기
        for (int k = 0; k < MainActivity.data.size(); k++) {
            if ((Integer.parseInt(MainActivity.data.get(k).getYear()) == curYear)
                    && (Integer.parseInt(MainActivity.data.get(k).getMonth()) == (curMonth) + 1)
                    && (Integer.parseInt(MainActivity.data.get(k).getDay()) == curDay))
                perDayCount++;
        }


        for (int i = 0; i < MainActivity.data.size(); i++) {
            if ((Integer.parseInt(MainActivity.data.get(i).getYear()) == curYear)
                    && (Integer.parseInt(MainActivity.data.get(i).getMonth()) == (curMonth) + 1)
                    && (Integer.parseInt(MainActivity.data.get(i).getDay()) == curDay)) {

                if (Integer.parseInt(MainActivity.data.get(i).getCheck()) == 1){
                    item.setCheckingValue(1);//복용
                    linear1.addView(takenimage, 25, 25);}
                else if (Integer.parseInt(MainActivity.data.get(i).getCheck()) == 0){
                    item.setCheckingValue(0);//미복용
                    linear1.addView(nottakenimage, 25, 25);}
                else
                    item.setCheckingValue(2);
            }
        }
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        //view가 최종적으로 띄울 MonthItemView임
        if (view == null) {
            view = inflater.inflate(layout, null);
        }

        tv = (TextView) view.findViewById(R.id.day_num);
        linear1 = (LinearLayout) view.findViewById(R.id.linear1);


        /**
         circlemark = (ImageView) view.findViewById(R.id.circle_mark);
         circlemark.setVisibility(View.GONE);
         circlemark2 = (ImageView) view.findViewById(R.id.circle_mark2);
         circlemark2.setVisibility(View.GONE);
         circlemark3 = (ImageView) view.findViewById(R.id.circle_mark3);
         circlemark3.setVisibility(View.GONE);
         circlemark4 = (ImageView) view.findViewById(R.id.circle_mark4);
         circlemark4.setVisibility(View.GONE);
         circlemark5 = (ImageView) view.findViewById(R.id.circle_mark5);
         circlemark5.setVisibility(View.GONE);
         circlemark6 = (ImageView) view.findViewById(R.id.circle_mark6);
         circlemark6.setVisibility(View.GONE);
         **/


        GridView.LayoutParams params = new GridView.LayoutParams(GridView.LayoutParams.MATCH_PARENT, 160);

        int rowIndex = position / countColumn;
        int columnIndex = position % countColumn;


        //여기서 setItem!!!!
        setItem(items[position]);
        UpdateImageonCalendar(position);

        view.setLayoutParams(params);
        view.setPadding(2, 2, 2, 2);

        tv.setGravity(Gravity.LEFT);
        if (columnIndex == 0) {
            tv.setTextColor(Color.RED);
        } else if (columnIndex == 6) {
            tv.setTextColor(Color.BLUE);
        } else {
            tv.setTextColor(Color.BLACK);
        }

        if (position == getSelectedPosition()) {
            view.setBackgroundColor(Color.rgb(242, 203, 97));
        } else if (items[position].getDay() == 0) {
            view.setBackgroundColor(Color.rgb(238, 229, 222));
        } else {
            view.setBackgroundColor(Color.WHITE);
        }

        return view;
    }

    public void setPreviousMonth() {
        calendar.add(Calendar.MONTH, -1);
        recalculate();
        resetDayNumbers();
        selectedPosition = -1;
    }

    public void setNextMonth() {
        calendar.add(Calendar.MONTH, 1);
        recalculate();
        resetDayNumbers();
        selectedPosition = -1;
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public int getCurYear() {
        return curYear;
    }

    public int getCurMonth() {
        return curMonth;
    }


}

