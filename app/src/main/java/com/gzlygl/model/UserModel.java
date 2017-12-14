package com.gzlygl.model;

import com.gzlygl.javabeen.User;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.SQLQueryListener;

/**
 * Created by DengJf on 2017/11/9.
 */

public class UserModel implements IUserModel{


    //以用户id作为索引搜索User
    @Override
    public void getUserId(String user_id,SQLQueryListener<User> sqlQueryListener) {
        BmobQuery<User> query=new BmobQuery<User>();
        String sql=String.format("select * from User where user_id='%s'",user_id);
        query.doSQLQuery(sql,sqlQueryListener);
    }
}
