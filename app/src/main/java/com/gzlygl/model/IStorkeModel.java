package com.gzlygl.model;

import com.gzlygl.javabeen.TravelPlan;

import java.util.Date;

import cn.bmob.v3.listener.FindListener;

/**
 * Created by DengJf on 2017/12/10.
 */

public interface IStorkeModel {
    void getFourTravelPlan(String user_id, Date date, FindListener<TravelPlan>findListener);
}
