package com.gzlygl.view.activity;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.gzlygl.R;

public class BaseActivity extends AppCompatActivity {
    protected Animation anim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        anim= AnimationUtils.loadAnimation(this, R.anim.alpha);
    }


}
