package com.example.xinhuayipin.mvp.contract;

import com.example.commons.mvp.IBasePresenter;
import com.example.commons.mvp.IBaseView;

/**
 * @Author skygge.
 * @Date on 2019-08-22.
 * @Github https://github.com/javofxu
 * @Dec:
 * @version: ${VERSION}.
 * @Update :
 */
public interface LoginContract {

    interface LoginView extends IBaseView{
        void countdown(int time);
    }

    interface LoginPresent extends IBasePresenter<LoginView>{

        void countdownFinish();

        void closeHandler();
    }
}
