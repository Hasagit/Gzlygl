package com.gzlygl.model;

import com.gzlygl.javabeen.User;
import com.gzlygl.presenter.RegisterPresenter;

import java.io.File;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by DengJf on 2017/11/14.
 */

public class RegisterModel implements IRegisterModel {
    RegisterPresenter presenter;

    public RegisterModel(RegisterPresenter presenter) {
        this.presenter = presenter;
    }

    //注册
    @Override
    public void register(final String id, final String name, final String pwd, final String sex, String file_name) {
        //上传头像
        final BmobFile user_img=new BmobFile(new File(file_name));
        user_img.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e==null){
                    //头像上传成功
                    String img_url=user_img.getUrl();
                    final String file_name=user_img.getFilename();
                    String file_group=user_img.getGroup();
                    //保存注册用户对象
                    User user=new User(id,name,pwd,sex,img_url,file_name,file_group);
                    user.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e==null){
                                //提示注册成功
                                presenter.dismissDialog();
                                presenter.setRegisterEnable(true);
                                presenter.setMsg("注册成功");
                                new File(file_name).delete();
                            }else {
                                //注册失败
                                presenter.setMsg(e.getMessage());
                                presenter.dismissDialog();
                                //显示注册失败原因
                                presenter.setRegisterEnable(true);
                            }
                        }
                    });
                }else {
                    presenter.dismissDialog();
                    presenter.setRegisterEnable(true);
                    presenter.setMsg(e.getMessage());
                }
            }

        });
    }
}
