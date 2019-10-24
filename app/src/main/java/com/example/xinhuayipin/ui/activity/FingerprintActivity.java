package com.example.xinhuayipin.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.commons.BaseActivity;
import com.example.commons.annotations.ViewInject;
import com.example.commons.bus.LiveDataBus;
import com.example.commons.utils.ConversionUtil;
import com.example.commons.utils.MediaPlayerUtil;
import com.example.commons.utils.RxTimerUtil;
import com.example.xinhuayipin.R;
import com.example.xinhuayipin.app.MyApplication;
import com.example.xinhuayipin.databinding.ActivityFingerprintBinding;
import com.example.xinhuayipin.mvp.contract.FingerprintContract;
import com.example.xinhuayipin.mvp.presenter.FingerprintPresenter;

import java.io.File;


@ViewInject(getLayoutId = R.layout.activity_fingerprint)
public class FingerprintActivity extends BaseActivity<FingerprintPresenter, ActivityFingerprintBinding> implements FingerprintContract.FingerprintView {

    private MediaPlayerUtil mediaPlayer;
    private Bundle bundle;
    private String mStudentId;
    private  int fpSize = 2;

    @Override
    protected void initPresent() {
        super.initPresent();
        mPresenter = new FingerprintPresenter(mContext);
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
        mediaPlayer = new MediaPlayerUtil(mContext);
    }

    @Override
    protected void initData() {
        super.initData();
        showProgressDialog();
        mPresenter.openFp();
//        mDataBinding.fingerState.setText(getString(R.string.opening));
    }


    @Override
    public void openSuccess() {
        dismissProgressDialog();
        Glide.with(mContext).load(R.drawable.scanning).diskCacheStrategy(DiskCacheStrategy.ALL).into(mDataBinding.fingerScanning);
        mDataBinding.fingerState.setText(getString(R.string.scanning));
        RxTimerUtil.interval(1000,()-> mPresenter.initInputFinger());
    }

    @Override
    public void openFailed(int number) {
        if (number>3){
            dismissProgressDialog();
            mDataBinding.fingerState.setText(getString(R.string.open_fail));
        }else {
            Log.i(TAG, "openFailed: ");
            mPresenter.openFp();
        }
    }

    @Override
    public void getFingerSuccess(byte[] fingerData, File file) {
        for(int i = 0;i<=fpSize;i++){
            String fingerprint = ConversionUtil.toHexString(fingerData);
            String schoolId = String.valueOf(MyApplication.getInstance().getSchoolId());
            mPresenter.uploadFinger(mToken, fingerprint, file, mStudentId, schoolId);
            int c = fpSize-i;
            mDataBinding.fingerState.setText("再按"+c+"次");
        }

    }

    @Override
    public void registeredSuccess() {
        mDataBinding.fingerScanning.setVisibility(View.INVISIBLE);
        mDataBinding.fingerTick.setVisibility(View.VISIBLE);
        mDataBinding.fingerState.setText(getString(R.string.register_success));
        mDataBinding.fingerTick.setChecked(true);
        mediaPlayer.play(getString(R.string.prompt));
        mPresenter.closeFP();
        RxTimerUtil.cancel();
        RxTimerUtil.interval(1000,()-> mPresenter.countdownFinish());
        finish();

    }

    @Override
    public void registeredFailed() {
        mDataBinding.fingerScanning.setVisibility(View.VISIBLE);
        mDataBinding.fingerTick.setVisibility(View.GONE);
        mDataBinding.fingerState.setText(getString(R.string.register_failed));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void countdown(int time) {
        if (time > 0){
            mDataBinding.fingerTip.setText(getString(R.string.tip_1)+time+getString(R.string.tip_2));
        }else {
            LiveDataBus.get().with("update").setValue("");
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.destroy();
        mPresenter.closeFP();
        mPresenter.closeHandler();
        RxTimerUtil.cancel();
    }
}
