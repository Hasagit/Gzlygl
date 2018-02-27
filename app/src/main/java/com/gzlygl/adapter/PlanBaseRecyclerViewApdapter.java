package com.gzlygl.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.liangmutian.airrecyclerview.swipetoloadlayout.BaseRecyclerAdapter;
import com.gzlygl.R;
import com.gzlygl.javabeen.Plan;

import java.util.List;

/**
 * Created by DengJf on 2018/2/26.
 */

public class PlanBaseRecyclerViewApdapter extends BaseRecyclerAdapter<PlanBaseRecyclerViewApdapter.ViewHolder, Plan>  {
    private List<Plan>data;
    private Context context;
    private int resources;
    private OnDeleteListener deleteListener;
    private OnCheckListener checkListener;

    public PlanBaseRecyclerViewApdapter(Context context,int resources,List<Plan> data) {
        super(data);
        this.data=data;
        this.resources=resources;
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(resources,null);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i, final Plan plan) {
        viewHolder.nameText.setText(plan.getPlan_name());
        viewHolder.dateText.setText(plan.getCreatedAt());
        viewHolder.delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deleteListener!=null){
                    deleteListener.onDelete(plan,i);
                }
            }
        });
        viewHolder.edBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkListener!=null){
                    checkListener.onCheck(plan,i);
                }
            }
        });
    }

    public List<Plan> getData() {
        return data;
    }

    public void addData(List<Plan>addDate){
        for (int i=0;i<addDate.size();i++){
            data.add(addDate.get(i));
        }
        notifyDataSetChanged();

    }

    public void setOnDeleteListener(OnDeleteListener deleteListener) {
        this.deleteListener = deleteListener;
    }

    public void setOnCheckListener(OnCheckListener checkListener) {
        this.checkListener = checkListener;
    }

    public class ViewHolder extends BaseRecyclerAdapter.BaseRecyclerViewHolder{
        public TextView nameText,dateText,edBtn,delBtn;
        public ViewHolder(View itemView) {
            super(itemView);
            nameText=(TextView) itemView.findViewById(R.id.name);
            dateText=(TextView)itemView.findViewById(R.id.date);
            edBtn=(TextView)itemView.findViewById(R.id.ed_btn);
            delBtn=(TextView)itemView.findViewById(R.id.delete);
        }
    }

    public interface OnDeleteListener{
        void onDelete(Plan plan,int position);
    }

    public interface OnCheckListener{
        void onCheck(Plan plan,int position);
    }
}
