package com.gzlygl.view.viewinterface;

import com.gzlygl.javabeen.Diary;

import java.util.List;

/**
 * Created by DengJf on 2017/12/6.
 */

public interface IPersonView {
    void refreshPersonList(List<Diary>data);

    void showMessage(String msg);

    void setLoadingMore(boolean loadingMore);

    void setRefreshing(boolean refreshing);
}
