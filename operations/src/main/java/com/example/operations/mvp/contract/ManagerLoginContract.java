package com.example.operations.mvp.contract;

import com.example.commons.mvp.IBasePresenter;
import com.example.commons.mvp.IBaseView;
import com.example.operations.data.LoginBean;

import io.reactivex.Observable;
import retrofit2.http.Field;

/**
 * @Author skygge.
 * @Date on 2019-09-19.
 * @Github https://github.com/javofxu
 * @Dec:
 * @version: ${VERSION}.
 * @Update :
 */
public interface ManagerLoginContract {

    interface Model{

        Observable<LoginBean> login(@Field("access_token") String access_token, @Field("username") String username, @Field("password") String password);
    }

    interface ManagerLoginView extends IBaseView {

        void successful(String user_token);

        void failture(String msg);

        void netWorkError();
    }

    interface ManagerLoginPresent extends IBasePresenter<ManagerLoginView> {

        void login(String access_token, String username, String password);
    }
}
