package com.gzlygl.view.fragment;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.liangmutian.airrecyclerview.swipetoloadlayout.OnLoadMoreListener;
import com.example.liangmutian.airrecyclerview.swipetoloadlayout.OnRefreshListener;
import com.example.liangmutian.airrecyclerview.swipetoloadlayout.SuperRefreshRecyclerView;
import com.gzlygl.R;
import com.gzlygl.adapter.PlanBaseRecyclerViewApdapter;
import com.gzlygl.javabeen.Plan;
import com.gzlygl.presenter.PlanPresenter;
import com.gzlygl.view.activity.StorkeActivity;
import com.gzlygl.view.view.PowerButton;
import com.gzlygl.view.viewinterface.IPlanView;

import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlanFragment extends Fragment implements IPlanView,View.OnClickListener{
    private FloatingActionButton addButton;
    private SuperRefreshRecyclerView planList;
    private PlanPresenter planPresenter;
    private ProgressDialog progressDialog;
    private PlanBaseRecyclerViewApdapter apdapter;
    public PlanFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_plan, container, false);
        iniView(view);
        planPresenter=new PlanPresenter(getContext(),this);
        planPresenter.getPlanData();
        return view;
    }

    private void iniView(View view){
        addButton=(FloatingActionButton)view.findViewById(R.id.add_btn);
        planList=(SuperRefreshRecyclerView)view.findViewById(R.id.plan_list);
        addButton.setOnClickListener(this);
        progressDialog=new ProgressDialog(getContext());
        progressDialog.setTitle("提示");
        progressDialog.setMessage("保存中...");
    }

    @Override
    public void setPlanListRefreshing(boolean enable) {
        planList.setRefreshing(enable);
    }

    @Override
    public void refreshingList(final List<Plan> data) {
        apdapter=new PlanBaseRecyclerViewApdapter(getContext(),R.layout.plan_item1,data);
        apdapter.setOnDeleteListener(new PlanBaseRecyclerViewApdapter.OnDeleteListener() {
            @Override
            public void onDelete(final Plan plan, int position) {
                AlertDialog dialog=new AlertDialog.Builder(getContext()).setTitle("提示")
                        .setMessage("确定删除计划："+plan.getPlan_name()+"？")
                        .setNeutralButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                planPresenter.deleteData(plan);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create();
                dialog.show();
            }
        });

        apdapter.setOnCheckListener(new PlanBaseRecyclerViewApdapter.OnCheckListener() {
            @Override
            public void onCheck(Plan plan, int position) {
                Intent intent=new Intent(getContext(), StorkeActivity.class);
                intent.putExtra("plan_id",plan.getObjectId());
                startActivity(intent);
            }
        });

        planList.setAdapter(apdapter);
        planList.init(new GridLayoutManager(getContext(), 1),
                new OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        planPresenter.getPlanData();
                    }
                },
                new OnLoadMoreListener() {
                    @Override
                    public void onLoadMore() {
                        planPresenter.loadMoreDate(apdapter);

                    }
        });
        planList.showData();

    }

    @Override
    public void showEditPlanDialog() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View view= LayoutInflater.from(getContext()).inflate(R.layout.dialog_edit_plan,null);
            final AlertDialog edDialog=new AlertDialog.Builder(getContext()).setView(view).create();
            edDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            PowerButton sureBtn=(PowerButton)view.findViewById(R.id.sure_btn);
            PowerButton cancelBtn=(PowerButton)view.findViewById(R.id.cancel_btn);
            final TextInputLayout layout=(TextInputLayout)view.findViewById(R.id.layout_plan);
            final TextInputEditText edit=(TextInputEditText)view.findViewById(R.id.edit_plan);
            sureBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (edit.getText().toString().equals("")){
                        layout.setErrorEnabled(true);
                        layout.setError("计划名称不能为空");
                    }else {
                        edDialog.dismiss();
                        planPresenter.savePlan(edit.getText().toString());
                    }
                }
            });
            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    edDialog.dismiss();
                }
            });
            edDialog.setCancelable(false);
            edDialog.setCanceledOnTouchOutside(false);
            edDialog.show();
        }
    }

    @Override
    public void setShowProgressDialogEnable(boolean enable) {
        if (enable){
            progressDialog.show();
        }else {
            progressDialog.dismiss();
        }
    }

    @Override
    public void showToastMsg(String msg) {
        Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setPlanListLoadMore(boolean enable) {
        planList.setLoadingMore(enable);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_btn:
                showEditPlanDialog();
                break;
        }
    }
}
