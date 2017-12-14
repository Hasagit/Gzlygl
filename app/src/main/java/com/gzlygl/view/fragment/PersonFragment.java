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
import com.gzlygl.presenter.PersonPresenter;
import com.gzlygl.view.viewinterface.IPersonView;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonFragment extends Fragment implements IPersonView{
    private DiaryBaseRecyclerViewAdapter adapter;
    private SuperRefreshRecyclerView person_recycler;
    private PersonPresenter presenter;
    public PersonFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_person, container, false);
        initView(view);
        presenter=new PersonPresenter(this);
        return view;
    }

    private void initView(View view){
        person_recycler=(SuperRefreshRecyclerView)view.findViewById(R.id.person_list);

    }

    @Override
    public void refreshPersonList(List<Diary> data) {
        adapter=new DiaryBaseRecyclerViewAdapter(getContext(),R.layout.diary_item,data);
        person_recycler.init(
                new GridLayoutManager(getContext(), 1),
                new OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        presenter.getData();
                    }
                },
                new OnLoadMoreListener() {
                    @Override
                    public void onLoadMore() {
                        presenter.getMoreThreeData(adapter);
                    }
                });
        person_recycler.setRefreshEnabled(true);
        person_recycler.setLoadingMoreEnable(true);
        person_recycler.setAdapter(adapter);
        person_recycler.showData();
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setLoadingMore(boolean loadingMore) {
        person_recycler.setLoadingMore(loadingMore);
    }

    @Override
    public void setRefreshing(boolean refreshing) {
        person_recycler.setRefreshing(refreshing);
    }
}
