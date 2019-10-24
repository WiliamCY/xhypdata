package com.example.commons;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.commons.annotations.ViewInject;
import com.example.commons.mvp.IBasePresenter;
import com.example.commons.mvp.IBaseView;
import com.example.commons.utils.SpHelperUtil;

/**
 * @Author skygge.
 * @Date on 2019-08-13.
 * @Github https://github.com/javofxu
 * @Dec: Activity基础类
 * @version: ${VERSION}.
 * @Update :
 */

public class BaseActivity<T extends IBasePresenter, B extends ViewDataBinding> extends AppCompatActivity implements IBaseView {

    protected String TAG = getClass().getSimpleName();
    protected Context mContext;
    protected T mPresenter;
    protected B mDataBinding;
    protected String mToken;
    protected String mMachId;
    private AlertDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        initialize();
        ViewInject annotation = getClass().getAnnotation(ViewInject.class);
        if (annotation!=null){
            int layoutId = annotation.getLayoutId();
            if (layoutId>0){
                setContentView(layoutId);
                mDataBinding = DataBindingUtil.setContentView(this, layoutId);
            }else {
                throw new RuntimeException(getString(R.string.no_layout));
            }
        }else {
            setContentView(getLayoutId());
            mDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        }
        initPresent();
        if (mPresenter!=null){
            mPresenter.attachView(this);
        }
        getToken();
        initView();
        initData();
        startServices();
        initListener();
    }

    protected void initialize() {
    }

    private void getToken() {
        SpHelperUtil mSpUtil = new SpHelperUtil(this);
        mToken = (String) mSpUtil.getSharedPreference("token","");
//        mMachId = (String) mSpUtil.getSharedPreference("machid","");
        mMachId = "505019A";
    }

    protected int getLayoutId(){
        return -1;
    }

    protected void initView() {
    }

    /**
     * 初始化数据
     */
    protected void initData() {

    }

    /**
     * 监听事件写在这
     */
    protected void initListener() {
    }

    /**
     * 初始化Presenter
     */
    protected void initPresent() {
    }

    /**
     * 开启服务
     */
    protected void startServices(){
    }

    /**
     * 关闭服务
     */
    protected void stopService(){
    }

    /**
     * 隐藏状态栏和虚拟栏
     */
    protected void hideStatusBar() {
        setStatusBarColor(Color.TRANSPARENT, true);
    }

    /**
     * 为状态栏设置颜色
     */
    protected void setStatusBarColor(int color, boolean isNavigationBarHide) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //将状态栏设置成透明
            window.setStatusBarColor(color);
            //将底部的虚拟键设置成共鸣
            if (isNavigationBarHide) {
                //window.setNavigationBarColor(Color.parseColor("#000000"));
            }
        }
    }

    protected void skipAnotherActivity(Class<?> mClass){
        startActivity(new Intent(mContext, mClass));
    }

    protected void skipAnotherActivity(Bundle bundle, Class<?> clazz) {
        Intent intent = new Intent(mContext, clazz);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    protected void  showToast(String content) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSuccess() {

    }

    @Override
    public void showFailed() {

    }

    @Override
    public void showProgressDialog() {
        progressDialog();
    }

    @Override
    public void dismissProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    protected final void progressDialog() {
        mProgressDialog = new AlertDialog.Builder(this).create();
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        Window window = mProgressDialog.getWindow();
        window.setContentView(R.layout.dialog_progress);
        window.setDimAmount(0f);
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawableResource(android.R.color.transparent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService();
        if (mPresenter!=null){
            mPresenter.detachView();
        }
        Log.i(TAG, "onDestroy: ");
    }

    public void finishActivity(View view) {
        finish();
    }
}
