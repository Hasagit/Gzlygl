package com.gzlygl.adapter;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.provider.AlarmClock;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import com.example.liangmutian.airrecyclerview.swipetoloadlayout.BaseRecyclerAdapter;
import com.gzlygl.R;
import com.gzlygl.javabeen.TravelPlan;
import java.util.List;

/**
 * Created by DengJf on 2017/12/11.
 */

public class StorkeBaseRecyclerViewAdapter extends BaseRecyclerAdapter<BaseRecyclerAdapter.BaseRecyclerViewHolder, TravelPlan> {
    private Context context;
    private List<TravelPlan>data;
    private int resources;
    private Animation animation;



    public StorkeBaseRecyclerViewAdapter(Context context,int resources,List<TravelPlan> data) {
        super(data);
        this.context=context;
        this.resources=resources;
        this.data=data;
        animation= AnimationUtils.loadAnimation(context,R.anim.alpha);
    }


    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(resources,null);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder holder, int position, final TravelPlan data) {
        final ViewHolder viewHolder= (ViewHolder) holder;
        viewHolder.plan.setText(data.getPlan_msg());
        viewHolder.place.setText(data.getPlan_place());
        viewHolder.date.setText(data.getPlan_date());
        viewHolder.alarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.alarmBtn.startAnimation(animation);
                Intent alarms = new Intent(AlarmClock.ACTION_SET_ALARM);
                context.startActivity(alarms);
            }
        });
    }


    public List<TravelPlan>getData(){
        return data;
    }


    public void addData(List<TravelPlan>addData){
        for (int i=0;i<addData.size();i++){
            data.add(addData.get(i));
        }
        notifyDataSetChanged();
    }



    private class ViewHolder extends BaseRecyclerAdapter.BaseRecyclerViewHolder{
        public TextView date,place,plan;
        public CardView alarmBtn;
        public ViewHolder(View itemView) {
            super(itemView);
            date=(TextView)itemView.findViewById(R.id.date);
            place=(TextView)itemView.findViewById(R.id.place);
            plan=(TextView)itemView.findViewById(R.id.plan);
            alarmBtn=(CardView)itemView.findViewById(R.id.alarm_btn);
        }
    }


}
