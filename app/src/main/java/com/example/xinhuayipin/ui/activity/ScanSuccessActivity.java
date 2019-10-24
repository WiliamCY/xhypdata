package com.example.xinhuayipin.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.example.commons.BaseActivity;
import com.example.commons.annotations.ViewInject;
import com.example.commons.utils.DataTimeUtil;
import com.example.commons.utils.MediaPlayerUtil;
import com.example.xinhuayipin.R;
import com.example.xinhuayipin.databinding.ActivityScanSuccessBinding;
import com.example.xinhuayipin.mvp.contract.SuccessContract;
import com.example.xinhuayipin.mvp.presenter.SuccessPresenter;
import com.example.xinhuayipin.ui.dialog.FailureDialog;
import com.example.xinhuayipin.ui.dialog.PromptDialog;

@ViewInject(getLayoutId = R.layout.activity_scan_success)
public class ScanSuccessActivity extends BaseActivity<SuccessPresenter, ActivityScanSuccessBinding> implements SuccessContract.SuccessView {

    private String mBookId;
    private String mBookName;
    private long mBorrowTime;
    private long mReturnTime;
    private String mUserName;
    private String mIsbn;
    private boolean isSuccess;
    private PromptDialog dialog;
    private MediaPlayerUtil mediaPlayer;

    @Override
    protected void initialize() {
        super.initialize();
        Bundle bundle = getIntent().getExtras();
        mBookId = bundle.getString("book_id");
        mBookName = bundle.getString("book_name");
        mBorrowTime = bundle.getLong("borrow_time");
        mReturnTime = bundle.getLong("need_return_back");
        mUserName = bundle.getString("username");
        mIsbn = bundle.getString("isbn");
    }

    @Override
    protected void initPresent() {
        super.initPresent();
        mPresenter = new SuccessPresenter();
    }

    @Override
    protected void initView() {
        super.initView();
        mediaPlayer = new MediaPlayerUtil(mContext);
    }

    @Override
    protected void initData() {
        super.initData();
        isSuccess = false;
        mDataBinding.scanSuccessName.setText(mBookName);
        mDataBinding.scanBorrowTime.setText(DataTimeUtil.formatDateTime(mBorrowTime));
        mDataBinding.scanNeedReturnTime.setText(DataTimeUtil.formatDateTime(mReturnTime));
        mDataBinding.scanSuccessBorrower.setText(mUserName);
        mDataBinding.scanSuccessIsbn.setText(mIsbn);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mDataBinding.scanSuccessCancel.setOnClickListener(v -> {
            if (isSuccess){
                finish();
            }else {
                mPresenter.returnBack(mToken, mMachId, mBookId);
            }
        });
        mDataBinding.scanSuccessGo.setOnClickListener(v -> skipAnotherActivity(BorrowActivity.class));
    }

    @Override
    public void successful() {
        isSuccess = true;
        mDataBinding.scanSuccessCancel.setText(getString(R.string.i_know));
        mDataBinding.scanSuccessTitle.setText(R.string.return_success);
        mDataBinding.scanSuccessMsg.setText(DataTimeUtil.getTime());
        mDataBinding.scanSuccessIcon.setVisibility(View.GONE);
        mDataBinding.scanSuccessTick.setVisibility(View.VISIBLE);
        mDataBinding.scanSuccessTick.setChecked(true);
        mediaPlayer.play(getString(R.string.prompt));
    }

    @Override
    public void failture(String msg) {
        FailureDialog dialog = new FailureDialog(mContext, msg);
        dialog.show();
    }

    @Override
    public void netWorkError() {
        dialog = new PromptDialog(mContext, new PromptDialog.listener() {
            @Override
            public void confirm() {
                mPresenter.returnBack(mToken, mMachId, mBookId);
            }

            @Override
            public void cancel() {
                dialog.dismiss();
            }
        });
        dialog.setDialogMsg(getString(R.string.network_error), getString(R.string.try_again), getString(R.string.button_cancel));
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.destroy();
    }
}
