package com.example.ilene.splashsrc;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //생성자. 표시할 데이터 전달 받음
    private ArrayList<ScheduleInfo> scheduleInfoArrayList;
    private static Context sContext;

    public ListAdapter(Context context, ArrayList<ScheduleInfo> scheduleitems){
        scheduleInfoArrayList = scheduleitems;
        sContext = context;
    }

    //RecyclerView의 item 표시
    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView date, startTime, endTime, schedule;

        RecyclerViewHolder(View view) {
            super(view);
            date = view.findViewById(R.id.date);
            startTime = view.findViewById(R.id.startTime);
            endTime = view.findViewById(R.id.endTime);
            schedule = view.findViewById(R.id.schedule);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(sContext,"click "+scheduleInfoArrayList.get(getAdapterPosition()).getSchedule(),Toast.LENGTH_SHORT).show();
                }
            });

            view.setOnLongClickListener(new View.OnLongClickListener(){
                @Override
                public boolean onLongClick(View v){
                    /*
                    final Dialog questionDialog = new Dialog(sContext);
                    questionDialog.setContentView(R.layout.delete_dialog);
                    questionDialog.setTitle("일정 삭제");
                    TextView question = (TextView)questionDialog.findViewById(R.id.question);

                    Button OK = (Button)questionDialog.findViewById(R.id.OK);
                    OK.setOnClickListener(new Button.OnClickListener(){
                        @Override
                        public void onClick(View v){
                            Toast.makeText(sContext,"remove "+scheduleInfoArrayList.get(getAdapterPosition()).getSchedule(),Toast.LENGTH_SHORT).show();
                            removeItemView(getAdapterPosition());
                            questionDialog.dismiss();
                        }
                    });

                    Button cancle = (Button)questionDialog.findViewById(R.id.cancle);
                    cancle.setOnClickListener(new Button.OnClickListener(){
                        @Override
                        public void onClick(View v){
                            questionDialog.dismiss();
                        }
                    });

                    questionDialog.show();
                    */
                    Toast.makeText(sContext,"remove "+scheduleInfoArrayList.get(getAdapterPosition()).getSchedule(),Toast.LENGTH_SHORT).show();
                    removeItemView(getAdapterPosition());
                    return false;
                }
            });
        }
    }

    //item 표시에 필요한 xml 가져옴
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule_itemview, parent, false);
        return new RecyclerViewHolder(v);
    }

    //표시되어질 텍스트 설정
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        RecyclerViewHolder myViewHolder = (RecyclerViewHolder) holder;

        myViewHolder.date.setText(scheduleInfoArrayList.get(position).date);
        myViewHolder.startTime.setText(scheduleInfoArrayList.get(position).startTime);
        myViewHolder.endTime.setText(scheduleInfoArrayList.get(position).endTime);
        myViewHolder.schedule.setText(scheduleInfoArrayList.get(position).schedule);
    }

    //item 개수 리턴
    @Override
    public int getItemCount() {
        return scheduleInfoArrayList.size();
    }

    private void removeItemView(int position){
        scheduleInfoArrayList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,scheduleInfoArrayList.size());
    }
}
