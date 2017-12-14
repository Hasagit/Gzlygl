package com.gzlygl.model;

import java.util.Date;
import java.util.List;

import cn.bmob.v3.datatype.BmobDate;

/**
 * Created by DengJf on 2017/11/20.
 */

public interface IEditeModel {
    void uploadDiary(String diary, BmobDate date, String user_id, List<String> filePaths);

    void uploadFile(String filePath,String diary_id,int order,int size);

    void uploadImg(String diary_id, String img_group, String img_url, String img_name, int order,int size);
}
