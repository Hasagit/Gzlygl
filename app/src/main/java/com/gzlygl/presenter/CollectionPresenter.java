package com.gzlygl.presenter;

import com.gzlygl.javabeen.Collection;
import com.gzlygl.javabeen.Diary;
import com.gzlygl.model.CollectionModel;
import com.gzlygl.util.PreferencesUtil;
import com.gzlygl.view.fragment.CollectionFragment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by DengJf on 2017/12/6.
 */

public class CollectionPresenter {
    private CollectionModel model;
    private CollectionFragment view;
    private PreferencesUtil preferencesUtil;

    public CollectionPresenter(final CollectionFragment view) {
        this.view = view;
        model=new CollectionModel();
        preferencesUtil=new PreferencesUtil(view.getContext());
        getThreeCollectionDiary();

    }

    public void getThreeCollectionDiary(){
        model.getTwoCollectionDiary(
                new Date(System.currentTimeMillis()),
                preferencesUtil.getString("user_id"),
                new FindListener<Collection>() {
                    @Override
                    public void done(List<Collection> list, BmobException e) {
                        if (e==null){
                            List<Diary>data=new ArrayList<Diary>();
                            for (int i=0;i<list.size();i++){
                                Diary diary=new Diary(
                                        list.get(i).getDiary_msg(),
                                        list.get(i).getUser_id(),
                                        list.get(i).getImg_num()
                                );
                                diary.setColl_date(list.get(i).getCreatedAt());
                                diary.setCreatAtII(list.get(i).getDiary_date());
                                diary.setObjectId(list.get(i).getDiary_id());
                                data.add(diary);
                            }
                            view.refreshCollectioList(data);
                        }else {
                            view.showMessage(e.getMessage());
                        }
                        view.setRefreshing(false);
                    }
                });
    }

    public void getThreeMoreCollectionDiary(){
        try {
            List<Diary>diaries=view.getAdater().getData();
            String date_str;
            if (diaries.size()>0){
                date_str=diaries.get(diaries.size()-1).getColl_date();
            }else {
                view.setLoadingMore(false);
                view.showMessage("没有更多的数据了");
                return;
            }

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = dateFormat.parse(date_str);
            model.getTwoCollectionDiary(
                    date,
                    preferencesUtil.getString("user_id"),
                    new FindListener<Collection>() {
                        @Override
                        public void done(List<Collection> list, BmobException e) {
                            if (e==null){
                                List<Diary>data=new ArrayList<Diary>();
                                for (int i=0;i<list.size();i++){
                                    Diary diary=new Diary(
                                            list.get(i).getDiary_msg(),
                                            list.get(i).getUser_id(),
                                            list.get(i).getImg_num()
                                    );
                                    diary.setCreatAtII(list.get(i).getDiary_date());
                                    diary.setObjectId(list.get(i).getDiary_id());
                                    diary.setColl_date(list.get(i).getCreatedAt());
                                    data.add(diary);
                                }
                                if (data.size()>0){
                                    view.getAdater().addData(data);
                                }else {
                                    view.showMessage("没有更多的数据了");
                                }
                            }else {
                                view.showMessage(e.getMessage());
                            }
                            view.setLoadingMore(false);
                        }
                    });
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
