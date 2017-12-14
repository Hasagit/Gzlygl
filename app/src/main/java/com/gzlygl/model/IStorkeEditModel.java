package com.gzlygl.model;

import com.gzlygl.javabeen.TravelPlan;

import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by DengJf on 2017/12/11.
 */

public interface IStorkeEditModel {
    void saveTravelPlan(String user_id, String date, String place, String plan, SaveListener<String>saveListener);
}
