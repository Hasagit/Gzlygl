package com.gzlygl.view.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.gzlygl.R;
import com.gzlygl.presenter.RegisterPresenter;
import com.gzlygl.util.PreferencesUtil;
import com.gzlygl.view.viewinterface.IRegisterView;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends BaseActivity implements IRegisterView,View.OnClickListener{
    private int TAKE_PHOTO=0;
    private int SELECT_PHOTO=1;
    private int CROP_PHOTO=2;
    private String register_user_img_path;
    private TextInputLayout id_layout,name_layout,pwd_layout,pwd_again_layout;
    private TextInputEditText id_ed,name_ed,pwd_ed,pwd_again_ed;
    private CircleImageView user_img;
    private CardView register_btn;
    private RadioButton man_radio,wuman_radio;
    private Toolbar toolbar;
    private PreferencesUtil preferencesUtil;
    private RegisterPresenter presenter;
    private ProgressDialog progressDialog;
    private Bitmap bm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        preferencesUtil=new PreferencesUtil(this);
        //这个变量跟后面裁剪的头像有关
        register_user_img_path=preferencesUtil.getString("system_path")+"/";
        initView();
        presenter=new RegisterPresenter(this);
    }

    private void initView(){
        toolbar=(Toolbar)findViewById(R.id.toolBar);
        id_ed=(TextInputEditText)findViewById(R.id.id_ed);
        id_layout=(TextInputLayout)findViewById(R.id.id_layout);
        name_ed=(TextInputEditText)findViewById(R.id.name_ed);
        name_layout=(TextInputLayout)findViewById(R.id.name_layout);
        pwd_ed=(TextInputEditText)findViewById(R.id.pwd_ed);
        pwd_layout=(TextInputLayout)findViewById(R.id.pwd_layout);
        pwd_again_ed=(TextInputEditText)findViewById(R.id.pwd_again_ed);
        pwd_again_layout=(TextInputLayout)findViewById(R.id.pwd_again_layout);
        user_img=(CircleImageView)findViewById(R.id.user_img);
        register_btn=(CardView)findViewById(R.id.register_btn);
        man_radio=(RadioButton)findViewById(R.id.man);
        wuman_radio=(RadioButton)findViewById(R.id.wuman);

        //为账号输入框设置输入监听（下同）
        id_ed.addTextChangedListener(new TextWatcher() {
            //输入前
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //账号不显示错误
                id_layout.setErrorEnabled(false);
            }
            //输入时
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //账号不显示错误
                id_layout.setErrorEnabled(false);
            }
            //输入后
            @Override
            public void afterTextChanged(Editable s) {
                //账号不显示错误
                id_layout.setErrorEnabled(false);
            }
        });

        name_ed.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                name_layout.setErrorEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                name_layout.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {
                name_layout.setErrorEnabled(false);
            }
        });

        pwd_again_ed.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                pwd_again_layout.setErrorEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                pwd_again_layout.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {
                pwd_again_layout.setErrorEnabled(false);
            }
        });

        pwd_ed.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                pwd_layout.setErrorEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                pwd_layout.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {
                pwd_layout.setErrorEnabled(false);
            }
        });

        //设置toolbar为标题栏
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        //设置标题栏左侧按钮可见（箭头按钮），点击事件在onOptionsItemSelected有重写
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");
        actionBar.setHomeAsUpIndicator(R.drawable.re);
        //为头像按钮设置点击事件
        user_img.setOnClickListener(this);
        //为注册按钮设置点击事件
        register_btn.setOnClickListener(this);

        //初始化等待对话框
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("注册中...");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //头像按钮点击事件
            case R.id.user_img:
                user_img.startAnimation(anim);
                //显示相册还是拍照对话框
                showImgDialog();
                break;
            //注册点击事件
            case R.id.register_btn:
                register_btn.startAnimation(anim);
                String sex;
                //根据性别单选按钮获取用户性别
                if (man_radio.isChecked()){
                    sex="男";
                }else {
                    sex="女";
                }
                //注册
                presenter.register(bm,register_user_img_path+"register_user_img_"+id_ed.getText().toString()+".jpg"
                        ,id_ed.getText().toString()
                        ,name_ed.getText().toString()
                        ,sex
                        ,pwd_ed.getText().toString()
                        ,pwd_again_ed.getText().toString());
                break;
        }
    }

    //初始化相册或拍照对话框
    @Override
    public void showImgDialog() {
        String[] items=new String[]{"拍照","从相册选择"};
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    //拍照
                    case 0:
                        Intent takeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        takeIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(register_user_img_path+"register_user_img_"+id_ed.getText().toString()+".jpg")));
                        startActivityForResult(takeIntent,TAKE_PHOTO);
                        break;

                    //从相册选取
                    case 1:
                        Intent intent = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, SELECT_PHOTO);
                        break;
                }
            }
        });
        builder.show();
    }

    //裁剪后会调用此方法
    @Override
    public void cropPhoto(String img_path) {
        //构建隐式Intent来启动裁剪程序
        Intent intent = new Intent("com.android.camera.action.CROP");
        //设置数据uri和类型为图片类型
        intent.setDataAndType(Uri.fromFile(new File(img_path)), "image/*");
        //显示View为可裁剪的
        intent.putExtra("crop", true);
        //裁剪的宽高的比例为1:1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        //输出图片的宽高均为150
        intent.putExtra("outputX", 500);
        intent.putExtra("outputY", 500);
        //裁剪之后的数据是通过Intent返回
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_PHOTO);

    }

    //显示账号错误信息
    @Override
    public void setIdError(String error) {
        id_layout.setErrorEnabled(true);
        id_layout.setError(error);
    }

    //显示用户名错误信息
    @Override
    public void setNameError(String error) {
        name_layout.setErrorEnabled(true);
        name_layout.setError(error);
    }

    //显示密码错误信息
    @Override
    public void setPwdError(String error) {
        pwd_layout.setErrorEnabled(true);
        pwd_layout.setError(error);
    }

    //显示再次输入密码错误信息
    @Override
    public void setPwdAgaiError(String error) {
        pwd_again_layout.setErrorEnabled(true);
        pwd_again_layout.setError(error);
    }


    @Override
    public void setMsg(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    //显示等待对话框
    @Override
    public void showDialog() {
        progressDialog.show();
    }

    //隐藏等待对话框
    @Override
    public void dismissDialog() {
        progressDialog.dismiss();
    }

    //注册按钮是否可点击
    @Override
    public void setRegisterBtnEnable(boolean enable) {
        register_btn.setEnabled(enable);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //获取拍照的图片
        if (requestCode==TAKE_PHOTO){
            cropPhoto(register_user_img_path+"register_user_img_"+id_ed.getText().toString()+".jpg");
        }


        //获取选取的图片路径
        if (requestCode == SELECT_PHOTO && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String imagePath = c.getString(columnIndex);
            cropPhoto(imagePath);
            c.close();
        }


        //裁剪后的图片
        if (requestCode==CROP_PHOTO){
            if (data == null){
                return;
            }else{
                Bundle extras = data.getExtras();
                if (extras != null){
                    //获取到裁剪后的图像
                    bm = extras.getParcelable("data");
                    user_img.setImageBitmap(bm);
                }
            }
        }
    }

    //导航栏左侧按钮事件监听
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
