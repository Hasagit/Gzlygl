package com.gzlygl.view.activity;

import android.app.ProgressDialog;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.gzlygl.R;
import com.gzlygl.presenter.StorkeEditPresenter;
import com.gzlygl.view.viewinterface.IStorkeEditView;

public class StorkeEditActivity extends BaseActivity implements IStorkeEditView{
    private TextInputLayout date_layout,place_layout;
    private TextInputEditText date_edit,place_edit;
    private EditText plan_edit;
    private android.support.v7.widget.Toolbar toolbar;
    private ImageView send_btn;
    private StorkeEditPresenter presenter;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storke_edit);
        initView();
        presenter=new StorkeEditPresenter(this);
    }

    private void initView(){
        date_layout=(TextInputLayout)findViewById(R.id.date_layout);
        date_edit=(TextInputEditText)findViewById(R.id.date_edit);
        place_layout=(TextInputLayout)findViewById(R.id.place_layout);
        place_edit=(TextInputEditText)findViewById(R.id.place_edit);
        plan_edit=(EditText)findViewById(R.id.plan_edit);
        send_btn=(ImageView)findViewById(R.id.send_btn);
        toolbar=(android.support.v7.widget.Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send_btn.startAnimation(anim);
                presenter.savePlan(
                        date_edit.getText().toString(),
                        place_edit.getText().toString(),
                        plan_edit.getText().toString());
            }
        });
        dialog=new ProgressDialog(this);
        dialog.setTitle("提交中...");
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                setResult(0);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setDateErrorEnable(boolean enable) {
        date_layout.setErrorEnabled(enable);
    }

    @Override
    public void setDateError(String error) {
        date_layout.setError(error);
    }

    @Override
    public void setPlaceErrorEnable(boolean enable) {
        place_layout.setErrorEnabled(enable);
    }

    @Override
    public void setPlaceError(String error) {
        place_layout.setError(error);
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(StorkeEditActivity.this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setProgressDialogShowing(boolean showing) {
        if (showing){
            dialog.show();
        }else {
            dialog.dismiss();
        }
    }
}
