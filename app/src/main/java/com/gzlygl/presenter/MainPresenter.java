package com.gzlygl.presenter;

import android.util.Log;

import com.gzlygl.model.MainModel;
import com.gzlygl.util.PreferencesUtil;
import com.gzlygl.view.activity.MainActivity;

/**
 * Created by DengJf on 2017/11/15.
 */

public class MainPresenter {
    private MainModel model;
    private MainActivity view;
    private PreferencesUtil preferencesUtil;

    public MainPresenter(MainActivity view) {
        this.view=view;
        model=new MainModel();
        preferencesUtil=new PreferencesUtil(view);
        view.showHead(preferencesUtil.getString("user_img_path"));
        view.setHelloText("您好，"+preferencesUtil.getString("user_name"));
    }
}
