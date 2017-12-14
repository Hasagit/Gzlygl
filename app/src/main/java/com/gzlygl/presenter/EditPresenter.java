package com.gzlygl.presenter;

import com.gzlygl.javabeen.Diary;
import com.gzlygl.javabeen.Img;
import com.gzlygl.model.EditModel;
import com.gzlygl.util.PreferencesUtil;
import com.gzlygl.view.activity.EditActivity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by DengJf on 2017/11/20.
 */

public class EditPresenter {
    private EditActivity view;
    private EditModel model;
    private PreferencesUtil preferencesUtil;

    public EditPresenter(EditActivity view) {
        this.view = view;
        model=new EditModel(this);
        preferencesUtil=new PreferencesUtil(view);
    }

    public void sendDiary(final List<String>filePaths, String diary){
        if (filePaths.size()>0){
            filePaths.remove(0);
        }
        view.showDialog();
        Date curDate=new Date(System.currentTimeMillis());//获取当前时间
        BmobDate bmobDate=new BmobDate(curDate);
        model.uploadDiary(diary,bmobDate,preferencesUtil.getString("user_id"),filePaths);

    }


    public void finish(){
        view.setResult(1);
        view.finish();
        view.dismissDialog();
    }
}
