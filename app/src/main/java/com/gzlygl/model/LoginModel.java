package com.gzlygl.model;

import com.gzlygl.javabeen.User;

import java.io.File;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.SQLQueryListener;

/**
 * Created by DengJf on 2017/11/10.
 */

public class LoginModel implements ILoginModel{

    //以id作为索引搜索User
    @Override
    public void getUserById(String user_id,SQLQueryListener<User> sqlQueryListener) {
        BmobQuery<User> query=new BmobQuery<User>();
        String sql=String.format("select * from User where user_id='%s'",user_id);
        query.doSQLQuery(sql,sqlQueryListener);
    }

    //下载文件
    @Override
    public void dowloadUserImg(String url, String name, String group, String file_path,DownloadFileListener listener) {
        BmobFile bmobFile=new BmobFile(name,group,url);
        File file=new File(file_path);
        if (file.exists()){
            file.delete();
        }
        bmobFile.download(file,listener );

    }
}
