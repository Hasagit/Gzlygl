package com.gzlygl.model;

import com.gzlygl.javabeen.Diary;

import java.util.Date;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by DengJf on 2017/12/6.
 */

public class PersonModel implements IPersonModel {
    @Override
    public void getDiary(Date date,String user_id, FindListener<Diary> findListener) {
        BmobQuery<Diary> query=new BmobQuery<Diary>();
        query.setLimit(3);
        query.addWhereLessThan("createdAt",new BmobDate(date));
        query.addWhereEqualTo("user_id",user_id);
        query.order("-createdAt");
        query.findObjects(findListener);
    }
}
