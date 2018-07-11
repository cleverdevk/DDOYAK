package com.example.caucse.calendar;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Calendar;

public class ScheduleCalendar extends LinearLayout implements View.OnClickListener {

    private enum dayOfWeek{
        SUN, MON, TUE, WED, THU, FRI, SAT
    }
    private final String PM_COLOR = "#FF4000";
    private final String BM_COLOR = "#FF8000";

    private Context context;
    private TextView monthText;
    private GridView monthView;

    private ArrayList<DayList> dayList;
    private Calendar calendar;
    private ScheduleCalendarAdapter adapter;
    private String today;

    public ScheduleCalendar(Context context, AttributeSet attrs) {
        super(context, attrs);
        int curYear = calendar.get(Calendar.YEAR);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        this.context = context;
        monthText = (TextView) findViewById(R.id.monthText);
        calendar = Calendar.getInstance();
        monthView = (GridView) findViewById(R.id.monthView);
        Button prevButton = (Button) findViewById(R.id.monthPrevious);
        Button nextButton = (Button) findViewById(R.id.monthNext);
        prevButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);

        int curMonth = calendar.get(Calendar.MONTH) + 1;
        StringBuffer buffer = new StringBuffer();
        buffer.append(curYear);
        buffer.append(curMonth);
        buffer.append(calendar.get(Calendar.DATE));
        today = buffer.toString();

        //Top에 일/월/화/수/목/금/토 띄워주기
        ScalableLayout scalableLayout = new ScalableLayout(context, 70, 7);
        TextView sun = createGridViewTop(dayOfWeek.SUN.name());
        scalableLayout.addView(sun, 0, 0, 10, 10);
        scalableLayout.setScale_TextSize(sun, 2);
        TextView mon = createGridViewTop(dayOfWeek.MON.name());
        scalableLayout.addView(mon, 10, 0, 10, 10);
        scalableLayout.setScale_TextSize(mon, 2);
        TextView tue = createGridViewTop(dayOfWeek.TUE.name());
        scalableLayout.addView(tue, 20, 0, 10, 10);
        scalableLayout.setScale_TextSize(tue, 2);
        TextView wed = createGridViewTop(dayOfWeek.WED.name());
        scalableLayout.addView(wed, 30, 0, 10, 10);
        scalableLayout.setScale_TextSize(wed, 2);
        TextView thu = createGridViewTop(dayOfWeek.THU.name());
        scalableLayout.addView(thu, 40, 0, 10, 10);
        scalableLayout.setScale_TextSize(thu, 2);
        TextView fri = createGridViewTop(dayOfWeek.FRI.name());
        scalableLayout.addView(fri, 50, 0, 10, 10);
        scalableLayout.setScale_TextSize(fri, 2);
        TextView sat = createGridViewTop(dayOfWeek.SAT.name());
        scalableLayout.addView(sat, 60, 0, 10, 10);
        scalableLayout.setScale_TextSize(sat, 2);


        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.gridviewcontainer);
        linearLayout.addView(scalableLayout, 0);
    }

    private TextView createGridViewTop(String dayOfWeek) {
        TextView tv = new TextView(context);
        tv.setText(dayOfWeek);
        tv.setTypeface(null, Typeface.BOLD);
        tv.setGravity(Gravity.CENTER);
        return tv;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.monthPrevious: //이전 달로 이동
                refreshAdapter(-1);
                break;
            case R.id.monthNext:    //다음 달로 이동
                refreshAdapter(1);
                break;
        }
    }

    private void refreshAdapter(int value) {    //adapter 초기화
        dayList = new ArrayList<DayList>();
        calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH));
        calendar.add(Calendar.MONTH, value);
        int dayNum = calendar.get(Calendar.DAY_OF_WEEK);

        //1일 앞에 공백 추가
        for(int i=1;i<dayNum;i++){
            DayList vo = new DayList();
            vo.setDay("");
            dayList.add(vo);
        }

        //1일부터 마지막 날까지 값 저장
        for(int i=0;i<calendar.getActualMaximum(Calendar.DAY_OF_MONTH);i++){
            DayList vo = new DayList();
            vo.setDay(String.valueOf(i+1));
            dayList.add(vo);
        }

        //스케줄 저장
        ArrayList<DayList> scheduleList = getSchedule();
        for(DayList schedule : scheduleList){
            int sDay = Integer.parseInt(schedule.getDay());
            for(DayList vo : dayList){
                int day;
                try{
                    day = Integer.parseInt(vo.getDay());
                } catch (NumberFormatException e){
                    continue;
                }

                if(sDay == day){
                    vo.setScheduleList(new ArrayList<ScheduleVo>());
                    for(int i=0;i<schedule.getScheduleList().size();i++){
                        ScheduleVo sv = schedule.getScheduleList().get(i);
                        vo.getScheduleList().add(sv);
                    }
                }
            }
        }

        //@년 @월 텍스트 설정
        monthText.setText(calendar.get(Calendar.YEAR)+"년 "+(calendar.get(Calendar.MONTH)+1)+"월");

        adapter = new ScheduleCalendarAdapter();
        monthView.setAdapter(adapter);
    }

    //스케줄 받아오기
    private ArrayList<DayList> getSchedule() {
        ArrayList<DayList> list = new ArrayList<>();
        return list;
    }


    class ScheduleCalendarAdapter extends BaseAdapter implements OnClickListener{

        private LayoutInflater inflater;
        private class ViewHolder{
            public TextView day;
        }

        public ScheduleCalendarAdapter(){
            inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return dayList.size();
        }

        @Override
        public Object getItem(int position) {
            return dayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if(convertView == null){
                convertView = inflater.inflate(R.layout.empty_layout, parent, false);
                Context ctx = convertView.getContext();
                ScalableLayout scalableLayout = new ScalableLayout(ctx,80,100);
                TextView daytv = new TextView(ctx);
                daytv.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
                scalableLayout.addView(daytv,0,0,80,25);
                scalableLayout.setScale_TextSize(daytv,20);
                ((LinearLayout) convertView).addView(scalableLayout);

                holder = new ViewHolder();
                holder.day = daytv;
                convertView.setTag(holder);
            } else{
                holder = (ViewHolder)convertView.getTag();
            }

            DayList vo = dayList.get(position);
            holder.day.setText(vo.getDay());
            if(vo.getDay().equals("")){
                convertView.setClickable(false);
            }

            //주말 텍스트 색깔 설정
            if(position % 7 == dayOfWeek.SAT.ordinal()){
                holder.day.setTextColor(Color.parseColor("#2E64FE"));
            } else if (position % 7 == dayOfWeek.SUN.ordinal()){
                holder.day.setTextColor(Color.RED);
            }

            //스케줄 리스트 설정
            if(vo.getScheduleList() != null){
                LinearLayout linearLayout = (LinearLayout) convertView;
                ScalableLayout scalableLayout = (ScalableLayout) linearLayout.getChildAt(0);
                int scheduleCnt = vo.getScheduleList().size();
                for(int i=0;i<scheduleCnt;i++) {
                    ScheduleVo sv = vo.getScheduleList().get(i);
                    TextView scheduleTv = new TextView(convertView.getContext());
                    scheduleTv.setGravity(Gravity.CENTER_VERTICAL);
                    if (i == 2) {
                        scalableLayout.addView(scheduleTv, 0, 70, 80, 25);
                        scalableLayout.setScale_TextSize(scheduleTv, 15);
                        scheduleTv.setText("TOTAL: " + String.valueOf(scheduleCnt + 1));
                        break;
                    }
                    scheduleTv.setTextColor(Color.WHITE);
                    scheduleTv.setSingleLine();
                    scalableLayout.addView(scheduleTv, 0, 25 + (i * 25), 80, 25);
                    scalableLayout.setScale_TextSize(scheduleTv, 15);
                    if (sv.getType().equals("PM")) {
                        scheduleTv.setBackgroundColor(Color.parseColor(PM_COLOR));
                    } else if (sv.getType().equals("BM")) {
                        scheduleTv.setBackgroundColor(Color.parseColor(BM_COLOR));
                    }
                    scheduleTv.setText(sv.getName());
                }
            }

            if(today.equals(calendar.get(Calendar.YEAR)+""+(calendar.get(Calendar.MONTH)+1)+""+vo.getDay())){
                holder.day.setText("★"+vo.getDay());
            }

            return convertView;
        }

        @Override
        public void onClick(View v) {

        }
    }
}
