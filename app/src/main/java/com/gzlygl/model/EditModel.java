package com.gzlygl.model;

import com.gzlygl.javabeen.Diary;
import com.gzlygl.javabeen.Img;
import com.gzlygl.presenter.EditPresenter;

import java.io.File;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by DengJf on 2017/11/20.
 */

public class EditModel implements IEditeModel{
    private EditPresenter persenter;

    public EditModel(EditPresenter persenter) {
        this.persenter = persenter;
    }

    @Override
    public void uploadDiary(final String diary, final BmobDate date, final String user_id, final List<String> filePaths) {
        final Diary diary_upload=new Diary(diary,user_id,filePaths.size()+"");
        diary_upload.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e!=null){
                    uploadDiary(diary,date,user_id,filePaths);
                }else {
                    for (int i=0;i<filePaths.size();i++){
                        uploadFile(filePaths.get(i),s,i,filePaths.size());
                    }
                }
            }
        });
    }

    @Override
    public void uploadFile(final String filePath, final String diary_id, final int order, final int size) {
        final BmobFile file=new BmobFile(new File(filePath));
        file.upload(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e!=null){
                    uploadFile(filePath,diary_id,order,size);
                }else {
                   uploadImg(diary_id,file.getGroup(),file.getUrl(),file.getFilename(),order,size);
                }
            }
        });
    }

    @Override
    public void uploadImg(final String diary_id, final String img_group, final String img_url, final String img_name, final int order, final int size) {
        Img img=new Img(diary_id,img_group,img_url,img_name,order);
        img.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e!=null){
                    uploadImg(diary_id,img_group,img_url,img_name,order,size);
                }else {
                    if (order==size-1);
                    persenter.finish();
                }
            }
        });
    }
}
