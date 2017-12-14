package com.gzlygl.presenter;

import android.graphics.Bitmap;

import com.gzlygl.model.RegisterModel;
import com.gzlygl.view.activity.RegisterActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by DengJf on 2017/11/13.
 */

public class RegisterPresenter {
    private RegisterActivity registerView;
    private RegisterModel model;

    public RegisterPresenter(RegisterActivity registerView) {
        this.registerView = registerView;
        model=new RegisterModel(this);
    }

    //保存文件到SD卡
    public void saveBitmap(String filePath, Bitmap bitmap) {
        File f = new File(filePath);
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //注册
    public void register(Bitmap bm,final String imgFilePath, final String id, final String name, final String sex, final String pwd, String pwdAgain){
        //讲选好的图片保存到SD卡
        if (bm!=null){
            saveBitmap(imgFilePath,bm);
        }
        //如果文件存在，则说明裁剪成功
        if (!new File(imgFilePath).exists()){
            registerView.setMsg("请先设置头像");
            return;
        }

        if (id.equals("")){
            registerView.setIdError("id不能为空");
            return;
        }
        if (name.equals("")){
            registerView.setNameError("昵称不能为空");
            return;
        }
        if (pwd.equals("")){
            registerView.setPwdError("密码不能为空");
            return;
        }
        if (!pwdAgain.equals(pwd)){
            registerView.setPwdAgaiError("两次密码输入不一致");
            return;
        }
        registerView.showDialog();
        //设置注册按钮不可点，防止重复注册出错
        registerView.setRegisterBtnEnable(false);
        //调用model类，上传给服务器
        model.register(id,name,pwd,sex,imgFilePath);


    }

    //Toast显示信息
    public void setMsg(String msg){
        registerView.setMsg(msg);
    }

    //隐藏对话框
    public void dismissDialog(){
        registerView.dismissDialog();
    }

    //设置注册按钮是否可点
    public void setRegisterEnable(boolean enable){
        registerView.setRegisterBtnEnable(enable);
    }

}
