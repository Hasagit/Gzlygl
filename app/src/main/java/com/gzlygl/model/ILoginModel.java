package com.gzlygl.model;

import com.gzlygl.javabeen.User;

import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.SQLQueryListener;

/**
 * Created by DengJf on 2017/11/10.
 */

public interface ILoginModel {

    void getUserById(String user_id,SQLQueryListener<User> sqlQueryListener);

    void dowloadUserImg(String url, String name, String group, String file_path, DownloadFileListener listener);
}
