package com.gzlygl.view.activity;

import android.app.ProgressDialog;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.gzlygl.R;
import com.gzlygl.adapter.PhotopAdapter;
import com.gzlygl.presenter.EditPresenter;
import com.gzlygl.view.viewinterface.IEditView;

import java.util.ArrayList;
import java.util.List;

public class EditActivity extends BaseActivity implements View.OnClickListener,IEditView{
    private RecyclerView photoList;
    private EditText diaryEdit;
    private ImageView sendBtn;
    private Toolbar toolbar;
    private PhotopAdapter adapter;
    private List<String>data;
    private EditPresenter persenter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        initView();
        persenter=new EditPresenter(this);
    }


    private void initView(){
        photoList=(RecyclerView)findViewById(R.id.photo);
        diaryEdit=(EditText)findViewById(R.id.diary);
        toolbar=(Toolbar)findViewById(R.id.toolBar);
        sendBtn=(ImageView)findViewById(R.id.send_btn);

        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.re);

        data=new ArrayList<>();
        data.add("");
        adapter=new PhotopAdapter(this,data);
        photoList.setAdapter(adapter);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL);
//设置布局管理器
        photoList.setLayoutManager(layoutManager);

        sendBtn.setOnClickListener(this);

        progressDialog=new ProgressDialog(this);
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.send_btn:
                sendBtn.startAnimation(anim);
                persenter.sendDiary(adapter.getData(),diaryEdit.getText().toString());
                break;
        }
    }

    @Override
    public void showMsg(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showDialog() {
        progressDialog.show();
    }

    @Override
    public void dismissDialog() {
        progressDialog.dismiss();
    }
}
