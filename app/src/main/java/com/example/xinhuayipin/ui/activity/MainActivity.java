package com.example.xinhuayipin.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.example.commons.BaseActivity;
import com.example.commons.annotations.ViewInject;
import com.example.commons.bus.LiveDataBus;
import com.example.commons.utils.ResourcesUtil;
import com.example.operations.ui.activity.ManagerActivity;
import com.example.operations.ui.activity.ManagerLoginActivity;
import com.example.xinhuayipin.R;
import com.example.xinhuayipin.app.MyService;
import com.example.xinhuayipin.data.UserDataBean;
import com.example.xinhuayipin.databinding.ActivityMainBinding;
import com.example.xinhuayipin.mvp.contract.MainContract;
import com.example.xinhuayipin.mvp.presenter.MainPresenter;
import com.example.xinhuayipin.ui.dialog.ContactUsDialog;
import com.example.xinhuayipin.ui.dialog.OrderDialog;
import com.example.xinhuayipin.ui.dialog.RemindDialog;
import com.example.xinhuayipin.util.GlideImageLoader;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

@ViewInject(getLayoutId = R.layout.activity_main)
public class MainActivity extends BaseActivity<MainPresenter, ActivityMainBinding> implements MainContract.MainView, View.OnClickListener{

    private int mPagerNumber;
    private boolean actionToRecord = false;
    private boolean actionToFetch = false;
    private boolean actionToFinger = false;
    private boolean verify = false;
    private List<Integer> imageViews;

    @Override
    protected void initPresent() {
        super.initPresent();
        mPresenter = new MainPresenter(mContext);
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected void initData() {
        super.initData();
        mPagerNumber = 3;
//        mPresenter.getBannerImg(mPagerNumber, mDataBinding.mainInstructions);
        showPagerImgs();
        mPresenter.getMonthAndDay();
        mPresenter.getQuotes();
        initRadioGroup();
        getFingerprint();
        Log.i(TAG, "initData: "+mToken);
    }


    private void getFingerprint() {
        LiveDataBus.get().with("update", String.class).observe(this, s ->{
            startService(new Intent(mContext, MyService.class));
        });

        LiveDataBus.get().with("verify", UserDataBean.class).observe(this, userDataBean -> {
            Bundle bundle = new Bundle();
            bundle.putInt("studentId", userDataBean.getStudentId());

            if (actionToFetch){
//                mPresenter.checkStudentFingerData(mToken, mMachId, userDataBean.getFingerId());
                mPresenter.checkStudentFingerData(mToken, "505019A", userDataBean.getFingerId());
                actionToFetch = false;
            }else if (actionToRecord){
                skipAnotherActivity(bundle, RecordActivity.class);
                actionToRecord = false;
            }else if (actionToFinger){
                skipAnotherActivity(bundle, FpManagementActivity.class);
            }
        });
    }


    /**
     * 初始化第一点的轮播
     */
    private void initRadioGroup() {
//        RadioButton radioButton = (RadioButton) mDataBinding.mainInstructions.getChildAt(0);
//        mDataBinding.mainInstructions.check(radioButton.getId());
//        radioButton.setLayoutParams(mPresenter.getParams(true));
    }

    @Override
    protected void initListener() {
        super.initListener();
        mDataBinding.mainBorrow.setOnClickListener(this);
        mDataBinding.mainFetch.setOnClickListener(this);
        mDataBinding.mainGiveBack.setOnClickListener(this);
        mDataBinding.mainRecord.setOnClickListener(this);
        mDataBinding.mainFingerprint.setOnClickListener(this);
        mDataBinding.mainLogin.setOnClickListener(this);
//        mDataBinding.mainOperations.setOnLongClickListener(v -> {
//          skipAnotherActivity(ManagerLoginActivity.class);
//           Intent intent  = new Intent(this,MangersActivity.class);
//           this.startActivity(intent);
//            return true;
//        });
    }

    @Override
    public void showDay(String day) {
        mDataBinding.mainDayTime.setText(day);
    }

    @Override
    public void showMonth(String month) {
        mDataBinding.mainMonth.setText(month);
    }

    @Override
    public void showQuotes(List<String> quotes) {
        mDataBinding.mainSentence.setList(quotes);
        if (mDataBinding.mainSentence.getList().size()>0){
            mDataBinding.mainSentence.startScroll();
        }
    }

    @Override
    public void showPagerImg(List<ImageView> img) {

    }

    //    @Override
//    public void showPagerImg(List<ImageView> img) {
//       mDataBinding.mainVp.initViewPager(img, 5, currentPage -> {
//           mDataBinding.mainInstructions.check(mDataBinding.mainInstructions.getChildAt(currentPage).getId());
//           int count = mDataBinding.mainInstructions.getChildCount();
//           for (int i = 0; i <count; i++) {
//               RadioButton rb = (RadioButton) mDataBinding.mainInstructions.getChildAt(i);
//               if (rb.isChecked()){
//                   rb.setLayoutParams(mPresenter.getParams(true));
//               }else {
//                   rb.setLayoutParams(mPresenter.getParams(false));
//               }
//           }
//       });
//    }

public void showPagerImgs() {
    Banner banner = findViewById(R.id.main_vp);
    imageViews = new ArrayList<>();
    for(int i=0;i<3;i++){
        imageViews.add(ResourcesUtil.getBackground().get(i));

    }
    banner.setImages(imageViews).setImageLoader(new GlideImageLoader()).start();

}

    @Override
    public void hasDidNotReturn() {
        Log.i(TAG, "hasDidNotReturn: ");
        RemindDialog dialog = new RemindDialog(mContext);
        dialog.setMsg(getString(R.string.hasDidNotReturn),getString(R.string.go_return), 1);
        dialog.show();
    }

    @Override
    public void hasReserve() {
        Log.i(TAG, "hasReserve: ");
        skipAnotherActivity(FetchActivity.class);
    }

    @Override
    public void noReserve() {
        Log.i(TAG, "noReserve: ");
        OrderDialog dialog = new OrderDialog(mContext);
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_borrow:
                skipAnotherActivity(BorrowActivity.class);
                break;
            case R.id.main_fetch:
                if(verify){
                    skipAnotherActivity(FetchActivity.class);
                }
                skipAnotherActivity(VerifyActivity.class);
                actionToFetch = true;
                break;
            case R.id.main_give_back:
                skipAnotherActivity(ScanActivity.class);
                break;
            case R.id.main_record:
                if(verify){
                    skipAnotherActivity(RecordActivity.class);
                }
                skipAnotherActivity(VerifyActivity.class);
                actionToRecord = true;
                break;
            case R.id.main_fingerprint:
                if(verify){
                    skipAnotherActivity(FpManagementActivity.class);
                }
                skipAnotherActivity(VerifyActivity.class);
                actionToFinger = true;
                break;
            case R.id.main_login:
                ContactUsDialog dialog = new ContactUsDialog(mContext);
                dialog.show();
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDataBinding.mainSentence.stopScroll();
        stopService(new Intent(mContext,MyService.class));
    }
}
