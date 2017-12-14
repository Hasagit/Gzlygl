package com.gzlygl.javabeen;

import java.util.Date;

import cn.bmob.v3.BmobObject;

/**
 * Created by DengJf on 2017/12/7.
 */

public class TravelPlan extends BmobObject{
    private String user_id;
    private String plan_date;
    private String plan_place;
    private String plan_msg;

    public TravelPlan(String user_id, String plan_date, String plan_place, String plan_msg) {
        this.user_id = user_id;
        this.plan_date = plan_date;
        this.plan_place = plan_place;
        this.plan_msg = plan_msg;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPlan_date() {
        return plan_date;
    }

    public void setPlan_date(String plan_date) {
        this.plan_date = plan_date;
    }

    public String getPlan_place() {
        return plan_place;
    }

    public void setPlan_place(String plan_place) {
        this.plan_place = plan_place;
    }

    public String getPlan_msg() {
        return plan_msg;
    }

    public void setPlan_msg(String plan_msg) {
        this.plan_msg = plan_msg;
    }
}
