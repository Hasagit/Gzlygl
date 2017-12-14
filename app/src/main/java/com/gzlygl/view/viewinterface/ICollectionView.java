package com.gzlygl.view.viewinterface;

import com.gzlygl.adapter.DiaryBaseRecyclerViewAdapter;
import com.gzlygl.javabeen.Diary;

import java.util.List;

/**
 * Created by DengJf on 2017/12/6.
 */

public interface ICollectionView {
    void refreshCollectioList(List<Diary>data);


    void setLoadingMore(boolean loadingMore);

    void setRefreshing(boolean refreshing);

    void showMessage(String msg);

    DiaryBaseRecyclerViewAdapter getAdater();
}
