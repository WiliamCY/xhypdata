package com.example.operations.ui.activity;

import android.os.Bundle;

import com.example.commons.BaseActivity;
import com.example.commons.utils.SpHelperUtil;
import com.example.operations.R;
import com.example.operations.databinding.ActivityManagerLoginBinding;
import com.example.operations.mvp.contract.ManagerLoginContract;
import com.example.operations.mvp.presenter.ManagerLoginPresenter;

public class ManagerLoginActivity extends BaseActivity<ManagerLoginPresenter, ActivityManagerLoginBinding> implements ManagerLoginContract.ManagerLoginView {

    private String username;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_manager_login;
    }

    @Override
    protected void initPresent() {
        super.initPresent();
        mPresenter = new ManagerLoginPresenter();
    }

    @Override
    protected void initView() {
        super.initView();
        mDataBinding.managerLoginPwd.setInputType(1);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mDataBinding.managerLogin.setOnClickListener(v -> {
            username = mDataBinding.managerLoginId.getInputContent();
            String password = mDataBinding.managerLoginPwd.getInputContent();
            mPresenter.login(mToken, username, password);
        });
    }

    @Override
    public void successful(String user_token) {
        showToast("登录成功!");
        Bundle bundle = new Bundle();
        bundle.putString("account_number", username);
        skipAnotherActivity(bundle, ManagerActivity.class);
        SpHelperUtil spHelperUtil = new SpHelperUtil(mContext);
        spHelperUtil.getSharedPreference("user_token", user_token);
        finish();
    }

    @Override
    public void failture(String msg) {
        showToast(msg);
        mDataBinding.managerLoginPwd.setInputContent("");
    }

    @Override
    public void netWorkError() {
        showToast(getString(R.string.network_error));
    }
}
