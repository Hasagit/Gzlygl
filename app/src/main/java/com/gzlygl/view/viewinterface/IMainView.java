package com.gzlygl.view.viewinterface;

/**
 * Created by DengJf on 2017/11/15.
 */

public interface IMainView {
    void showHead(String filePath);

    void setHelloText(String helloText);

    void setHomeResources(String filePath);

    void setHomeResources(int drawable);

    void homeBeginAnim();
}
