package com.gzlygl.model;

import com.gzlygl.javabeen.Diary;

import java.util.Date;

import cn.bmob.v3.listener.FindListener;

/**
 * Created by DengJf on 2017/11/21.
 */

public interface ITravelModel {
    void getTwoDiary(Date date, FindListener<Diary> findListener);
}
