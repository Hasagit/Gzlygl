package com.gzlygl.view.activity;

import android.app.ActionBar;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toolbar;

import com.gzlygl.R;
import com.gzlygl.javabeen.TravelPlan;
import com.gzlygl.view.fragment.StorkeFragment;

import java.util.ArrayList;

public class StorkeActivity extends AppCompatActivity {
    private android.support.v7.widget.Toolbar toolbar;
    private FrameLayout content;
    private StorkeFragment storkeFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storke);
        initView();
    }

    private void initView(){
        toolbar=(android.support.v7.widget.Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("详情");
        android.support.v4.app.FragmentManager FM = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction =FM.beginTransaction();
        storkeFragment=new StorkeFragment();
        transaction.replace(R.id.content,storkeFragment);
        transaction.commit();
        storkeFragment.setPlan_id(getIntent().getStringExtra("plan_id"));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        storkeFragment.refreshStorkeList(new ArrayList<TravelPlan>());
        super.onDestroy();
    }
}
