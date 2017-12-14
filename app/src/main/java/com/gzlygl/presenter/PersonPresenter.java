package com.gzlygl.presenter;

import com.gzlygl.adapter.DiaryBaseRecyclerViewAdapter;
import com.gzlygl.javabeen.Diary;
import com.gzlygl.model.PersonModel;
import com.gzlygl.util.PreferencesUtil;
import com.gzlygl.view.fragment.PersonFragment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by DengJf on 2017/12/6.
 */

public class PersonPresenter {
    private PersonFragment view;
    private PersonModel model;
    private PreferencesUtil preferencesUtil;

    public PersonPresenter(PersonFragment view) {
        this.view = view;
        model=new PersonModel();
        preferencesUtil=new PreferencesUtil(view.getContext());
        getData();
    }


    public void getData(){
        Date date=new Date(System.currentTimeMillis());
        String user_id=preferencesUtil.getString("user_id");
        model.getDiary(date, user_id, new FindListener<Diary>() {
            @Override
            public void done(List<Diary> list, BmobException e) {
                view.setRefreshing(false);
                if (e==null){
                    if (list.size()==0){
                        view.showMessage("您还没有发表过游记");
                    }
                    view.refreshPersonList(list);
                }else {
                    view.showMessage(e.getMessage());
                }
            }
        });
    }

    public void getMoreThreeData(final DiaryBaseRecyclerViewAdapter adapter){
        try {
            String date_str;
            if (adapter!=null&&adapter.getItemCount()>0){
                date_str=adapter.getData().get(adapter.getItemCount()-1).getCreatedAt();
            }else {
                view.showMessage("没有更多数据了");
                view.setLoadingMore(false);
                return;
            }
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = dateFormat.parse(date_str);
            model.getDiary(date, preferencesUtil.getString("user_id"), new FindListener<Diary>() {
                @Override
                public void done(List<Diary> list, BmobException e) {
                    view.setLoadingMore(false);
                    if (e==null){
                        if (list.size()>0){
                            adapter.addData(list);
                        }else {
                            view.showMessage("没有更多数据了");
                        }
                    }else {
                        view.showMessage(e.getMessage());
                    }
                }
            });
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
