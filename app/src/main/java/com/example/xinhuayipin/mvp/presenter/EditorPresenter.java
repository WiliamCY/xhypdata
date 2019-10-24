package com.example.xinhuayipin.mvp.presenter;

import android.content.Context;

import com.example.commons.mvp.BasePresenterImpl;
import com.example.xinhuayipin.app.MyApplication;
import com.example.xinhuayipin.data.DaoSession;
import com.example.xinhuayipin.data.FingerprintBean;
import com.example.xinhuayipin.mvp.contract.EditorContract;

/**
 * @Author skygge.
 * @Date on 2019-08-22.
 * @Github https://github.com/javofxu
 * @Dec:
 * @version: ${VERSION}.
 * @Update :
 */
public class EditorPresenter extends BasePresenterImpl<EditorContract.EditorView> implements EditorContract.EditorPresent {

    private Context mContext;
    private DaoSession daoSession;
    private FingerprintBean bean;

    public EditorPresenter(Context mContext) {
        this.mContext = mContext;
        daoSession = MyApplication.getInstance().getDaoSession();
    }

    @Override
    public void getFingerprint(Long id) {
        bean = daoSession.getFingerprintBeanDao().load(id);
        String name = "手指"+bean.getFingerprint_id();
        mView.setFingerName(name);
    }

    @Override
    public void saveFingerName(String name) {
        //bean.setFingerName(name);
        daoSession.getFingerprintBeanDao().update(bean);
        mView.complete();
    }


    @Override
    public void deleteFingerName() {
        daoSession.getFingerprintBeanDao().delete(bean);
        mView.complete();
    }
}
