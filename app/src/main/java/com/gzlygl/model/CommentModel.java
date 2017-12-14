package com.gzlygl.model;

import com.gzlygl.javabeen.Comment;
import com.gzlygl.javabeen.Diary;

import java.util.Date;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by DengJf on 2017/12/4.
 */

public class CommentModel implements ICommentModel {
    @Override
    public void getComment(Date date,String diary_id, FindListener<Comment> findListener) {
        BmobQuery<Comment> query=new BmobQuery<>();
        query.setLimit(10);
        query.addWhereEqualTo("diary_id",diary_id);
        query.addWhereLessThan("createdAt",new BmobDate(date));
        query.order("-createdAt");
        query.findObjects(findListener);
    }

    @Override
    public void getMoreFourComment(Date date, String diary_id, FindListener<Comment> findListener) {
        BmobQuery<Comment> query=new BmobQuery<>();
        query.setLimit(10);
        query.addWhereEqualTo("diary_id",diary_id);
        query.addWhereLessThan("createdAt",new BmobDate(date));
        query.order("-createdAt");
        query.findObjects(findListener);
    }
}
