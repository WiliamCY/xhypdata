package com.example.xinhuayipin.mvp.presenter;

import android.util.Log;

import com.example.commons.mvp.BasePresenterImpl;
import com.example.xinhuayipin.app.MyApplication;
import com.example.xinhuayipin.data.DaoSession;
import com.example.xinhuayipin.data.FingerprintBean;
import com.example.xinhuayipin.mvp.contract.ManagementContract;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author skygge.
 * @Date on 2019-08-21.
 * @Github https://github.com/javofxu
 * @Dec:
 * @version: ${VERSION}.
 * @Update :
 */
public class ManagementPresenter extends BasePresenterImpl<ManagementContract.ManagementView> implements ManagementContract.ManagementPresent {

    private static final String TAG = ManagementPresenter.class.getSimpleName();
    private DaoSession daoSession;
    private List<FingerprintBean> fingerprintList;

    @Override
    public void getFingerprint(String userId) {
        daoSession = MyApplication.getInstance().getDaoSession();
        fingerprintList = new ArrayList<>();
        fingerprintList = daoSession.queryRaw(FingerprintBean.class, " where STUDENT_ID = ?", userId);
        Log.i(TAG, "getFingerprint: "+fingerprintList.size());
        if (fingerprintList.size()>0) {
            mView.setFingerprintList(fingerprintList);
        }else {
            mView.noFingerprintData();
        }
    }
}
