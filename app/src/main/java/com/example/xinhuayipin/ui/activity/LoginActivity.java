package com.example.xinhuayipin.ui.activity;


import android.view.View;

import com.example.commons.BaseActivity;
import com.example.commons.annotations.ViewInject;
import com.example.commons.bus.LiveDataBus;
import com.example.commons.utils.MediaPlayerUtil;
import com.example.commons.utils.RxTimerUtil;
import com.example.xinhuayipin.R;
import com.example.xinhuayipin.data.UserDataBean;
import com.example.xinhuayipin.databinding.ActivityLoginBinding;
import com.example.xinhuayipin.mvp.contract.LoginContract;
import com.example.xinhuayipin.mvp.presenter.LoginPresenter;

@ViewInject(getLayoutId = R.layout.activity_login)
public class LoginActivity extends BaseActivity<LoginPresenter, ActivityLoginBinding> implements LoginContract.LoginView {

    private MediaPlayerUtil mediaPlayer;
    @Override
    protected void initPresent() {
        super.initPresent();
        mPresenter = new LoginPresenter();
    }

    @Override
    protected void initView() {
        super.initView();
        mediaPlayer = new MediaPlayerUtil(mContext);
    }

    @Override
    protected void initListener() {
        super.initListener();
        LiveDataBus.get().with("verify", UserDataBean.class).observe(this, userDataBean -> {
            mDataBinding.loginButton.setVisibility(View.GONE);
            mDataBinding.loginSuccess.setVisibility(View.VISIBLE);
            mDataBinding.loginTick.setChecked(true);
            mDataBinding.loginUser.setText(getString(R.string.login_welcome)+userDataBean.getStudentId());
            mediaPlayer.play(getString(R.string.prompt));
            RxTimerUtil.interval(1000,()-> mPresenter.countdownFinish());
        });
        mDataBinding.loginFingerprint.setOnClickListener(v -> skipAnotherActivity(VerifyActivity.class));
        mDataBinding.loginAccount.setOnClickListener(v -> skipAnotherActivity(ToLoginActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.destroy();
        RxTimerUtil.cancel();
    }

    @Override
    public void countdown(int time) {
        if (time>0){
            mDataBinding.loginUser.setText(getString(R.string.tip_1)+time+getString(R.string.tip_2));
        }else {
            mPresenter.closeHandler();
            finish();
            LiveDataBus.get().with("update").setValue("");
        }
    }
}
