package com.gzlygl.view.viewinterface;

/**
 * Created by DengJf on 2017/12/11.
 */

public interface IStorkeEditView {
    void setDateErrorEnable(boolean enable);

    void setDateError(String error);

    void setPlaceErrorEnable(boolean enable);

    void setPlaceError(String error);

    void showMessage(String msg);

    void setProgressDialogShowing(boolean showing);
}
