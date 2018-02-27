package com.gzlygl.model;

import com.gzlygl.javabeen.Diary;
import com.gzlygl.javabeen.Plan;

import java.util.Date;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by DengJf on 2018/2/23.
 */

public class PlanModel implements IPlanModel {


    @Override
    public void getPlanData(Date date, String user_id, FindListener<Plan> findListener) {
        BmobQuery<Plan> query=new BmobQuery<Plan>();
        query.setLimit(6);
        query.addWhereEqualTo("create_user_id",user_id);
        query.addWhereLessThan("createdAt",new BmobDate(date));
        query.order("-createdAt");
        query.findObjects(findListener);
    }

    @Override
    public void savePlan(String user_id, String plan_name, SaveListener<String> saveListener) {
        Plan plan=new Plan(plan_name,user_id);
        plan.save(saveListener);
    }
}
