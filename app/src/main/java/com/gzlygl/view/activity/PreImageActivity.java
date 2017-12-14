package com.gzlygl.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.gzlygl.R;
import com.gzlygl.adapter.ViewPagerAdapter;
import com.gzlygl.view.view.PreViewFragment;

import java.util.List;

public class PreImageActivity extends BaseActivity {
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_image);
        initView();
    }


    private void initView(){
        viewPager=(ViewPager)findViewById(R.id.viewPager);
        adapter=new ViewPagerAdapter(getSupportFragmentManager());
        Intent intent=getIntent();
        List<String>imgs=intent.getStringArrayListExtra("imgs");
        int position=intent.getIntExtra("position",0);
        for (int i=0;i<imgs.size();i++){
            adapter.addFragment(new PreViewFragment(imgs.get(i)),i+"");
        }
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(0);
        viewPager.setCurrentItem(position-1);
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
