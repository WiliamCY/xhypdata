package com.example.xinhuayipin.ui.activity;

import com.example.commons.BaseActivity;
import com.example.commons.annotations.ViewInject;
import com.example.xinhuayipin.R;
import com.example.xinhuayipin.databinding.ActivityFetchBinding;
import com.example.xinhuayipin.mvp.presenter.FetchPresenter;

@ViewInject(getLayoutId = R.layout.activity_fetch)
public class FetchActivity extends BaseActivity<FetchPresenter, ActivityFetchBinding> {

    @Override
    protected void initPresent() {
        super.initPresent();
        mPresenter = new FetchPresenter();
    }

}
