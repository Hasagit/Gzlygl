package com.gzlygl.model;

import com.gzlygl.javabeen.Comment;

import java.util.Date;

import cn.bmob.v3.listener.FindListener;

/**
 * Created by DengJf on 2017/12/4.
 */

public interface ICommentModel {
    public void getComment(Date date,String diary_id, FindListener<Comment> findListener);

    public void getMoreFourComment(Date date,String diary_id, FindListener<Comment> findListener);
}
