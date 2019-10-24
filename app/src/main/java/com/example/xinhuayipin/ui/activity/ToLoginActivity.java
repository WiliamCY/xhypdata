package com.example.xinhuayipin.ui.activity;

import android.text.TextUtils;

import com.example.commons.BaseActivity;
import com.example.commons.annotations.ViewInject;
import com.example.commons.bus.LiveDataBus;
import com.example.xinhuayipin.R;
import com.example.xinhuayipin.app.MyApplication;
import com.example.xinhuayipin.data.UserDataBean;
import com.example.xinhuayipin.databinding.ActivityToLoginBinding;
import com.example.xinhuayipin.mvp.contract.ToLoginContract;
import com.example.xinhuayipin.mvp.presenter.ToLoginPresenter;
import com.example.xinhuayipin.ui.dialog.PromptDialog;

@ViewInject(getLayoutId = R.layout.activity_to_login)
public class ToLoginActivity extends BaseActivity<ToLoginPresenter, ActivityToLoginBinding> implements ToLoginContract.ToLoginView {

    private String username;
    private String password;
    private PromptDialog dialog;

    @Override
    protected void initPresent() {
        super.initPresent();
        mPresenter = new ToLoginPresenter();
    }

    @Override
    protected void initView() {
        super.initView();
        mDataBinding.toLoginPwd.setInputType(1);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mDataBinding.toLogin.setOnClickListener(v -> {
            showProgressDialog();
            username = mDataBinding.toLoginId.getInputContent();
            password = mDataBinding.toLoginPwd.getInputContent();
            if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)){
                mPresenter.login(mToken, "505019A", username, password);
                }else {
                    showToast(getString(R.string.input_error));
            }
        });
    }


    @Override
    public void loginSuccess(long studentId, long schoolId, String msg) {
        dismissProgressDialog();
        showToast(msg);
        MyApplication.getInstance().saveSchoolId((int)schoolId);
        UserDataBean userDataBean = new UserDataBean();
        userDataBean.setStudentId((int)studentId);
        userDataBean.setFingerId(0);
        LiveDataBus.get().with("verify").setValue(userDataBean);
        finish();
    }

    @Override
    public void loginFailed(String msg) {
        dismissProgressDialog();
        showToast(msg);
    }

    @Override
    public void networkError() {
        dismissProgressDialog();
        dialog = new PromptDialog(mContext, new PromptDialog.listener() {
            @Override
            public void confirm() {
                mPresenter.login(mToken, mMachId, username, password);
                dialog.dismiss();
            }

            @Override
            public void cancel() {
                dialog.dismiss();
            }
        });
        dialog.setDialogMsg(getString(R.string.compare_fail), getString(R.string.try_again), getString(R.string.button_cancel));
        dialog.show();
    }
}
