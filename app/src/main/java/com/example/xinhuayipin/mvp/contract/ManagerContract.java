package com.example.xinhuayipin.mvp.contract;

import com.example.commons.mvp.IBasePresenter;
import com.example.commons.mvp.IBaseView;

/**
 * @Author skygge.
 * @Date on 2019-09-19.
 * @Github https://github.com/javofxu
 * @Dec:
 * @version: ${VERSION}.
 * @Update :
 */
public interface ManagerContract {

    interface ManagerView extends IBaseView{

    }

    interface ManagerPresent extends IBasePresenter<ManagerView>{

    }
}
