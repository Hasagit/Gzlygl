package com.gzlygl.view.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.liangmutian.airrecyclerview.swipetoloadlayout.OnLoadMoreListener;
import com.example.liangmutian.airrecyclerview.swipetoloadlayout.OnRefreshListener;
import com.example.liangmutian.airrecyclerview.swipetoloadlayout.SuperRefreshRecyclerView;
import com.gzlygl.R;
import com.gzlygl.adapter.StorkeBaseRecyclerViewAdapter;
import com.gzlygl.javabeen.TravelPlan;
import com.gzlygl.presenter.StorkePresenter;
import com.gzlygl.view.viewinterface.IStorkeView;

import java.util.List;


public class StorkeFragment extends Fragment implements IStorkeView{
    private SuperRefreshRecyclerView storke_list;
    private FloatingActionButton add_btn;
    private StorkePresenter presenter;
    private StorkeBaseRecyclerViewAdapter adapter;
    public StorkeFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_storke, container, false);
        initView(view);
        presenter=new StorkePresenter(this);
        return view;
    }

    private void initView(View view){
        storke_list=(SuperRefreshRecyclerView)view.findViewById(R.id.storke_list);
        add_btn=(FloatingActionButton)view.findViewById(R.id.add_btn);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.goToStorkeEditActivity();
            }
        });
    }

    @Override
    public void refreshStorkeList(List<TravelPlan> data) {
        adapter=new StorkeBaseRecyclerViewAdapter(getContext(),R.layout.plan_item,data);
        storke_list.init(
                new GridLayoutManager(getContext(), 1),
                new OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        presenter.refreshStorkeList();
                    }
                }, new OnLoadMoreListener() {
                    @Override
                    public void onLoadMore() {
                        presenter.loadMoreStorkeList(adapter);
                    }
                });
        storke_list.setRefreshEnabled(true);
        storke_list.setLoadingMoreEnable(true);
        storke_list.setAdapter(adapter);
        storke_list.showData();
    }

    @Override
    public void setRefreshing(boolean refreshing) {
        storke_list.setRefreshing(refreshing);
    }

    @Override
    public void setLoadingMore(boolean loadingMore) {
        storke_list.setLoadingMore(loadingMore);
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode){
            case 0:
                presenter.refreshStorkeList();
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
