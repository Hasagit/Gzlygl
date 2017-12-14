package com.gzlygl.javabeen;

import cn.bmob.v3.BmobObject;

/**
 * Created by DengJf on 2017/12/5.
 */

public class Comment extends BmobObject{
    private String user_id;
    private String diary_id;
    private String commet_msg;

    public Comment(String user_id, String diary_id, String commet_msg) {
        this.user_id = user_id;
        this.diary_id = diary_id;
        this.commet_msg = commet_msg;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getDiary_id() {
        return diary_id;
    }

    public String getCommet_msg() {
        return commet_msg;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setDiary_id(String diary_id) {
        this.diary_id = diary_id;
    }

    public void setCommet_msg(String commet_msg) {
        this.commet_msg = commet_msg;
    }
}
