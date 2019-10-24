package com.example.xinhuayipin.mvp.presenter;

import com.example.commons.data.RequestValue;
import com.example.commons.mvp.BasePresenterImpl;
import com.example.xinhuayipin.mvp.contract.SuccessContract;
import com.example.xinhuayipin.mvp.model.SuccessModel;

import io.reactivex.Observable;

/**
 * @Author skygge.
 * @Date on 2019-09-03.
 * @Github https://github.com/javofxu
 * @Dec:
 * @version: ${VERSION}.
 * @Update :
 */
public class SuccessPresenter extends BasePresenterImpl<SuccessContract.SuccessView> implements SuccessContract.SuccessPresent {

    private SuccessContract.SuccessModel model;

    @Override
    public void returnBack(String access_token, String machid, String book_id) {
        model = new SuccessModel();
        Observable<RequestValue> observable = model.returnBack(access_token, machid, book_id);
        observable.subscribe(requestValue -> {
            if (requestValue.getCode() == 0){
                mView.successful();
            }else {
                mView.failture(requestValue.getMsg());
            }
        }, throwable -> mView.netWorkError());
    }
}
