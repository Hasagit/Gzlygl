package com.gzlygl.view.fragment;


import android.os.Bundle;
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
import com.gzlygl.adapter.DiaryBaseRecyclerViewAdapter;
import com.gzlygl.javabeen.Diary;
import com.gzlygl.presenter.CollectionPresenter;
import com.gzlygl.view.viewinterface.ICollectionView;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CollectionFragment extends Fragment implements ICollectionView{
    private SuperRefreshRecyclerView collection_recycler;
    private DiaryBaseRecyclerViewAdapter adapter;
    private CollectionPresenter persenter;
    public CollectionFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_collection, container, false);
        initView(view);
        persenter=new CollectionPresenter(this);
        return view;
    }

    private void initView(View view){
        collection_recycler=(SuperRefreshRecyclerView)view.findViewById(R.id.collection_list);
    }


    @Override
    public void refreshCollectioList(List<Diary> data) {
        adapter=new DiaryBaseRecyclerViewAdapter(getContext(),R.layout.diary_item,data);
        collection_recycler.init(new GridLayoutManager(getContext(), 1),
                new OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        persenter.getThreeCollectionDiary();
                    }
                },
                new OnLoadMoreListener() {
                    @Override
                    public void onLoadMore() {
                        persenter.getThreeMoreCollectionDiary();
                    }
                });
        collection_recycler.setRefreshEnabled(true);
        collection_recycler.setLoadingMoreEnable(true);
        collection_recycler.setAdapter(adapter);
        collection_recycler.showData();
    }

    @Override
    public void setLoadingMore(boolean loadingMore) {
        collection_recycler.setLoadingMore(loadingMore);
    }

    @Override
    public void setRefreshing(boolean refreshing) {
        collection_recycler.setRefreshing(refreshing);
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public DiaryBaseRecyclerViewAdapter getAdater() {
        return adapter;
    }
}
