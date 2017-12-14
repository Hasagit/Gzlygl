package com.gzlygl.model;

import com.gzlygl.javabeen.Diary;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SQLQueryListener;

/**
 * Created by DengJf on 2017/11/21.
 */

public class TravelModel implements ITravelModel{

    @Override
    public void getTwoDiary(Date date, FindListener<Diary>findListener) {
        BmobQuery<Diary>query=new BmobQuery<Diary>();
        query.setLimit(3);
        query.addWhereLessThan("createdAt",new BmobDate(date));
        query.order("-createdAt");
        query.findObjects(findListener);
    }
}
