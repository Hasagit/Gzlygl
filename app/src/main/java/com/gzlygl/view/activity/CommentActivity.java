package com.gzlygl.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liangmutian.airrecyclerview.swipetoloadlayout.OnLoadMoreListener;
import com.example.liangmutian.airrecyclerview.swipetoloadlayout.OnRefreshListener;
import com.example.liangmutian.airrecyclerview.swipetoloadlayout.SuperRefreshRecyclerView;
import com.gzlygl.R;
import com.gzlygl.adapter.CommentBaseRecyclerViewAdapter;
import com.gzlygl.adapter.DiaryBaseRecyclerViewAdapter;
import com.gzlygl.javabeen.Comment;
import com.gzlygl.javabeen.Diary;
import com.gzlygl.presenter.CommentPresenter;
import com.gzlygl.util.PreferencesUtil;
import com.gzlygl.view.viewinterface.ICommentView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class CommentActivity extends AppCompatActivity implements View.OnClickListener,ICommentView {
    private SuperRefreshRecyclerView comment_recycler;
    private TextView diary_text;
    private String ObjectId,Diary_date,Diary_msg,Img_num,User_id;
    private FloatingActionButton comment_btn;
    private AlertDialog commentDialog;
    private ProgressDialog progressDialog;
    private Animation animation;
    private PreferencesUtil preferencesUtil;
    private CommentBaseRecyclerViewAdapter comment_adapter;
    private CommentPresenter persenter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Toolbar toolbar;
    private AppBarLayout appBarLayout;
    private int LOADINGMOREENABLE=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        initData();
        initView();
        persenter=new CommentPresenter(this,ObjectId);
    }

    private void initView(){
        diary_text=(TextView)findViewById(R.id.diary);
        comment_recycler=(SuperRefreshRecyclerView)findViewById(R.id.comment_recycler);
        comment_btn=(FloatingActionButton)findViewById(R.id.comment_btn);
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipe);
        toolbar=(Toolbar)findViewById(R.id.toolBar);
        appBarLayout=(AppBarLayout)findViewById(R.id.appbar);
        //swipeRefreshLayout.setEnabled(false);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("详情");
        diary_text.setText(Diary_msg);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener()
        {
            //verticalOffset是当前appbarLayout的高度与最开始appbarlayout高度的差，向上滑动的话是负数。
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset)
            {
                if (toolbar.getHeight() - appBarLayout.getHeight() == verticalOffset) {
                    if (LOADINGMOREENABLE==1){
                        comment_recycler.setLoadingMoreEnable(true);
                    }
                }else {
                    comment_recycler.setLoadingMoreEnable(false);
                }
            }
        });


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                persenter.getComment(new Date(System.currentTimeMillis()), ObjectId, new FindListener<Comment>() {
                    @Override
                    public void done(List<Comment> list, BmobException e) {
                        if (e==null){
                            if (list.size()>0){
                                refreshCommenList(list);
                            }else {
                                Toast.makeText(CommentActivity.this,"暂时还没有人评论",Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(CommentActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });


        comment_btn.setOnClickListener(this);
        initCommentDialog();
    }


    private void initData(){
        Intent intent=getIntent();
        ObjectId=intent.getStringExtra("ObjectId");
        Diary_date=intent.getStringExtra("Diary_date");
        Diary_msg=intent.getStringExtra("Diary_msg");
        Img_num=intent.getStringExtra("Img_num");
        User_id=intent.getStringExtra("User_id");
        animation= AnimationUtils.loadAnimation(this,R.anim.alpha);
        preferencesUtil=new PreferencesUtil(this);
    }


    private void initCommentDialog(){
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("发送中...");
        View view= LayoutInflater.from(this).inflate(R.layout.comment_layout,null);
        commentDialog= new AlertDialog.Builder(this).setView(view).create();
        commentDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        final CardView comentSend=(CardView)view.findViewById(R.id.send_comment_btn);
        final EditText commentEdit=(EditText)view.findViewById(R.id.comment_ed);
        comentSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comentSend.startAnimation(animation);
                String user_id=preferencesUtil.getString("user_id");
                String msg=commentEdit.getText().toString();
                if (msg.equals("")){
                    Toast.makeText(CommentActivity.this,"请先输入评论内容",Toast.LENGTH_SHORT).show();
                }else {
                    final Comment comment=new Comment(user_id,ObjectId,msg);
                    progressDialog.show();
                    comment.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e==null){
                                commentDialog.dismiss();
                                progressDialog.dismiss();
                                addCommentHeadData(comment);
                            }else {
                                Toast.makeText(CommentActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                                commentDialog.dismiss();
                                progressDialog.dismiss();
                            }
                        }
                    });
                }
            }
        });
    }


    private void addCommentFootData(Comment comment){
        List<Comment>comments=new ArrayList<>();
        comments.add(comment);
        comment_adapter.addFootData(comments);
    }

    private void addCommentHeadData(Comment comment){
        List<Comment>comments=new ArrayList<>();
        comments.add(comment);
        comment_adapter.addHeadData(comments);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.comment_btn:
                commentDialog.show();
                break;
        }
    }

    @Override
    public void refreshCommenList(List<Comment> data) {
        comment_adapter=new CommentBaseRecyclerViewAdapter(this,data,R.layout.comment_item);
        comment_recycler.init(new GridLayoutManager(this, 1),
                new OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        persenter.getComment(new Date(System.currentTimeMillis()), ObjectId, new FindListener<Comment>() {
                            @Override
                            public void done(List<Comment> list, BmobException e) {
                                if (e==null){
                                    if (list.size()==0){
                                        Toast.makeText(CommentActivity.this,"暂时还没有人评论",Toast.LENGTH_SHORT).show();
                                    }
                                    refreshCommenList(list);
                                }else {
                                    Toast.makeText(CommentActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                                }
                                comment_recycler.setRefreshing(false);
                            }
                        });
                    }
                },
                new OnLoadMoreListener() {
                    @Override
                    public void onLoadMore() {
                        String date_str;
                        if (comment_adapter.getItemCount()>0){
                            date_str=comment_adapter.getData().get(comment_adapter.getItemCount()-1).getCreatedAt();
                        }else {
                            Toast.makeText(CommentActivity.this,"没有更多数据了",Toast.LENGTH_SHORT).show();
                            comment_recycler.setLoadingMore(false);
                            return;
                        }
                        persenter.getMoreComment(date_str, ObjectId, new FindListener<Comment>() {
                            @Override
                            public void done(List<Comment> list, BmobException e) {
                                if (e==null){
                                    if (list.size()>0){
                                        comment_adapter.addFootData(list);
                                    }else {
                                        Toast.makeText(CommentActivity.this,"没有更多数据了",Toast.LENGTH_SHORT).show();
                                    }
                                }else {
                                    Toast.makeText(CommentActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                                }
                                comment_recycler.setLoadingMore(false);
                            }
                        });
                        comment_recycler.setLoadingMore(false);
                    }
                });
        comment_recycler.setRefreshEnabled(false);
        if (data.size()>9){
            LOADINGMOREENABLE=1;
        }else {
            LOADINGMOREENABLE=0;
        }
        comment_recycler.setAdapter(comment_adapter);
        comment_recycler.showData();
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
}
