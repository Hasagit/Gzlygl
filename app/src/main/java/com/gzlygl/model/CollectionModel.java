package com.gzlygl.model;

import com.gzlygl.javabeen.Collection;
import com.gzlygl.javabeen.Diary;

import java.util.Date;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by DengJf on 2017/12/6.
 */

public class CollectionModel implements ICollectionModel{

    @Override
    public void getTwoCollectionDiary(Date date,String user_id, FindListener<Collection> findListener) {
        BmobQuery<Collection> query=new BmobQuery<Collection>();
        query.setLimit(3);
        query.addWhereLessThan("createdAt",new BmobDate(date));
        query.addWhereEqualTo("collection_user_id",user_id);
        query.order("-createdAt");
        query.findObjects(findListener);
    }
}
