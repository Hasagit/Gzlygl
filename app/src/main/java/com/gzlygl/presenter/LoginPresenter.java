package com.gzlygl.presenter;

import android.content.Intent;

import com.gzlygl.javabeen.User;
import com.gzlygl.model.LoginModel;
import com.gzlygl.util.PreferencesUtil;
import com.gzlygl.view.activity.ForgetActivity;
import com.gzlygl.view.activity.LoginActivity;
import com.gzlygl.view.activity.MainActivity;
import com.gzlygl.view.activity.RegisterActivity;

import java.util.List;

import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.SQLQueryListener;

/**
 * Created by DengJf on 2017/11/10.
 */

public class LoginPresenter {
    private LoginModel loginModel;
    private LoginActivity loginView;
    private PreferencesUtil preferencesUtil;

    public LoginPresenter(LoginActivity loginView) {
        this.loginView=loginView;
        loginModel=new LoginModel();
        preferencesUtil=new PreferencesUtil(loginView);

        //如果系统有过登录记录则自动填写账号密码
        if (!preferencesUtil.getString("user_id").equals("")){
            loginView.setDefaultIdPwd(preferencesUtil.getString("user_id")
                    ,preferencesUtil.getString("user_pwd"));
        }
        //如果系统有过登录记录，则自动加载头像
        if (!preferencesUtil.getString("user_img_path").equals("")){
            loginView.showUserHead(preferencesUtil.getString("user_img_path"));
        }
    }

    //进行网络校验
    public void login(final String user_id, final String user_pwd){
        if (user_id.equals("")){
            loginView.setIdInputLayoutError("账号不能为空");
            return;
        }
        if (user_pwd.equals("")){
            loginView.setPwdInputLayoutError("密码不能为空");
            return;
        }
        loginView.showProgressDialog("登录中...");
        //进行网络校验
        loginModel.getUserById(user_id, new SQLQueryListener<User>() {
            @Override
            public void done(BmobQueryResult<User> bmobQueryResult, BmobException e) {
                //loginView.dismissProgreassDialog();
                //如果e为空则服务器成功返回数据
                if (e==null){
                    //从返回数据中取出结果集
                    List<User>users=bmobQueryResult.getResults();
                    //如果结果集大于0,则说明有注册该账号
                    if (users.size()>0){
                        User user=users.get(0);
                        //如果密码匹配一致，校验通过
                        if (user.getUser_pwd().equals(user_pwd)){
                            //讲用户信息写入缓存中，方便以后调用
                            preferencesUtil.setString("user_id",user.getUser_id());
                            preferencesUtil.setString("user_name",user.getUser_name());
                            preferencesUtil.setString("user_pwd",user.getUser_pwd());
                            preferencesUtil.setString("user_sex",user.getUser_sex());
                            preferencesUtil.setString("user_img_url",user.getImg_url());
                            preferencesUtil.setString("user_img_name",user.getImg_name());
                            preferencesUtil.setString("user_img_group",user.getImg_group());
                            //头像文件路径
                            String filePath=preferencesUtil.getString("system_img_path")+"/user_head_"+user_id+".jpg";
                            //保存头像的存储路径
                            preferencesUtil.setString("user_img_path",filePath);
                            //下载头像
                            loginModel.dowloadUserImg(user.getImg_url(), user.getImg_name(), user.getImg_group(), filePath, new DownloadFileListener() {
                                @Override
                                public void done(String s, BmobException e) {
                                    //下载完成，等待对话框消失
                                    loginView.dismissProgreassDialog();
                                    //如果e为空，成功下载文件
                                    if (e==null){
                                        //进入主界面
                                        Intent intent=new Intent(loginView,MainActivity.class);
                                        loginView.startActivity(intent);
                                        loginView.finish();
                                    }else {
                                        //提示错误
                                        loginView.showError(e.getMessage());
                                        loginView.showError("头像下载出错");
                                    }
                                }

                                @Override
                                public void onProgress(Integer integer, long l) {

                                }
                            });
                        }else {
                            loginView.dismissProgreassDialog();
                            loginView.setPwdInputLayoutError("账号密码不匹配");
                        }
                    }else {
                        loginView.dismissProgreassDialog();
                        loginView.setIdInputLayoutError("该账号未注册");
                    }
                }else {
                    loginView.dismissProgreassDialog();
                    loginView.showError(e.getMessage());
                }
            }
        });
    }

    //进入注册界面
    public void goToRegister(){
        Intent intent=new Intent(loginView, RegisterActivity.class);
        loginView.startActivity(intent);
    }

    //进入忘记密码界面（没有实现）
    public void goToForget(){
        Intent intent=new Intent(loginView, ForgetActivity.class);
        loginView.startActivity(intent);
    }


}
