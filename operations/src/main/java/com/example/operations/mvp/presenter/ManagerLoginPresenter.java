package com.example.operations.mvp.presenter;

import com.example.commons.mvp.BasePresenterImpl;
import com.example.operations.data.LoginBean;
import com.example.operations.mvp.RequestModel;
import com.example.operations.mvp.contract.ManagerLoginContract;

import io.reactivex.Observable;

/**
 * @Author skygge.
 * @Date on 2019-09-19.
 * @Github https://github.com/javofxu
 * @Dec:
 * @version: ${VERSION}.
 * @Update :
 */
public class ManagerLoginPresenter extends BasePresenterImpl<ManagerLoginContract.ManagerLoginView> implements ManagerLoginContract.ManagerLoginPresent {

    private ManagerLoginContract.Model mModel;

    @Override
    public void login(String access_token, String username, String password) {
        mModel = new RequestModel();
        Observable<LoginBean> observable = mModel.login(access_token, username, password);
        observable.subscribe(loginBean -> {
            if (loginBean.getCode() == 0){
                mView.successful(loginBean.getData().getUser_token());
            }else {
                mView.failture(loginBean.getMsg());
            }
        }, throwable -> mView.netWorkError());
    }
}
