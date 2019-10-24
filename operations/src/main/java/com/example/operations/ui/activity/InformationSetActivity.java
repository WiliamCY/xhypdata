package com.example.operations.ui.activity;

import android.text.TextUtils;

import com.example.commons.BaseActivity;
import com.example.commons.utils.SpHelperUtil;
import com.example.operations.R;
import com.example.operations.data.DaoSession;
import com.example.operations.data.DeviceBean;
import com.example.operations.data.GreenDaoUtil;
import com.example.operations.databinding.ActivityInformationSetBinding;
import com.example.operations.mvp.contract.InformationContract;
import com.example.operations.mvp.presenter.InformationPresenter;

public class InformationSetActivity extends BaseActivity<InformationPresenter, ActivityInformationSetBinding> implements InformationContract.InformationView {

    private String name;
    private String number;
    private String door;
    private DaoSession daoSession;
    private DeviceBean deviceBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_information_set;
    }

    @Override
    protected void initPresent() {
        super.initPresent();
        mPresenter = new InformationPresenter();
    }

    @Override
    protected void initView() {
        super.initView();
        GreenDaoUtil.getInstance().initGreenDao(this);
        daoSession = GreenDaoUtil.getInstance().getDaoSession();
    }

    @Override
    protected void initData() {
        super.initData();
        if (!TextUtils.isEmpty(mMachId)){
            deviceBean = daoSession.getDeviceBeanDao().load((long) 1);
            if (deviceBean!=null){
                mDataBinding.setCaseName.setInputContent(deviceBean.getName());
                mDataBinding.setCaseNumber.setInputContent(deviceBean.getMachid());
                mDataBinding.setCaseDoorCount.setInputContent(deviceBean.getDoor_count());
            }
            mDataBinding.setInformationSave.setEnabled(false);
            mDataBinding.setCaseName.setCursorVisible(false);
            mDataBinding.setCaseNumber.setCursorVisible(false);
            mDataBinding.setCaseDoorCount.setCursorVisible(false);
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        mDataBinding.setInformationSave.setOnClickListener(v -> {
            name = mDataBinding.setCaseName.getInputContent();
            number = mDataBinding.setCaseNumber.getInputContent();
            door = mDataBinding.setCaseDoorCount.getInputContent();
            mPresenter.saveInformation(mToken, number, name, "", door);
        });
    }

    @Override
    public void successful() {
        showToast("设置成功！");
        SpHelperUtil mSpUtil = new SpHelperUtil(this);
        mSpUtil.put("machid", number);
        DeviceBean deviceBean = new DeviceBean();
        deviceBean.setName(name);
        deviceBean.setMachid(number);
        deviceBean.setDoor_count(door);
        daoSession.getDeviceBeanDao().insert(deviceBean);
        finish();
    }

    @Override
    public void failture(String msg) {
        showToast(msg);
    }

    @Override
    public void netWorkError() {
        showToast("网络异常!");
    }
}
