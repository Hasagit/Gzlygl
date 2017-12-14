package com.gzlygl.view.viewinterface;

import com.gzlygl.javabeen.Diary;

import java.util.List;
import java.util.Map;

/**
 * Created by DengJf on 2017/11/21.
 */

public interface ITravelView {
    void refreshListView(List<Diary>data);

    void showMsg(String msg);
}
