package com.gzlygl.presenter;

import android.os.Environment;

import com.gzlygl.R;
import com.gzlygl.javabeen.User;
import com.gzlygl.model.UserModel;
import com.gzlygl.util.PreferencesUtil;
import com.gzlygl.view.activity.FirstActivity;

import java.io.File;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;

/**
 * Created by DengJf on 2017/11/9.
 */

public class FirstPresenter {
    private FirstActivity firstView;
    private UserModel userModel;
    private PreferencesUtil preferencesUtil;


    public FirstPresenter(final FirstActivity firstView) {
        this.firstView = firstView;
        //初始化数据
        initData();

        //根据上一次登录记录的账号和密码再次登录
        comparedMsg();
    }


    private void initData(){
        //初始化Bmod
        BmobConfig config =new BmobConfig.Builder(firstView.getInstance())
                //设置Bmod的app密匙
                .setApplicationId(firstView.getInstance().getResources().getString(R.string.app_id))
                //设置链接超时
                .setConnectTimeout(5000)
                //设置文件下载片段大小
                .setFileExpiration(128)
                .build();
        Bmob.initialize(config);

        //负责网络请求的类
        userModel=new UserModel();
        //负责数据缓存的类
        preferencesUtil=new PreferencesUtil(firstView);
        //系统默认在SD卡上面的缓存位置
        String filePath = Environment.getExternalStorageDirectory().getPath()+"/Gzlygl";
        File file=new File(filePath);
        if (!file.exists()){
            file.mkdir();
        }
        //存储系统文件夹路径，方便后面使用
        preferencesUtil.setString("system_path",filePath);
        //存储系统图片存储路径，方便后面使用
        preferencesUtil.setString("system_img_path",filePath+"/img");
        //如果文件夹不存在则新建文件夹
        File file_img=new File(filePath+"/img");
        if (!file_img.exists()){
            file_img.mkdir();
        }
    }


    private void comparedMsg(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                //先等待四秒
                try {
                    Thread.currentThread().sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //如果系统没有过登录记录则跳转去登录界面
                if (preferencesUtil.getString("user_id").equals("")){
                    firstView.goToLogin();
                }else {
                    //如果有登录记录则进行网络校验
                    userModel.getUserId(preferencesUtil.getString("user_id"), new SQLQueryListener<User>() {
                        @Override
                        public void done(BmobQueryResult<User> bmobQueryResult, BmobException e) {
                            //e是错误信息，如果e为空则说明网络没有问题，服务器返回数据
                            if (e==null){
                                //从服务器返回的结果中取出对象集
                                List<User>users=bmobQueryResult.getResults();
                                //如果集合大于0则说明账号已经注册
                                if (users.size()>0){
                                    //如果密码也一样这登录成功
                                    if (users.get(0).getUser_pwd().equals(preferencesUtil.getString("user_pwd"))){
                                        //进入主界面
                                        firstView.gotToMain();
                                    }else {
                                        //反之进入登录界面
                                        firstView.goToLogin();
                                    }
                                }else {
                                    //反之进入登录界面
                                    firstView.goToLogin();
                                }
                            }else {
                                //反之进入登录界面
                                firstView.goToLogin();
                            }
                        }
                    });
                }
            }
        }).start();
    }

}
