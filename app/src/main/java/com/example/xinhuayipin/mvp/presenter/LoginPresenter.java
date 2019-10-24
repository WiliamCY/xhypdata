package com.example.xinhuayipin.mvp.presenter;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

import com.example.commons.mvp.BasePresenterImpl;
import com.example.xinhuayipin.mvp.contract.LoginContract;

/**
 * @Author skygge.
 * @Date on 2019-08-22.
 * @Github https://github.com/javofxu
 * @Dec:
 * @version: ${VERSION}.
 * @Update :
 */
public class LoginPresenter extends BasePresenterImpl<LoginContract.LoginView> implements LoginContract.LoginPresent {

    private int COUNTDOWN = 0X12;
    private int mCountdown = 3;

    @Override
    public void countdownFinish() {
        mHandler.sendEmptyMessage(COUNTDOWN);
    }

    @Override
    public void closeHandler() {
        mHandler.removeMessages(COUNTDOWN);
        mHandler = null;
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mView.countdown(mCountdown);
            mCountdown--;
        }
    };
}
