package com.example.xinhuayipin.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;

import com.example.commons.BaseActivity;
import com.example.commons.annotations.ViewInject;
import com.example.commons.utils.MediaPlayerUtil;
import com.example.xinhuayipin.R;
import com.example.xinhuayipin.databinding.ActivitySuccessfulBinding;
import com.example.xinhuayipin.mvp.presenter.SuccessPresenter;

@ViewInject(getLayoutId = R.layout.activity_successful)
public class SuccessfulActivity extends BaseActivity<SuccessPresenter, ActivitySuccessfulBinding> {

    private String mName;
    private String mAddress;
    private String mBorrower;
    private String mTime;
    private String mIsbn;
    private MediaPlayerUtil mediaPlayer;

    @Override
    protected void initPresent() {
        super.initPresent();
        mPresenter = new SuccessPresenter();
    }

    @Override
    protected void initialize() {
        super.initialize();
        Intent intent = getIntent();
        mName = intent.getStringExtra("name");
        mAddress = intent.getStringExtra("address");
        mBorrower = intent.getStringExtra("borrower");
        mTime = intent.getStringExtra("time");
        mIsbn = intent.getStringExtra("isbn");
    }

    @Override
    protected void initView() {
        super.initView();
        mediaPlayer = new MediaPlayerUtil(mContext);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initData() {
        super.initData();
        mDataBinding.successName.setText(getString(R.string.book_left)+mName+getString(R.string.book_right));
        mDataBinding.successAddress.setText(mAddress);
        mDataBinding.successBorrower.setText(mBorrower);
        mDataBinding.successTime.setText(mTime);
        mDataBinding.successIsbn.setText(mIsbn);
        mDataBinding.successTick.setChecked(true);
        mediaPlayer.play(getString(R.string.prompt));
    }

    @Override
    protected void initListener() {
        super.initListener();
        mDataBinding.successCancel.setOnClickListener(v -> finish());
        mDataBinding.successGo.setOnClickListener(v -> skipAnotherActivity(FetchActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.destroy();
    }
}
