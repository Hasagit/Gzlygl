package com.gzlygl.presenter;

import com.gzlygl.model.StorkeEditModel;
import com.gzlygl.util.PreferencesUtil;
import com.gzlygl.view.activity.StorkeEditActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by DengJf on 2017/12/11.
 */

public class StorkeEditPresenter {
    private StorkeEditModel model;
    private StorkeEditActivity view;
    private PreferencesUtil preferencesUtil;

    public StorkeEditPresenter(StorkeEditActivity view) {
        this.view = view;
        model=new StorkeEditModel();
        preferencesUtil=new PreferencesUtil(view);
    }

    public void savePlan(String date_str,String place,String plan){
        String user_id=preferencesUtil.getString("user_id");
        view.setPlaceErrorEnable(false);
        view.setDateErrorEnable(false);
        if (date_str.equals("")){
            view.setDateErrorEnable(true);
            view.setDateError("时间不能为空");
            return;
        }
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date = dateFormat.parse(date_str);
        } catch (ParseException e) {
            view.setDateErrorEnable(true);
            view.setDateError("请输入正确时间格式");
            e.printStackTrace();
            return;
        }


        if (place.equals("")){
            view.setPlaceErrorEnable(true);
            view.setPlaceError("地点不能为空");
            return;
        }
        if (plan.equals("")){
            view.showMessage("事件不能为空");
            return;
        }
        view.setProgressDialogShowing(true);
        model.saveTravelPlan(user_id, date_str, place, plan, new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                view.setProgressDialogShowing(false);
                if (e==null){
                    view.setResult(0);
                    view.finish();
                }else {
                    view.showMessage(e.getMessage());
                }
            }
        });

    }
}
