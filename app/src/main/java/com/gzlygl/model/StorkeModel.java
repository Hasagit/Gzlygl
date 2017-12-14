package com.gzlygl.model;

import com.gzlygl.javabeen.TravelPlan;

import java.util.Date;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by DengJf on 2017/12/10.
 */

public class StorkeModel implements IStorkeModel{
    @Override
    public void getFourTravelPlan(String user_id, Date date,FindListener<TravelPlan> findListener) {
        BmobQuery<TravelPlan>query=new BmobQuery<>();
        query.addWhereLessThan("createdAt",new BmobDate(date));
        query.addWhereEqualTo("user_id",user_id);
        query.setLimit(4);
        query.order("-createdAt");
        query.findObjects(findListener);
    }
}
