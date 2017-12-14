package com.gzlygl.view.viewinterface;

/**
 * Created by DengJf on 2017/11/10.
 */

public interface ILoginView {

    void setIdInputLayoutError(String error);

    void setPwdInputLayoutError(String error);

    void showProgressDialog(String msg);

    void dismissProgreassDialog();

    void showError(String error);

    void showUserHead(String filePath);

    void setDefaultIdPwd(String userId,String userPwd);
}
