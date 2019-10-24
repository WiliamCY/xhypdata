package com.example.xinhuayipin.ui.activity;

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

/**
 * 扫条形码
 */
@ViewInject(getLayoutId = R.layout.activity_scan)
public class ScanActivity extends BaseActivity<ScanPresenter, ActivityScanBinding> implements ScanContract.ScanView {

    private PromptDialog dialog;

    @Override
    protected void initPresent() {
        super.initPresent();
        mPresenter = new ScanPresenter();
    }

    @Override
    protected void initView() {
        super.initView();
//        mDataBinding.scanSweep.setImageResource(R.drawable.bar_code);
        Glide.with(mContext).load(R.drawable.bar_code).diskCacheStrategy(DiskCacheStrategy.ALL).into(mDataBinding.scanSweep);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.getBookByIsbn(mToken,"978-7-810-82143-8", mMachId, "");
    }

    @Override
    protected void initListener() {
        super.initListener();
        mDataBinding.scanFinish.setOnClickListener(v -> finish());
    }


    @Override
    public void bookOutOfCase() {
        RemindDialog dialog = new RemindDialog(mContext);
        dialog.setMsg(getString(R.string.bookOutOfCase),getString(R.string.i_know), 0);
        dialog.show();
    }

    @Override
    public void getBookId(ScanBean.Data data) {
        Bundle bundle = new Bundle();
        bundle.putString("book_id", data.getBook_id());
        bundle.putString("book_name", data.getTitle());
        bundle.putLong("borrow_time", data.getLend_time());
        bundle.putLong("need_return_back", data.getNeed_return_time());
        bundle.putString("username", data.getReader_name());
        bundle.putString("isbn", data.getMarc_id());
        skipAnotherActivity(bundle, ScanSuccessActivity.class);
        finish();
    }

    @Override
    public void scanBookError(String msg) {
        FailureDialog dialog = new FailureDialog(mContext, msg);
        dialog.setCallBack(() -> finish());
        dialog.show();
    }

    @Override
    public void netWorkError() {
        dialog = new PromptDialog(mContext, new PromptDialog.listener() {
            @Override
            public void confirm() {
                mPresenter.getBookByIsbn(mToken,"978-7-810-82143-8", mMachId, "");
            }

            @Override
            public void cancel() {
                dialog.dismiss();
            }
        });
        dialog.setDialogMsg(getString(R.string.network_error), getString(R.string.try_again), getString(R.string.button_cancel));
        dialog.show();
    }
}
