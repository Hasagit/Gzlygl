package com.gzlygl.presenter;

import android.content.Context;

import com.gzlygl.adapter.PlanBaseRecyclerViewApdapter;
import com.gzlygl.javabeen.Plan;
import com.gzlygl.model.PlanModel;
import com.gzlygl.util.PreferencesUtil;
import com.gzlygl.view.viewinterface.IPlanView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by DengJf on 2018/2/23.
 */

public class PlanPresenter {
    private Context context;
    private PlanModel planModel;
    private PreferencesUtil preferencesUtil;
    private IPlanView view;

    public PlanPresenter(Context context,IPlanView view) {
        this.context = context;
        planModel=new PlanModel();
        preferencesUtil=new PreferencesUtil(context);
        this.view=view;
        getPlanData();
    }

    public void getPlanData(){
        String user_id=preferencesUtil.getString("user_id");
        Date date=new Date(System.currentTimeMillis());
        planModel.getPlanData(date, user_id, new FindListener<Plan>() {
            @Override
            public void done(List<Plan> list, BmobException e) {
                view.setPlanListRefreshing(false);
                if (e==null){
                    view.refreshingList(list);
                }else {
                    view.showToastMsg(e.getMessage());
                }
            }
        });
    }

    public void savePlan(String planName){
        String user_id=preferencesUtil.getString("user_id");
        view.setShowProgressDialogEnable(true);
        planModel.savePlan(user_id, planName, new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                view.setShowProgressDialogEnable(false);
                if (e==null){
                    getPlanData();
                }else {
                    view.showToastMsg(s);
                }
            }
        });

    }

    public void loadMoreDate(final PlanBaseRecyclerViewApdapter apdapter){
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = dateFormat.parse(apdapter.getData().get(apdapter.getData().size()-1).getCreatedAt());
            String user_id=preferencesUtil.getString("user_id");
            planModel.getPlanData(date, user_id, new FindListener<Plan>() {
                @Override
                public void done(List<Plan> list, BmobException e) {
                    view.setPlanListLoadMore(false);
                    if (e==null){
                        if (list.size()>0){
                            apdapter.addData(list);
                        }else {
                            view.showToastMsg("没有更多数据了");
                        }
                    }else {
                        view.showToastMsg(e.getMessage());
                    }
                }
            });
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void deleteData(Plan plan){
        view.setShowProgressDialogEnable(true);
        Plan plan_delete=new Plan();
        plan_delete.setObjectId(plan.getObjectId());
        plan_delete.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                view.setShowProgressDialogEnable(false);
                if (e==null){
                    getPlanData();
                }else {
                    view.showToastMsg(e.getMessage());
                }
            }
        });

    }
}
