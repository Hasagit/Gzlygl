package com.gzlygl.javabeen;

import cn.bmob.v3.BmobObject;

/**
 * Created by DengJf on 2017/11/9.
 */

public class User extends BmobObject {
    private String user_id;
    private String user_name;
    private String user_pwd;
    private String user_sex;
    private String img_url;
    private String img_name;
    private String img_group;

    public User(String user_id, String user_name, String user_pwd, String user_sex, String img_url, String img_name, String img_group) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_pwd = user_pwd;
        this.user_sex = user_sex;
        this.img_url = img_url;
        this.img_name = img_name;
        this.img_group = img_group;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getUser_pwd() {
        return user_pwd;
    }


    public String getUser_sex(){
        return user_sex;
    }

    public String getImg_url() {
        return img_url;
    }

    public String getImg_name() {
        return img_name;
    }

    public String getImg_group() {
        return img_group;
    }


    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setUser_pwd(String user_pwd) {
        this.user_pwd = user_pwd;
    }

    public void setUser_sex(String user_sex) {
        this.user_sex = user_sex;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public void setImg_name(String img_name) {
        this.img_name = img_name;
    }

    public void setImg_group(String img_group) {
        this.img_group = img_group;
    }

}
