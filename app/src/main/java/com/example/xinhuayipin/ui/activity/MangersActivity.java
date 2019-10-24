package com.example.xinhuayipin.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.commons.BaseActivity;
import com.example.xinhuayipin.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MangersActivity extends BaseActivity {

    @BindView(R.id.manager_shelves)
    RelativeLayout managerShelves;
    @BindView(R.id.manager_the_shelves)
    LinearLayout managerTheShelves;
    @BindView(R.id.manager_open)
    LinearLayout managerOpen;
    @BindView(R.id.manager_log)
    LinearLayout managerLog;
    @BindView(R.id.manager_set)
    LinearLayout managerSet;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_manager;
    }

    @Override
    protected void initData() {
        super.initData();


    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.manager_shelves, R.id.manager_the_shelves, R.id.manager_open, R.id.manager_log, R.id.manager_set})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.manager_shelves:
                Intent intent = new Intent(this,ScansActivity.class);
                this.startActivity(intent);
                break;
            case R.id.manager_the_shelves:
                break;
            case R.id.manager_open:
                break;
            case R.id.manager_log:
                break;
            case R.id.manager_set:
                break;
        }
    }
}
