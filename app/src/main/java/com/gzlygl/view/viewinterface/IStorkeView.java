package com.gzlygl.view.viewinterface;

import com.gzlygl.javabeen.TravelPlan;

import java.util.List;

/**
 * Created by DengJf on 2017/12/10.
 */

public interface IStorkeView {
    void refreshStorkeList(List<TravelPlan>data);

    void setRefreshing(boolean refreshing);

    void setLoadingMore(boolean loadingMore);

    void showMessage(String msg);
}
