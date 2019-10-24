package com.example.operations.ui.activity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.commons.BaseActivity;
import com.example.operations.R;

import pl.droidsonroids.gif.GifImageView;

/**
 * 扫条形码
 */
public class ScansActivity extends BaseActivity{

    //private PromptDialog dialog;
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
//        mDataBinding.scanSweep.setImageResource(R.drawable.bar_code);
        Glide.with(mContext).load(R.drawable.barcodescanner).diskCacheStrategy(DiskCacheStrategy.ALL).into(gifImageView);
    }

    @Override
    protected void initData() {
        super.initData();
      //  mPresenter.getBookByIsbn(mToken,"978-7-810-82143-8", mMachId, "");
    }

    @Override
    protected void initListener() {
        super.initListener();
    //    mDataBinding.scanFinish.setOnClickListener(v -> finish());

    }



}
