package com.gzlygl.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.os.Bundle;
import android.view.View;

import com.gzlygl.R;
import com.gzlygl.presenter.FirstPresenter;
import com.gzlygl.view.viewinterface.IFirstView;

public class FirstActivity extends BaseActivity implements IFirstView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        //presenter是逻辑主要实现的地方
        FirstPresenter persenter=new FirstPresenter(this);
    }

    //返回上下文
    @Override
    public Context getInstance() {
        return this;
    }

    //进入主界面
    @Override
    public void gotToMain() {
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    //进入登录界面
    public void goToLogin(){
        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);
        finish();
    }


    //沉浸式实现方法
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        View decorView = getWindow().getDecorView();
        int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(option);
        getWindow().setNavigationBarColor(Color.TRANSPARENT);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
    }


}
