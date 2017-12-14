package com.gzlygl.javabeen;

import cn.bmob.v3.BmobObject;

/**
 * Created by DengJf on 2017/12/6.
 */

public class Collection extends BmobObject{
    private String collection_user_id;
    private String diary_id;
    private String diary_msg;
    private String user_id;
    private String img_num;
    private String diary_date;

    public Collection(String collection_user_id, String diary_id, String diary_msg, String user_id, String img_num,String diary_date) {
        this.collection_user_id = collection_user_id;
        this.diary_id = diary_id;
        this.diary_msg = diary_msg;
        this.user_id = user_id;
        this.img_num = img_num;
        this.diary_date=diary_date;
    }

    public Collection() {
    }

    public String getCollection_user_id() {
        return collection_user_id;
    }

    public void setCollection_user_id(String collection_user_id) {
        this.collection_user_id = collection_user_id;
    }

    public String getDiary_id() {
        return diary_id;
    }

    public void setDiary_id(String diary_id) {
        this.diary_id = diary_id;
    }

    public String getDiary_msg() {
        return diary_msg;
    }

    public void setDiary_msg(String diary_msg) {
        this.diary_msg = diary_msg;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getImg_num() {
        return img_num;
    }

    public void setImg_num(String img_num) {
        this.img_num = img_num;
    }

    public String getDiary_date() {
        return diary_date;
    }

    public void setDiary_date(String diary_date) {
        this.diary_date = diary_date;
    }
}
