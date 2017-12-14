package com.gzlygl.model;

import com.gzlygl.javabeen.User;

import cn.bmob.v3.listener.SQLQueryListener;

/**
 * Created by DengJf on 2017/11/9.
 */

public interface IUserModel {
    void getUserId(String user_id,SQLQueryListener<User> sqlQueryListener);

}
