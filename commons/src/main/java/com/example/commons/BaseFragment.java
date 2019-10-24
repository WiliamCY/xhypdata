package com.example.commons;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.commons.annotations.ViewInject;
import com.example.commons.mvp.IBasePresenter;
import com.example.commons.mvp.IBaseView;
import com.example.commons.utils.SpHelperUtil;


/**
 * @Author skygge.
 * @Date on 2019-09-03.
 * @Github https://github.com/javofxu
 * @Dec: Fragment基础类
 * @version: ${VERSION}.
 * @Update :
 */
public class BaseFragment<T extends IBasePresenter, B extends ViewDataBinding> extends Fragment implements IBaseView {

    protected String TAG = getClass().getSimpleName();
    protected Activity mContext;
    protected T mPresenter;
    protected B mDataBinding;
    protected String mToken;
    protected String mMac = "505019A";
    private AlertDialog mProgressDialog;

    private View mView;
    private boolean mIsPrepare = false;		//视图还没准备好
    private boolean mIsVisible= false;		//不可见
    private boolean mIsFirstLoad = true;	//第一次加载

    public BaseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        initialize();
        getToken();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewInject annotation = getClass().getAnnotation(ViewInject.class);
        if (annotation!=null){
            int layoutId = annotation.getLayoutId();
            if (layoutId>0){
                mDataBinding = DataBindingUtil.inflate(inflater, layoutId, container, false);
                mView = mDataBinding.getRoot();
            }else {
                throw new RuntimeException(getString(R.string.no_layout));
            }
        }else{
            throw new RuntimeException(getString(R.string.no_layout));
        }
        initPresent();
        if (mPresenter!=null){
            mPresenter.attachView(this);
        }
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mIsPrepare = true;
        lazyLoad();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            mIsVisible = true;
            lazyLoad();
        } else {
            mIsVisible = false;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
        initListener();
    }

    private void lazyLoad() {
        //这里进行三个条件的判断，如果有一个不满足，都将不进行加载
        if (!mIsPrepare || !mIsVisible||!mIsFirstLoad) {
            return;
        }
        loadData();
        //数据加载完毕,恢复标记,防止重复加载
        mIsFirstLoad = false;
    }

    protected void loadData() {

    }

    protected void initialize() {
    }

    private void getToken() {
        SpHelperUtil mSpUtil = new SpHelperUtil(getActivity());
        mToken = (String) mSpUtil.getSharedPreference("token","");
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
            Window window = getActivity().getWindow();
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
        Toast.makeText(getActivity(), content, Toast.LENGTH_SHORT).show();
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
        mProgressDialog = new AlertDialog.Builder(getActivity()).create();
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
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: ");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mIsFirstLoad=true;
        mIsPrepare=false;
        mIsVisible = false;
        if (mView != null) {
            ((ViewGroup) mView.getParent()).removeView(mView);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter!=null){
            mPresenter.detachView();
        }
        Log.i(TAG, "onDestroy: ");
    }
}
