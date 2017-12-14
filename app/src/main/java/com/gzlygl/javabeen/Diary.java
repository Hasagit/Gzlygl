package com.gzlygl.javabeen;

import java.io.Serializable;
import java.util.Date;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;

/**
 * Created by DengJf on 2017/11/16.
 */

public class Diary extends BmobObject implements Serializable {
    private String diary_msg;
    private String user_id;
    private String img_num;
    private String coll_date;

    public Diary( String diary_msg, String user_id, String img_num) {
        this.diary_msg = diary_msg;
        this.user_id = user_id;
        this.img_num = img_num;
    }


    public String getDiary_msg() {
        return diary_msg;
    }


    public String getUser_id() {
        return user_id;
    }

    public String getImg_num() {
        return img_num;
    }

    public void setDiary_msg(String diary_msg) {
        this.diary_msg = diary_msg;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setImg_num(String img_num) {
        this.img_num = img_num;
    }

    public void setCreatAtII(String creatAt){
        setCreatedAt(creatAt);
    }

    public String getColl_date() {
        return coll_date;
    }

    public void setColl_date(String coll_date) {
        this.coll_date = coll_date;
    }
}
