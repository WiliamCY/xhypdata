package com.example.xinhuayipin.mvp.presenter;

import com.example.commons.mvp.BasePresenterImpl;
import com.example.xinhuayipin.data.StudentBean;
import com.example.xinhuayipin.mvp.contract.ToLoginContract;
import com.example.xinhuayipin.mvp.model.ToLoginModel;

import io.reactivex.Observable;

/**
 * @Author skygge.
 * @Date on 2019-08-22.
 * @Github https://github.com/javofxu
 * @Dec:
 * @version: ${VERSION}.
 * @Update :
 */
public class ToLoginPresenter extends BasePresenterImpl<ToLoginContract.ToLoginView> implements ToLoginContract.ToLoginPresent {

    private ToLoginContract.ToLoginModel model;

    @Override
    public void login(String access_token, String machid, String username, String password) {
        model = new ToLoginModel();
        Observable<StudentBean> observable = model.login(access_token, machid, username, password);
        observable.subscribe(studentBean -> {
            if (studentBean.getCode() == 0) {
                long studentId = studentBean.getData().getStudent_info().getId();
                long schoolId = studentBean.getData().getStudent_info().getSchool_id();
                mView.loginSuccess(studentId, schoolId, studentBean.getMsg());
            } else {
                mView.loginFailed(studentBean.getMsg());
            }
        }, throwable -> mView.networkError());
    }

}
