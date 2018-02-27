package com.gzlygl.javabeen;

import cn.bmob.v3.BmobObject;

/**
 * Created by DengJf on 2018/2/23.
 */

public class Plan extends BmobObject {
    public String plan_name;
    public String create_user_id;


    public Plan() {
    }

    public Plan(String plan_name, String create_user_id) {
        this.plan_name = plan_name;
        this.create_user_id = create_user_id;
    }

    public String getPlan_name() {
        return plan_name;
    }

    public void setPlan_name(String plan_name) {
        this.plan_name = plan_name;
    }

    public String getCreate_user_id() {
        return create_user_id;
    }

    public void setCreate_user_id(String create_user_id) {
        this.create_user_id = create_user_id;
    }
}
