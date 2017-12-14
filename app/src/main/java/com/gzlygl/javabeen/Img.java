package com.gzlygl.javabeen;

import cn.bmob.v3.BmobObject;

/**
 * Created by DengJf on 2017/11/16.
 */

public class Img extends BmobObject{
    private String diary_id;
    private String img_group;
    private String img_url;
    private String img_name;
    private int order;

    public Img(String diary_id, String img_group, String img_url, String img_name, int order) {
        this.diary_id = diary_id;
        this.img_group = img_group;
        this.img_url = img_url;
        this.img_name = img_name;
        this.order = order;
    }

    public String getDiary_id() {
        return diary_id;
    }

    public String getImg_group() {
        return img_group;
    }

    public String getImg_url() {
        return img_url;
    }

    public String getImg_name() {
        return img_name;
    }


    public int getOrder() {
        return order;
    }

    public void setDiary_id(String diary_id) {
        this.diary_id = diary_id;
    }

    public void setImg_group(String img_group) {
        this.img_group = img_group;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public void setImg_name(String img_name) {
        this.img_name = img_name;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
