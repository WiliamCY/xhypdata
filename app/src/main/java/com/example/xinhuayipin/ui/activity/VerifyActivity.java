package com.example.xinhuayipin.ui.activity;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.commons.BaseActivity;
import com.example.commons.annotations.ViewInject;
import com.example.commons.bus.LiveDataBus;
import com.example.commons.utils.RxTimerUtil;
import com.example.xinhuayipin.R;
import com.example.xinhuayipin.app.MyApplication;
import com.example.xinhuayipin.data.UserDataBean;
import com.example.xinhuayipin.databinding.ActivityVerifyBinding;
import com.example.xinhuayipin.mvp.contract.VerifyContract;
import com.example.xinhuayipin.mvp.presenter.VerifyPresenter;
import com.example.xinhuayipin.ui.dialog.PromptDialog;

import fgtit.fpengine.fpdevice;

/**
 * 指纹验证界面
 */
@ViewInject(getLayoutId = R.layout.activity_verify)
public class VerifyActivity extends BaseActivity<VerifyPresenter, ActivityVerifyBinding> implements VerifyContract.VerifyView {

    private byte[][] fingerprint;
   private PromptDialog dialog;

    @Override
    protected void initPresent() {
        super.initPresent();
        mPresenter = new VerifyPresenter(mContext);
    }

    @Override
    protected void initView() {
        super.initView();
        showProgressDialog();
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.openFp();
    }

    @Override
    protected void initListener() {
        super.initListener();
        mDataBinding.verifyFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vs) {
                mPresenter.closeFP();
                dialog.dismiss();
                RxTimerUtil.cancel();
                finish();

            }
        });
        //mDataBinding.verifyFinish.setOnClickListener(v -> finish());
        dialog = new PromptDialog(mContext, new PromptDialog.listener() {
            @Override
            public void confirm() {
                skipAnotherActivity(LoginActivity.class);
                dialog.dismiss();
                finish();

            }

            @Override
            public void cancel() {
                dialog.dismiss();
                mPresenter.openFp();

            }
        });
        dialog.setDialogMsg(getString(R.string.compare_fail), getString(R.string.go_login), getString(R.string.revalidation));
    }

    @Override
    public void openSuccess() {
         dismissProgressDialog();
        Glide.with(mContext).load(R.drawable.verify).diskCacheStrategy(DiskCacheStrategy.ALL).into(mDataBinding.verifyScan);
        RxTimerUtil.interval(1000,()-> mPresenter.compareFinger(fingerprint));

    }


    @Override
    public void openFailed(int number) {
        if (number>3){
            dismissProgressDialog();
        }else {
            mPresenter.closeFP();

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        mPresenter.Resume();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(KeyEvent.KEYCODE_BACK == keyCode){
            mPresenter.keyDown();
            finish();
        }
        return  super.onKeyDown(keyCode, event);
    }

    @Override
    public void compareSuccess(int parse) {
        Log.i(TAG, "compareSuccess: "+parse);
        RxTimerUtil.cancel();
        mPresenter.closeFP();
        mPresenter.saveStudentIdAndGetFingerId(parse);
    }

    @Override
    public void compareFailed() {
//        showToast(getString(R.string.compare_fail_tip));
        dialog.show();
        RxTimerUtil.cancel();
        mPresenter.closeFP();
    }

    @Override
    public void getFingerIdAndStudentId(int fingerId, int studentId) {
        UserDataBean dataBean = new UserDataBean();
        dataBean.setFingerId(fingerId);
        dataBean.setStudentId(studentId);
        LiveDataBus.get().with("verify").setValue(dataBean);
        finish();
    }

}
