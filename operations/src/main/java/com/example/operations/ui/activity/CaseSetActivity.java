package com.example.operations.ui.activity;

import com.example.commons.BaseActivity;
import com.example.commons.utils.SpHelperUtil;
import com.example.operations.R;
import com.example.operations.data.DaoSession;
import com.example.operations.data.GreenDaoUtil;
import com.example.operations.ui.dialog.FactoryDialog;

public class CaseSetActivity extends BaseActivity{

    private DaoSession daoSession;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_case_set;
    }

    @Override
    protected void initView() {
        super.initView();
        GreenDaoUtil.getInstance().initGreenDao(this);
        daoSession = GreenDaoUtil.getInstance().getDaoSession();
    }

    @Override
    protected void initListener() {
        super.initListener();
        findViewById(R.id.set_information).setOnClickListener(v -> skipAnotherActivity(InformationSetActivity.class));
        findViewById(R.id.factory_reset).setOnClickListener(v -> {
            FactoryDialog dialog = new FactoryDialog(this, () -> {
                daoSession.getDeviceBeanDao().deleteAll();
                SpHelperUtil spHelperUtil = new SpHelperUtil(mContext);
                spHelperUtil.remove("machid");
                showToast("恢复出厂设置完成!");
            });
            dialog.show();
        });

    }
}
