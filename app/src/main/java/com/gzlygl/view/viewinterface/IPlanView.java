package com.gzlygl.view.viewinterface;

import com.gzlygl.javabeen.Plan;

import java.util.List;
import java.util.Map;

/**
 * Created by DengJf on 2018/2/23.
 */

public interface IPlanView {

    void setPlanListRefreshing(boolean enable);

    void refreshingList(List<Plan>data);

    void showEditPlanDialog();

    void setShowProgressDialogEnable(boolean enable);

    void showToastMsg(String msg);

    void setPlanListLoadMore(boolean enable);
}
