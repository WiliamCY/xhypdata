package com.example.xinhuayipin.mvp.presenter;

import com.example.commons.mvp.BasePresenterImpl;
import com.example.xinhuayipin.data.record.RecordBean;
import com.example.xinhuayipin.mvp.contract.GiveBackContract;
import com.example.xinhuayipin.mvp.contract.RecordContract;
import com.example.xinhuayipin.mvp.model.RecordModel;

import io.reactivex.Observable;

/**
 * @Author skygge.
 * @Date on 2019-09-10.
 * @Github https://github.com/javofxu
 * @Dec:
 * @version: ${VERSION}.
 * @Update :
 */
public class GiveBackPresenter extends BasePresenterImpl<GiveBackContract.GiveBackView> implements GiveBackContract.GiveBackPresent {

    private RecordContract.RecordModel model;

    @Override
    public void getReserveInfo(String token, int studentID, int type) {
        model = new RecordModel();
        Observable<RecordBean> observable = model.lendInfo(token, String.valueOf(studentID), String.valueOf(type));
        observable.subscribe(recordBean -> {
            if (recordBean.getData().getLend_history_count()==0){
                mView.noGiveBack();
            }else {
                mView.hasGiveBack(recordBean.getData().getLend_history_info());
            }
        },throwable -> mView.netWorkError());
    }
}
