package com.example.xinhuayipin.mvp.contract;

import com.example.commons.mvp.IBasePresenter;
import com.example.commons.mvp.IBaseView;
import com.example.xinhuayipin.data.StudentBean;

import io.reactivex.Observable;
import retrofit2.http.Field;

/**
 * @Author skygge.
 * @Date on 2019-08-22.
 * @Github https://github.com/javofxu
 * @Dec:
 * @version: ${VERSION}.
 * @Update :
 */
public interface ToLoginContract {

    interface ToLoginModel{

        Observable<StudentBean> login(@Field("access_token") String access_token, @Field("machid") String machid, @Field("username") String username, @Field("password") String password);
    }

    interface ToLoginView extends IBaseView{

        void loginSuccess(long studentId, long schoolId, String msg);

        void loginFailed(String msg);

        void networkError();
    }

    interface ToLoginPresent extends IBasePresenter<ToLoginView>{

        void login(String access_token, String machid, String username, String password);
    }
}
