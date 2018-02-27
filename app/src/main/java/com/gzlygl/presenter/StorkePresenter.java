package com.gzlygl.presenter;

import android.content.Intent;

import com.gzlygl.adapter.StorkeBaseRecyclerViewAdapter;
import com.gzlygl.javabeen.TravelPlan;
import com.gzlygl.model.StorkeModel;
import com.gzlygl.util.PreferencesUtil;
import com.gzlygl.view.activity.StorkeEditActivity;
import com.gzlygl.view.fragment.StorkeFragment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by DengJf on 2017/12/10.
 */

public class StorkePresenter {
    private StorkeFragment view;
    private StorkeModel model;
    private PreferencesUtil preferencesUtil;
    private String plan_id;

    public StorkePresenter(StorkeFragment view) {
        this.view = view;
        model=new StorkeModel();
        preferencesUtil=new PreferencesUtil(view.getContext());
        //refreshStorkeList();
    }

    public void goToStorkeEditActivity(){
        Intent intent=new Intent(view.getContext(), StorkeEditActivity.class);
        intent.putExtra("plan_id",plan_id);
        view.startActivityForResult(intent,0);
    }


    public void refreshStorkeList(){
        Date date=new Date(System.currentTimeMillis()+10000);
        model.getFourTravelPlan(plan_id,
                date,
                new FindListener<TravelPlan>() {
                    @Override
                    public void done(List<TravelPlan> list, BmobException e) {
                        if (e==null){
                            view.refreshStorkeList(list);
                        }else {
                            view.showMessage(e.getMessage());
                        }
                        view.setRefreshing(false);
                    }
                }
        );

    }


    public void loadMoreStorkeList(final StorkeBaseRecyclerViewAdapter adapter){
        try {
            String date_str;
            if (adapter!=null&&adapter.getData().size()>0){
                date_str=adapter.getData().get(adapter.getData().size()-1).getCreatedAt();
            }else {
                view.setLoadingMore(false);
                return;
            }
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = dateFormat.parse(date_str);
            model.getFourTravelPlan(
                    preferencesUtil.getString("user_id"),
                    date,
                    new FindListener<TravelPlan>() {
                        @Override
                        public void done(List<TravelPlan> list, BmobException e) {
                            view.setLoadingMore(false);
                            if (e==null){
                                if (list.size()>0){
                                    adapter.addData(list);
                                }else {
                                    view.showMessage("没有更多数据了");
                                }
                            }else {
                                view.showMessage(e.getMessage());
                            }
                        }
                    }
            );
        } catch (ParseException e) {
            view.setLoadingMore(false);
            e.printStackTrace();
        }
    }

    public void setPlan_id(String plan_id) {
        this.plan_id = plan_id;
    }
}
