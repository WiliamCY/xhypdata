package com.example.xinhuayipin.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.commons.BaseActivity;
import com.example.commons.annotations.ViewInject;
import com.example.commons.bus.LiveDataBus;
import com.example.xinhuayipin.R;
import com.example.xinhuayipin.app.MyApplication;
import com.example.xinhuayipin.data.ScanBean;
import com.example.xinhuayipin.data.UserDataBean;
import com.example.xinhuayipin.data.borrow.BorrowBean;
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
    private String   studentId;
    private int mStudentId;
    private Bundle bundle;
    private BroadcastReceiver mReceiver;
    private String isb;

    @Override
    protected void initPresent() {
        super.initPresent();
        mPresenter = new ScanPresenter();
    }

    @Override
    protected void initView() {
        super.initView();
        Glide.with(mContext).load(R.drawable.bar_code).diskCacheStrategy(DiskCacheStrategy.ALL).into(mDataBinding.scanSweep);
        mContext = getApplicationContext();
        mReceiver= new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                final String scanResult_1=intent.getStringExtra("SCAN_BARCODE1");
                final String scanResult_2=intent.getStringExtra("SCAN_BARCODE2");
                final int barcodeType = intent.getIntExtra("SCAN_BARCODE_TYPE", -1); // -1:unknown
                final String scanStatus=intent.getStringExtra("SCAN_STATE");
                if("ok".equals(scanStatus)){

                }else{

                }
            }
        };



    }

    @Override
    protected void initData() {
        super.initData();
        Intent intent = new Intent ("ACTION_BAR_SCANCFG");
        intent.putExtra("EXTRA_SCAN_MODE", 3);
        mContext.sendBroadcast(intent);
        mDataBinding.ibsEdit.setText("");



    }


    @Override
    protected void initListener() {
        super.initListener();
        mDataBinding.scanFinish.setOnClickListener(v -> finish());
        mDataBinding.ibsEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String c = mDataBinding.ibsEdit.getText().toString();
                Toast.makeText(getApplicationContext(), c, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });


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
                mPresenter.getBookByIsbn(mToken,"978-7-810-82143-8", mMachId, studentId);
            }

            @Override
            public void cancel() {
                dialog.dismiss();
            }
        });
        dialog.setDialogMsg(getString(R.string.network_error), getString(R.string.try_again), getString(R.string.button_cancel));
        dialog.show();
    }
    private void registerReceiver()
    {
        IntentFilter mFilter= new IntentFilter("nlscan.action.SCANNER_RESULT");
        registerReceiver(mReceiver, mFilter);
    }
    private void unRegisterReceiver()
    {
        try {
            unregisterReceiver(mReceiver);
        } catch (Exception e) {
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        unRegisterReceiver();
    }
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver();
    }

}
