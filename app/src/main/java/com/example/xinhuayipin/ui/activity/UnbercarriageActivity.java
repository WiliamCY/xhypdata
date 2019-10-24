package com.example.xinhuayipin.ui.activity;

import com.example.commons.BaseActivity;
import com.example.xinhuayipin.R;
import com.example.xinhuayipin.databinding.ActivityLoginBinding;
import com.example.xinhuayipin.mvp.contract.GroundingContract;
import com.example.xinhuayipin.mvp.contract.UndercarriageContract;
import com.example.xinhuayipin.mvp.presenter.GroundBindPresenter;
import com.example.xinhuayipin.mvp.presenter.UnbercarriagePresenter;

public class UnbercarriageActivity extends BaseActivity<UnbercarriagePresenter, ActivityLoginBinding> implements UndercarriageContract.ClearView {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_record;
    }

    @Override
    protected void initPresent() {
        super.initPresent();
        mPresenter = new UnbercarriagePresenter();

    }

    @Override
    protected void initData() {
        super.initData();
       // mPresenter.bindBook();
    }

    @Override
    public void loginSuccess(long studentId, long schoolId, String msg) {

    }

    @Override
    public void loginFailed(String msg) {

    }

    @Override
    public void networkError() {

    }
}
