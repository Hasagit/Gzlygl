package com.gzlygl.model;

import com.gzlygl.javabeen.Plan;

import java.util.Date;

import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by DengJf on 2018/2/23.
 */

public interface IPlanModel {
    void getPlanData(Date date,String user_id, FindListener<Plan>findListener);

    void savePlan(String user_id, String plan_name, SaveListener<String> saveListener);
}
