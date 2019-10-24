package com.example.xinhuayipin.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.commons.BaseActivity;
import com.example.commons.annotations.ViewInject;
import com.example.xinhuayipin.R;
import com.example.xinhuayipin.data.ScanBean;
import com.example.xinhuayipin.databinding.ActivityScanBinding;
import com.example.xinhuayipin.mvp.contract.ScanContract;
import com.example.xinhuayipin.mvp.presenter.ScanPresenter;
import com.example.xinhuayipin.ui.dialog.FailureDialog;
import com.example.xinhuayipin.ui.dialog.PromptDialog;
import com.example.xinhuayipin.ui.dialog.RemindDialog;

import pl.droidsonroids.gif.GifImageView;

/**
 * 扫条形码
 */
public class ScansActivity extends BaseActivity{

    private PromptDialog dialog;
    private GifImageView gifImageView;

    @Override
    protected void initPresent() {
        super.initPresent();
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_scans;
    }

    @Override
    protected void initView() {
        super.initView();
        gifImageView = findViewById(R.id.scan_sweep);
        Glide.with(mContext).load(R.drawable.barcodescanner).diskCacheStrategy(DiskCacheStrategy.ALL).into(gifImageView);
     Intent intent = new Intent(this,GroundingActivity.class);
     startActivity(intent);
     finish();
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void initListener() {
        super.initListener();
        findViewById(R.id.scan_finish).setOnClickListener(v -> finish());
    }



}
