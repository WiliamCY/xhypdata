package com.example.operations.mvp.presenter;

import com.example.commons.data.RequestValue;
import com.example.commons.mvp.BasePresenterImpl;
import com.example.operations.mvp.RequestModel;
import com.example.operations.mvp.contract.InformationContract;

import io.reactivex.Observable;

/**
 * @Author skygge.
 * @Date on 2019-09-19.
 * @Github https://github.com/javofxu
 * @Dec:
 * @version: ${VERSION}.
 * @Update :
 */
public class InformationPresenter extends BasePresenterImpl<InformationContract.InformationView> implements InformationContract.InformationPresent {

    private InformationContract.Model mModel = new RequestModel();

    @Override
    public void saveInformation(String access_token, String machid, String name, String model, String door_count) {
        Observable<RequestValue> observable = mModel.uploadDevice(access_token, machid, name, model, door_count);
        observable.subscribe(requestValue -> {
            if (requestValue.getCode() == 0){
                mView.successful();
            }else {
                mView.failture(requestValue.getMsg());
            }
        }, throwable -> mView.netWorkError());
    }
}
