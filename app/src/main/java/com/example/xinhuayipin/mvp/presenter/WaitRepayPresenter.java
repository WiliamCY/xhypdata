package com.example.xinhuayipin.mvp.presenter;

import com.example.commons.mvp.BasePresenterImpl;
import com.example.xinhuayipin.data.record.RecordBean;
import com.example.xinhuayipin.mvp.contract.RecordContract;
import com.example.xinhuayipin.mvp.contract.WaitRepayContract;
import com.example.xinhuayipin.mvp.model.RecordModel;

import io.reactivex.Observable;

/**
 * @Author skygge.
 * @Date on 2019-09-05.
 * @Github https://github.com/javofxu
 * @Dec:
 * @version: ${VERSION}.
 * @Update :
 */
public class WaitRepayPresenter extends BasePresenterImpl<WaitRepayContract.WaitRepayView> implements WaitRepayContract.WaitRepayPresent {

    private RecordContract.RecordModel model;

    @Override
    public void getReserveInfo(String token, int studentID, int type) {
        model = new RecordModel();
        Observable<RecordBean> observable = model.lendInfo(token, String.valueOf(studentID), String.valueOf(type));
        observable.subscribe(recordBean -> {
            if (recordBean.getData().getLend_count()==0){
                mView.noRepay();
            }else {
                mView.hasRepay(recordBean.getData().getLend_info());
            }
        },throwable -> mView.netWorkError());
    }
}
