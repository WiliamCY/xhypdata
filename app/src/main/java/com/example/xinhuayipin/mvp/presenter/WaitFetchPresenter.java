package com.example.xinhuayipin.mvp.presenter;

import com.example.commons.mvp.BasePresenterImpl;
import com.example.xinhuayipin.data.record.RecordBean;
import com.example.xinhuayipin.mvp.contract.RecordContract;
import com.example.xinhuayipin.mvp.contract.WaitFetchContract;
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
public class WaitFetchPresenter extends BasePresenterImpl<WaitFetchContract.WaitFetchView> implements WaitFetchContract.WaitFetchPresent {

    private RecordContract.RecordModel model;

    @Override
    public void getReserveInfo(String token, int studentID, int type) {
        model = new RecordModel();
        Observable<RecordBean> observable = model.lendInfo(token, String.valueOf(studentID), String.valueOf(type));
        observable.subscribe(recordBean -> {
            if (recordBean.getData().getReserve_count()==0){
                mView.noFetch();
            }else {
                mView.hasFetch(recordBean.getData().getReserve_info());
            }
        },throwable -> mView.netWorkError());
    }
}
