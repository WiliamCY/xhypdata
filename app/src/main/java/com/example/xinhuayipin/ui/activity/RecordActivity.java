package com.example.xinhuayipin.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.example.commons.BaseActivity;
import com.example.commons.annotations.ViewInject;
import com.example.xinhuayipin.R;
import com.example.xinhuayipin.app.MyApplication;
import com.example.xinhuayipin.databinding.ActivityRecordBinding;
import com.example.xinhuayipin.mvp.contract.RecordContract;
import com.example.xinhuayipin.mvp.presenter.RecordPresenter;
import com.example.xinhuayipin.mvp.presenter.SuccessPresenter;
import com.example.xinhuayipin.ui.adapter.PagerAdapter;
import com.example.xinhuayipin.ui.dialog.RemindDialog;
import com.example.xinhuayipin.ui.fragment.GiveBackFragment;
import com.example.xinhuayipin.ui.fragment.WaitFetchFragment;
import com.example.xinhuayipin.ui.fragment.WaitRepayFragment;

import java.util.ArrayList;
import java.util.List;


@ViewInject(getLayoutId = R.layout.activity_record)
public class RecordActivity extends BaseActivity<RecordPresenter, ActivityRecordBinding> implements RecordContract.RecordView {

    private List<String> mTitle;
    private List<Fragment> fragmentList;
    private PagerAdapter mAdapter;

    private Fragment waitFetchFragment;
    private Fragment waitRepayFragment;
    private Fragment giveBackFragment;

    private int mStudentId;
    private Bundle bundle;

    @Override
    protected void initPresent() {
        super.initPresent();
        mPresenter = new RecordPresenter();
    }

    @Override
    protected void initialize() {
        super.initialize();
        bundle = getIntent().getExtras();
        if (bundle!=null) mStudentId = bundle.getInt("studentId");
    }

    @Override
    protected void initView() {
        super.initView();
        mTitle = new ArrayList<>();
        fragmentList = new ArrayList<>();
        mTitle.add(getString(R.string.not_take_book));
        mTitle.add(getString(R.string.wait_return));
        mTitle.add(getString(R.string.already_return));
        waitFetchFragment = new WaitFetchFragment();
        waitRepayFragment = new WaitRepayFragment();
        giveBackFragment = new GiveBackFragment();

        waitFetchFragment.setArguments(bundle);
        waitRepayFragment.setArguments(bundle);
        giveBackFragment.setArguments(bundle);

        for (String title: mTitle) {
            mDataBinding.recordTab.addTab(mDataBinding.recordTab.newTab().setText(title));
        }
        fragmentList.add(waitFetchFragment);
        fragmentList.add(waitRepayFragment);
        fragmentList.add(giveBackFragment);
        mAdapter = new PagerAdapter(mTitle, fragmentList, getSupportFragmentManager());
        mDataBinding.recordViewPager.setAdapter(mAdapter);
        mDataBinding.recordTab.setupWithViewPager(mDataBinding.recordViewPager);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.checkStudentByStudentID(mToken, mMachId, mStudentId);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mDataBinding.recordGo.setOnClickListener(v -> {
            skipAnotherActivity(BorrowActivity.class);
            finish();
        });
    }

    @Override
    public void hasDidNotReturn() {
        RemindDialog dialog = new RemindDialog(mContext);
        dialog.setMsg(getString(R.string.hasDidNotReturn),getString(R.string.go_return), 0);
        dialog.show();
        mDataBinding.recordViewPager.setCurrentItem(1);
    }

    @Override
    public void hasReserve() {
        RemindDialog dialog = new RemindDialog(mContext);
        dialog.setMsg(getString(R.string.hasDidReserve),getString(R.string.go_fetch), 0);
        dialog.show();
        mDataBinding.recordViewPager.setCurrentItem(0);
    }

    @Override
    public void noReserve() {
        mDataBinding.recordViewPager.setCurrentItem(2);
    }
}
