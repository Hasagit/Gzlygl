package com.gzlygl.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.liangmutian.airrecyclerview.swipetoloadlayout.OnLoadMoreListener;
import com.example.liangmutian.airrecyclerview.swipetoloadlayout.OnRefreshListener;
import com.example.liangmutian.airrecyclerview.swipetoloadlayout.SuperRefreshRecyclerView;
import com.gzlygl.R;
import com.gzlygl.adapter.DiaryBaseRecyclerViewAdapter;
import com.gzlygl.javabeen.Diary;
import com.gzlygl.presenter.TravelPresenter;
import com.gzlygl.util.PreferencesUtil;
import com.gzlygl.view.activity.EditActivity;
import com.gzlygl.view.activity.MainActivity;
import com.gzlygl.view.viewinterface.ITravelView;

import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class TravelFragment extends Fragment implements ITravelView{
    private FloatingActionButton actionButton;
    private ImageView coverImage;
    private Animation anim;
    private PreferencesUtil preferencesUtil;
    private DiaryBaseRecyclerViewAdapter adapter;
    private SuperRefreshRecyclerView diaryListView;
    private NestedScrollView scrollView;
    private SwipeRefreshLayout swipeRefreshView;
    private TravelPresenter persenter;



    public TravelFragment() {

    }



    public static TravelFragment newInstance(String param1, String param2) {
        TravelFragment fragment = new TravelFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        anim= AnimationUtils.loadAnimation(getContext(),R.anim.alpha);
        preferencesUtil=new PreferencesUtil(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_travel, container, false);
        initView(view);
        persenter=new TravelPresenter(this);
        return view;
    }

    private void initView(View view){
        diaryListView=(SuperRefreshRecyclerView) view.findViewById(R.id.diary_list);
        actionButton=(FloatingActionButton)view.findViewById(R.id.ed_btn);
        coverImage=(ImageView)view.findViewById(R.id.cover);
        swipeRefreshView=(SwipeRefreshLayout) view.findViewById(R.id.swipe);
        //swipeRefreshView.setEnabled(false);
        Glide.with(getContext())
                .load(preferencesUtil.getString("user_img_path"))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(coverImage);
        /*appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener()
        {
            //verticalOffset是当前appbarLayout的高度与最开始appbarlayout高度的差，向上滑动的话是负数。
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset)
            {
                if (toolbar.getHeight() - appBarLayout.getHeight() == verticalOffset) {
                    activity.setHomeResources(preferencesUtil.getString("user_img_path"));
                    activity.homeBeginAnim();
                    home_img=1;
                }else {
                    if (home_img==1){
                        home_img=0;
                        activity.setHomeResources(R.drawable.home);
                        activity.homeBeginAnim();
                    }
                }
            }
        });*/

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionButton.startAnimation(anim);
                Intent intent=new Intent(getContext(), EditActivity.class);
                startActivityForResult(intent,0);
            }
        });


       swipeRefreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshView.setRefreshing(false);
                persenter.getThreeDiary(new FindListener<Diary>() {
                    @Override
                    public void done(List<Diary> list, BmobException e) {
                        if (e==null){
                            refreshListView(list);
                        }else {
                            Toast.makeText(getContext(),"网络开小差了~",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });



    }


    @Override
    public void refreshListView(final List<Diary> data) {
        adapter=new DiaryBaseRecyclerViewAdapter(getActivity(),R.layout.diary_item,data);
        GridLayoutManager layoutManager=new GridLayoutManager(getContext(),1,LinearLayoutManager.VERTICAL,false);
        diaryListView.init(new GridLayoutManager(getContext(),1,LinearLayoutManager.VERTICAL,false),
                new OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        persenter.getThreeDiary(new FindListener<Diary>() {
                            @Override
                            public void done(List<Diary> list, BmobException e) {
                                if (e==null){
                                    refreshListView(list);
                                }else {
                                    Toast.makeText(getContext(),"网络开小差了~",Toast.LENGTH_SHORT).show();
                                }
                                diaryListView.setRefreshing(false);
                            }
                        });
                    }
                },
                new OnLoadMoreListener() {
                    @Override
                    public void onLoadMore() {
                        final int size=adapter.getItemCount()-1;
                        Diary diary=adapter.getData().get(size);
                        persenter.getMoreThreeDiary(diary.getCreatedAt(), new FindListener<Diary>() {
                            @Override
                            public void done(List<Diary> list, BmobException e) {
                                if (e==null){
                                    if (list.size()>0){
                                        adapter.addData(list);
                                        adapter.notifyDataSetChanged();
                                        //diaryListView.moveToPosition(size-1);
                                    }else {
                                        Toast.makeText(getContext(),"没有更多数据了",Toast.LENGTH_SHORT).show();
                                    }
                                }else {
                                    Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                                }
                                diaryListView.setLoadingMore(false);
                            }
                        });
                    }
                });
        diaryListView.setRefreshEnabled(false);
        diaryListView.setLoadingMoreEnable(true);
        diaryListView.setAdapter(adapter);
        diaryListView.showData();

    }

    @Override
    public void showMsg(String msg) {
        Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode){
            case 1:
                persenter.getThreeDiary(new FindListener<Diary>() {
                    @Override
                    public void done(List<Diary> list, BmobException e) {
                        if (e==null&&(list.size()>0)){
                            refreshListView(list);
                        }
                    }
                });
                break;
        }
    }


}
