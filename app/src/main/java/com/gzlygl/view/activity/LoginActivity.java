package com.gzlygl.view.activity;

import android.app.ProgressDialog;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gzlygl.R;
import com.gzlygl.presenter.LoginPresenter;
import com.gzlygl.view.viewinterface.ILoginView;

import de.hdodenhof.circleimageview.CircleImageView;


public class LoginActivity extends BaseActivity implements View.OnClickListener,ILoginView {
    private TextInputLayout user_id_layout,user_pwd_layout;
    private TextInputEditText user_id_ed,user_pwd_ed;
    private CardView login_card;
    private TextView register_text,forget_text;
    private CircleImageView user_img;
    private Toolbar toolbar;
    private LoginPresenter presenter;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        presenter=new LoginPresenter(this);
    }


    private void initView(){
        user_id_ed=(TextInputEditText)findViewById(R.id.user_id_ed);
        user_id_layout=(TextInputLayout)findViewById(R.id.user_id_layout);
        user_pwd_ed=(TextInputEditText)findViewById(R.id.user_pwd_ed);
        user_pwd_layout=(TextInputLayout)findViewById(R.id.user_pwd_layout);
        login_card=(CardView)findViewById(R.id.login_btn);
        register_text=(TextView)findViewById(R.id.register);
        forget_text=(TextView)findViewById(R.id.forget);
        user_img=(CircleImageView) findViewById(R.id.user_img);
        toolbar=(Toolbar)findViewById(R.id.toolBar);
        //设置toolbar为ActionBar
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        //设置导航栏Home键为可见（即左边那个箭头），在下面的onOptionsItemSelected方法有重写它的点击事件
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.re);
        //设置事件监听，在下面的OnClick方法有写点击事件监听
        //登录按钮事件监听
        login_card.setOnClickListener(this);
        //注册按钮事件监听
        register_text.setOnClickListener(this);
        //忘记密码事件监听
        forget_text.setOnClickListener(this);
        //为密码输入框设置事件监听
        user_pwd_ed.addTextChangedListener(new TextWatcher() {
            //输入字体前
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //不显示密码出错
                user_pwd_layout.setErrorEnabled(false);
            }

            //正在输入字体
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //不显示密码出错
                user_pwd_layout.setErrorEnabled(false);
            }

            //输入完字体之后
            @Override
            public void afterTextChanged(Editable s) {
                //不显密码号出错
                user_pwd_layout.setErrorEnabled(false);
            }
        });

        //为账号输入框设置事件监听
        user_id_ed.addTextChangedListener(new TextWatcher() {
            //账号输入前
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //不显示账号出错
                user_id_layout.setErrorEnabled(false);
            }
            //账号输入时
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //不显示账号出错
                user_id_layout.setErrorEnabled(false);
            }
            //账号输入后
            @Override
            public void afterTextChanged(Editable s) {
                //不显示账号出错
                user_id_layout.setErrorEnabled(false);
            }
        });

        progressDialog=new ProgressDialog(this);
    }





    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //注册事件
            case R.id.register:
                //按钮动画
                register_text.startAnimation(anim);
                //进入注册界面
                presenter.goToRegister();
                break;
            //忘记密码事件
            case R.id.forget:
                //按钮动画
                forget_text.startAnimation(anim);
                //进入忘记密码事件
                presenter.goToForget();
                break;
            case R.id.login_btn:
                //按钮动画
                login_card.startAnimation(anim);
                //进入主界面
                presenter.login(user_id_ed.getText().toString().trim()
                        , user_pwd_ed.getText().toString().trim());
                break;
        }
    }

    @Override
    public void setIdInputLayoutError(String error) {
        user_id_layout.setError(error);
        user_id_layout.setErrorEnabled(true);
    }

    @Override
    public void setPwdInputLayoutError(String error) {
        user_pwd_layout.setError(error);
        user_pwd_layout.setErrorEnabled(true);
    }

    @Override
    public void showProgressDialog(String msg) {
        progressDialog.show();
    }

    @Override
    public void dismissProgreassDialog() {
        progressDialog.dismiss();
    }

    @Override
    public void showError(String error) {
        Toast.makeText(this,error,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showUserHead(String filePath) {
        Glide.with(this)
                .load(filePath)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(user_img);
    }

    @Override
    public void setDefaultIdPwd(String userId, String userPwd) {
        user_id_ed.setText(userId);
        user_pwd_ed.setText(userPwd);
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
