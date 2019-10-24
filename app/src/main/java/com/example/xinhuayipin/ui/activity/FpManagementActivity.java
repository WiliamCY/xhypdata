package com.example.xinhuayipin.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;

import com.example.commons.BaseActivity;
import com.example.commons.annotations.ViewInject;
import com.example.commons.bus.LiveDataBus;
import com.example.commons.view.refresh.CustomRefreshView;
import com.example.xinhuayipin.R;
import com.example.xinhuayipin.data.FingerprintBean;
import com.example.xinhuayipin.databinding.ActivityFpManagementBinding;
import com.example.xinhuayipin.mvp.contract.ManagementContract;
import com.example.xinhuayipin.mvp.presenter.ManagementPresenter;
import com.example.xinhuayipin.ui.adapter.FingerprintAdapter;

import java.util.List;

@ViewInject(getLayoutId = R.layout.activity_fp_management)
public class FpManagementActivity extends BaseActivity<ManagementPresenter, ActivityFpManagementBinding> implements ManagementContract.ManagementView {

    private FingerprintAdapter mAdapter;
    private int mFingerprintNumber;
    private String mStudentId;
    private Bundle bundle;

    @Override
    protected void initPresent() {
        super.initPresent();
        mPresenter = new ManagementPresenter();
    }

    @Override
    protected void initialize() {
        super.initialize();
        bundle = getIntent().getExtras();
        if (bundle!=null) mStudentId = String.valueOf(bundle.getInt("studentId"));
    }

    @Override
    protected void initView() {
        super.initView();
        mDataBinding.fpRefresh.getSwipeRefreshLayout().setColorSchemeColors(getResources().getColor(R.color.login));
        mDataBinding.fpRefresh.getRecyclerView().setLayoutManager(new LinearLayoutManager(mContext));
        mDataBinding.fpRefresh.setLoadMoreEnable(false);
        mAdapter = new FingerprintAdapter(mContext);
        mAdapter.setFingerprintBeanList(null);
        mDataBinding.fpRefresh.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        if (!TextUtils.isEmpty(mStudentId)){
            mPresenter.getFingerprint(mStudentId);
        }else {
            noFingerprintData();
        }
        LiveDataBus.get().with("update", String.class).observe(this, s -> mPresenter.getFingerprint(mStudentId));
    }

    @Override
    protected void initListener() {
        super.initListener();
        mDataBinding.fpRefresh.setOnLoadListener(new CustomRefreshView.OnLoadListener() {
            @Override
            public void onRefresh() {
                mPresenter.getFingerprint(mStudentId);
            }

            @Override
            public void onLoadMore() {

            }
        });

        mDataBinding.fpAdd.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(mStudentId)) {
                if (mFingerprintNumber > 1000) {
                    showToast(getString(R.string.finger_add_error));
                } else {
                    skipAnotherActivity(bundle, FingerprintActivity.class);
                }
            }else {
                showToast(getString(R.string.no_user));
                skipAnotherActivity(LoginActivity.class);
                finish();
            }
        });
    }

    @Override
    public void setFingerprintList(List<FingerprintBean> fingerprintList) {
        mAdapter.setFingerprintBeanList(fingerprintList);
        mAdapter.notifyDataSetChanged();
        mDataBinding.fpRefresh.complete();
        mFingerprintNumber = fingerprintList.size();
    }

    @Override
    public void noFingerprintData() {
        mAdapter.setFingerprintBeanList(null);
        mAdapter.notifyDataSetChanged();
        mDataBinding.fpRefresh.complete();
        mFingerprintNumber = 0;
        mDataBinding.fpRefresh.setEmptyView(getString(R.string.no_finger), 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
