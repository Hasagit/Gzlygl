package com.gzlygl.view.activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gzlygl.R;
import com.gzlygl.adapter.ViewPagerAdapter;
import com.gzlygl.presenter.MainPresenter;
import com.gzlygl.view.fragment.CollectionFragment;
import com.gzlygl.view.fragment.PersonFragment;
import com.gzlygl.view.fragment.StorkeFragment;
import com.gzlygl.view.fragment.TravelFragment;
import com.gzlygl.view.viewinterface.IMainView;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseActivity implements View.OnClickListener,IMainView {
    private DrawerLayout drawer;
    private ImageView user_img;
    private CircleImageView home_img;
    private TextView hello_user,travel_text,storke_text,collection_text,person_text;
    private Toolbar toolbar;
    private LinearLayout leftLayout,travel_left,storke_left,collection_left,person_left,exit_left,switch_left;
    private Button travel_btn,storke_btn,collection_btn,person_btn;
    private MainPresenter persenter;
    private FrameLayout travel_layout,storke_layout,collection_layout,person_layout;
    private Animation fragment_alpha;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        persenter=new MainPresenter(this);
    }

    private void initView(){
        //动画，用于点击按钮时的渐透明
        fragment_alpha= AnimationUtils.loadAnimation(this,R.anim.fragment_alpha);
        drawer=(DrawerLayout)findViewById(R.id.drawer);
        user_img=(ImageView)findViewById(R.id.user_img);
        hello_user=(TextView)findViewById(R.id.hello);
        toolbar=(Toolbar)findViewById(R.id.toolBar);
        home_img=(CircleImageView) findViewById(R.id.home);
        leftLayout=(LinearLayout)findViewById(R.id.leftLayout);
        travel_left=(LinearLayout)findViewById(R.id.travel_left);
        travel_btn=(Button)findViewById(R.id.travel_btn);
        travel_text=(TextView)findViewById(R.id.travel_text);
        travel_layout=(FrameLayout)findViewById(R.id.travel_layout);

        storke_left=(LinearLayout)findViewById(R.id.storke_left);
        storke_btn=(Button)findViewById(R.id.storke_btn);
        storke_text=(TextView)findViewById(R.id.storke_text);
        storke_layout=(FrameLayout)findViewById(R.id.storke_layout);

        collection_left=(LinearLayout)findViewById(R.id.collection_left);
        collection_btn=(Button)findViewById(R.id.collection_btn);
        collection_text=(TextView)findViewById(R.id.collection_text);
        collection_layout=(FrameLayout)findViewById(R.id.collection_layout);

        person_left=(LinearLayout)findViewById(R.id.person_left);
        person_btn=(Button)findViewById(R.id.person_btn);
        person_text=(TextView)findViewById(R.id.person_text);
        person_layout=(FrameLayout)findViewById(R.id.person_layout);

        exit_left=(LinearLayout)findViewById(R.id.exit_left);
        switch_left=(LinearLayout)findViewById(R.id.switch_left);
        initViewPager();



        setSupportActionBar(toolbar);


        home_img.setOnClickListener(this);

        person_btn.setOnClickListener(this);
        person_left.setOnClickListener(this);
        person_layout.setOnClickListener(this);

        collection_btn.setOnClickListener(this);
        collection_left.setOnClickListener(this);
        collection_layout.setOnClickListener(this);

        storke_btn.setOnClickListener(this);
        storke_left.setOnClickListener(this);
        storke_layout.setOnClickListener(this);

        travel_btn.setOnClickListener(this);
        travel_left.setOnClickListener(this);
        travel_layout.setOnClickListener(this);

        switch_left.setOnClickListener(this);
        exit_left.setOnClickListener(this);
    }


    //初始化滑动页面
    private void initViewPager(){
        viewPager=(ViewPager)findViewById(R.id.content);
        viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new TravelFragment(),"游记");
        viewPagerAdapter.addFragment(new StorkeFragment(),"行程");
        viewPagerAdapter.addFragment(new CollectionFragment(),"收藏");
        viewPagerAdapter.addFragment(new PersonFragment(),"我的");
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setOffscreenPageLimit(1);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            //当页面滑动时的事件监听
            @Override
            public void onPageSelected(int position) {
                switch (position){
                    //游记界面时
                    case 0:
                        travel_btn.startAnimation(anim);
                        travel_text.startAnimation(anim);
                        travel_btn.setBackgroundResource(R.drawable.travel_true);
                        travel_text.setTextColor(getResources().getColor(R.color.colorPrimary));
                        storke_btn.setBackgroundResource(R.drawable.stroke_false);
                        storke_text.setTextColor(getResources().getColor(R.color.color_false));
                        collection_btn.setBackgroundResource(R.drawable.collection_false);
                        collection_text.setTextColor(getResources().getColor(R.color.color_false));
                        person_btn.setBackgroundResource(R.drawable.person_false);
                        person_text.setTextColor(getResources().getColor(R.color.color_false));
                        setHomeResources(R.drawable.home);
                        break;
                    //行程界面时
                    case 1:
                        storke_btn.startAnimation(anim);
                        storke_text.startAnimation(anim);
                        travel_btn.setBackgroundResource(R.drawable.travel_false);
                        travel_text.setTextColor(getResources().getColor(R.color.color_false));
                        storke_btn.setBackgroundResource(R.drawable.stroke_true);
                        storke_text.setTextColor(getResources().getColor(R.color.colorPrimary));
                        collection_btn.setBackgroundResource(R.drawable.collection_false);
                        collection_text.setTextColor(getResources().getColor(R.color.color_false));
                        person_btn.setBackgroundResource(R.drawable.person_false);
                        person_text.setTextColor(getResources().getColor(R.color.color_false));
                        setHomeResources(R.drawable.home);
                        break;
                    //收藏界面时
                    case 2:
                        collection_btn.startAnimation(anim);
                        collection_text.startAnimation(anim);
                        travel_btn.setBackgroundResource(R.drawable.travel_false);
                        travel_text.setTextColor(getResources().getColor(R.color.color_false));
                        storke_btn.setBackgroundResource(R.drawable.stroke_false);
                        storke_text.setTextColor(getResources().getColor(R.color.color_false));
                        collection_btn.setBackgroundResource(R.drawable.collection_true);
                        collection_text.setTextColor(getResources().getColor(R.color.colorPrimary));
                        person_btn.setBackgroundResource(R.drawable.person_false);
                        person_text.setTextColor(getResources().getColor(R.color.color_false));
                        setHomeResources(R.drawable.home);
                        break;
                    //个人界面时
                    case 3:
                        person_btn.startAnimation(anim);
                        person_text.startAnimation(anim);
                        travel_btn.setBackgroundResource(R.drawable.travel_false);
                        travel_text.setTextColor(getResources().getColor(R.color.color_false));
                        storke_btn.setBackgroundResource(R.drawable.stroke_false);
                        storke_text.setTextColor(getResources().getColor(R.color.color_false));
                        collection_btn.setBackgroundResource(R.drawable.collection_false);
                        collection_text.setTextColor(getResources().getColor(R.color.color_false));
                        person_btn.setBackgroundResource(R.drawable.person_true);
                        person_text.setTextColor(getResources().getColor(R.color.colorPrimary));
                        viewPager.startAnimation(fragment_alpha);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    //各个按钮的事件监听
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //导航栏左侧按钮
            case R.id.home:
                home_img.startAnimation(anim);
                //打开侧滑菜单
                drawer.openDrawer(Gravity.LEFT);
                break;
            //游记按钮
            case R.id.travel_btn:
                travel_btn.startAnimation(anim);
                travel_text.startAnimation(anim);
                //跳转游记界面
                travelEven();
                break;
            //行程按钮
            case R.id.storke_btn:
                storke_btn.startAnimation(anim);
                storke_text.startAnimation(anim);
                //跳转行程界面
                storkeEven();
                break;
            //收藏按钮
            case R.id.collection_btn:
                collection_btn.startAnimation(anim);
                collection_text.startAnimation(anim);
                //跳转收藏界面
                collectionEven();
                break;
            //个人按钮
            case R.id.person_btn:
                person_btn.startAnimation(anim);
                person_text.startAnimation(anim);
                //跳转个人界面
                personEven();
                break;
            //游记按钮
            case R.id.travel_layout:
                travel_btn.startAnimation(anim);
                travel_text.startAnimation(anim);
                //跳转游记界面
                travelEven();
                break;
            //行程按钮
            case R.id.storke_layout:
                storke_btn.startAnimation(anim);
                storke_text.startAnimation(anim);
                //跳转行程界面
                storkeEven();
                break;
            //收藏按钮
            case R.id.collection_layout:
                collection_btn.startAnimation(anim);
                collection_text.startAnimation(anim);
                //跳转收藏界面
                collectionEven();
                break;
            //个人按钮
            case R.id.person_layout:
                person_btn.startAnimation(anim);
                person_text.startAnimation(anim);
                //跳转个人界面
                personEven();
                break;
            //侧滑游记按钮
            case R.id.travel_left:
                travel_left.startAnimation(anim);
                //跳转游记界面
                travelEven();
                //关闭侧滑菜单
                drawer.closeDrawer(Gravity.LEFT);
                break;
            case R.id.storke_left:
                storke_left.startAnimation(anim);
                storkeEven();
                drawer.closeDrawer(Gravity.LEFT);
                break;
            case R.id.collection_left:
                collection_left.startAnimation(anim);
                collectionEven();
                drawer.closeDrawer(Gravity.LEFT);
                break;
            case R.id.person_left:
                person_left.startAnimation(anim);
                personEven();
                drawer.closeDrawer(Gravity.LEFT);
                break;
            case R.id.switch_left:
                switch_left.startAnimation(anim);
                Intent intent_login=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent_login);
                finish();
                break;
            case R.id.exit_left:
                exit_left.startAnimation(anim);
                finish();
                break;
        }
    }

    public void travelEven(){
        travel_btn.setBackgroundResource(R.drawable.travel_true);
        travel_text.setTextColor(getResources().getColor(R.color.colorPrimary));
        storke_btn.setBackgroundResource(R.drawable.stroke_false);
        storke_text.setTextColor(getResources().getColor(R.color.color_false));
        collection_btn.setBackgroundResource(R.drawable.collection_false);
        collection_text.setTextColor(getResources().getColor(R.color.color_false));
        person_btn.setBackgroundResource(R.drawable.person_false);
        person_text.setTextColor(getResources().getColor(R.color.color_false));
        viewPager.setCurrentItem(0);
        viewPager.startAnimation(fragment_alpha);
        setHomeResources(R.drawable.home);
    }

    public void storkeEven(){
        travel_btn.setBackgroundResource(R.drawable.travel_false);
        travel_text.setTextColor(getResources().getColor(R.color.color_false));
        storke_btn.setBackgroundResource(R.drawable.stroke_true);
        storke_text.setTextColor(getResources().getColor(R.color.colorPrimary));
        collection_btn.setBackgroundResource(R.drawable.collection_false);
        collection_text.setTextColor(getResources().getColor(R.color.color_false));
        person_btn.setBackgroundResource(R.drawable.person_false);
        person_text.setTextColor(getResources().getColor(R.color.color_false));
        viewPager.setCurrentItem(1);
        viewPager.startAnimation(fragment_alpha);
        setHomeResources(R.drawable.home);
    }

    public void collectionEven(){
        travel_btn.setBackgroundResource(R.drawable.travel_false);
        travel_text.setTextColor(getResources().getColor(R.color.color_false));
        storke_btn.setBackgroundResource(R.drawable.stroke_false);
        storke_text.setTextColor(getResources().getColor(R.color.color_false));
        collection_btn.setBackgroundResource(R.drawable.collection_true);
        collection_text.setTextColor(getResources().getColor(R.color.colorPrimary));
        person_btn.setBackgroundResource(R.drawable.person_false);
        person_text.setTextColor(getResources().getColor(R.color.color_false));
        viewPager.setCurrentItem(2);
        viewPager.startAnimation(fragment_alpha);
        setHomeResources(R.drawable.home);
    }

    public void personEven(){
        travel_btn.setBackgroundResource(R.drawable.travel_false);
        travel_text.setTextColor(getResources().getColor(R.color.color_false));
        storke_btn.setBackgroundResource(R.drawable.stroke_false);
        storke_text.setTextColor(getResources().getColor(R.color.color_false));
        collection_btn.setBackgroundResource(R.drawable.collection_false);
        collection_text.setTextColor(getResources().getColor(R.color.color_false));
        person_btn.setBackgroundResource(R.drawable.person_true);
        person_text.setTextColor(getResources().getColor(R.color.colorPrimary));
        viewPager.setCurrentItem(3);
        setHomeResources(R.drawable.home);
        viewPager.startAnimation(fragment_alpha);
    }
    @Override
    public void showHead(String filePath) {
        Glide.with(this).load(filePath)
                .diskCacheStrategy(DiskCacheStrategy.NONE).
                skipMemoryCache(true)
                .into(user_img);
    }

    @Override
    public void setHelloText(String helloText) {
        hello_user.setText(helloText);
    }

    @Override
    public void setHomeResources(String filePath) {
        Glide.with(this).load(filePath)
                .diskCacheStrategy(DiskCacheStrategy.NONE).
                skipMemoryCache(true)
                .into(home_img);
    }

    @Override
    public void setHomeResources(int drawable) {
        Glide.with(this)
                .load(drawable)
                .diskCacheStrategy(DiskCacheStrategy.NONE).
                skipMemoryCache(true)
                .into(home_img);
    }

    @Override
    public void homeBeginAnim() {
        home_img.startAnimation(fragment_alpha);
    }
}
