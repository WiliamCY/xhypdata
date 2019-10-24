package com.example.operations.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.view.View;

import com.example.commons.BaseActivity;
import com.example.operations.R;
import com.example.operations.databinding.ActivityManagerBinding;
import com.example.operations.mvp.presenter.InformationPresenter;


public class ManagerActivity extends BaseActivity<InformationPresenter, ActivityManagerBinding> {

    private String username;

    @Override
    protected void initialize() {
        super.initialize();
        Bundle bundle = getIntent().getExtras();
//        username = bundle.getString("account_number");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_manager;
    }

    @Override
    protected void initData() {
        super.initData();
//        mDataBinding.mangerName.setText(username);


    }

    @Override
    protected void initListener() {
        super.initListener();
        mDataBinding.managerSet.setOnClickListener(v -> skipAnotherActivity(CaseSetActivity.class));
        mDataBinding.managerLogo.setOnClickListener(v -> finish());
        mDataBinding.managerShelves.setOnClickListener(v -> skipAnotherActivity(ScansActivity.class));




    }
}
