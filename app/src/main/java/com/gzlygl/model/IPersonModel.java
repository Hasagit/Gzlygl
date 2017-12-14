package com.gzlygl.model;

import com.gzlygl.javabeen.Diary;

import java.util.Date;

import cn.bmob.v3.listener.FindListener;

/**
 * Created by DengJf on 2017/12/6.
 */

public interface IPersonModel {
    void getDiary(Date date,String user_id, FindListener<Diary> findListener);
}
