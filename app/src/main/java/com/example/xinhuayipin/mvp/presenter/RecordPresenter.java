package com.example.xinhuayipin.mvp.presenter;

import com.example.commons.mvp.BasePresenterImpl;
import com.example.xinhuayipin.data.fetch.FetchBean;
import com.example.xinhuayipin.mvp.contract.RecordContract;
import com.example.xinhuayipin.mvp.model.RecordModel;

import io.reactivex.Observable;

/**
 * @Author skygge.
 * @Date on 2019-09-11.
 * @Github https://github.com/javofxu
 * @Dec:
 * @version: ${VERSION}.
 * @Update :
 */
public class RecordPresenter extends BasePresenterImpl<RecordContract.RecordView> implements RecordContract.RecordPresent {

    private RecordContract.RecordModel model;

    @Override
    public void checkStudentByStudentID(String token, String mac, int studentID) {
        model = new RecordModel();
        Observable<FetchBean> observable = model.checkStudentByStudentID(token, mac, String.valueOf(studentID));
        observable.subscribe(fetchBean -> {
            if (fetchBean.getCode()==0){
                if (fetchBean.getData().getLend_info().getCount()>0){
                    mView.hasDidNotReturn();
                }else {
                    if (fetchBean.getData().getBook_reserve_info().getCount()>0){
                        mView.hasReserve();
                    }else {
                        mView.noReserve();
                    }
                }
            }else {
            }
        });
    }
}
