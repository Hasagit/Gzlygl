package com.gzlygl.view.viewinterface;

/**
 * Created by DengJf on 2017/11/11.
 */

public interface IRegisterView {
    void showImgDialog();

    void cropPhoto(String img_path);

    void setIdError(String error);

    void setNameError(String error);

    void setPwdError(String error);

    void setPwdAgaiError(String error);

    void setMsg(String msg);

    void showDialog();

    void dismissDialog();

    void setRegisterBtnEnable(boolean enable);
}
