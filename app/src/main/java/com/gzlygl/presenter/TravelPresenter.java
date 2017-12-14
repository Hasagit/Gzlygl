package com.gzlygl.presenter;

import android.util.Log;

import com.gzlygl.javabeen.Diary;
import com.gzlygl.model.TravelModel;
import com.gzlygl.view.fragment.TravelFragment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SQLQueryListener;

/**
 * Created by DengJf on 2017/11/21.
 */

public class TravelPresenter {
    private TravelFragment view;
    private TravelModel model;

    public TravelPresenter(final TravelFragment view) {
        this.view = view;
        model=new TravelModel();
        model.getTwoDiary(new Date(System.currentTimeMillis()), new FindListener<Diary>() {
            @Override
            public void done(List<Diary> list, BmobException e) {
                if (e!=null){
                    view.showMsg(e.getMessage());
                }else {
                    view.refreshListView(list);
                }
            }
        });
    }

    public void getThreeDiary(FindListener<Diary>findListener){
        Date date=new Date(System.currentTimeMillis());
        model.getTwoDiary(date,findListener);
    }

    public void getMoreThreeDiary(String date_str,FindListener<Diary>findListener){
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = dateFormat.parse(date_str);
            model.getTwoDiary(date,findListener);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
