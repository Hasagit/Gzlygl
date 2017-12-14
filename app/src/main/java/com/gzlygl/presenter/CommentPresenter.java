package com.gzlygl.presenter;

import com.gzlygl.javabeen.Comment;
import com.gzlygl.model.CommentModel;
import com.gzlygl.view.activity.CommentActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by DengJf on 2017/12/4.
 */

public class CommentPresenter {
    private CommentActivity view;
    private CommentModel model;
    private String diary_id;

    public CommentPresenter(final CommentActivity view, String diary_id) {
        this.view = view;
        model=new CommentModel();
        this.diary_id=diary_id;
        model.getComment(new Date(System.currentTimeMillis()),diary_id, new FindListener<Comment>() {
            @Override
            public void done(List<Comment> list, BmobException e) {
                if (e==null){
                    view.refreshCommenList(list);
                }
            }
        });
    }


    public void getFourComment(String date_str,String diary_id,FindListener<Comment>findListener){
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = dateFormat.parse(date_str);
            model.getComment(date,diary_id,findListener);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }


    public void getComment(Date date,String diary_id,FindListener<Comment>findListener){
        model.getComment(date,diary_id,findListener);
    }


    public void getMoreComment(String date_str,String diary_id,FindListener<Comment>findListener){
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = dateFormat.parse(date_str);
            /*Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.SECOND, 1);*/
            model.getMoreFourComment(date,diary_id,findListener);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
