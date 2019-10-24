package com.example.xinhuayipin.mvp.contract;

import com.example.commons.mvp.IBasePresenter;
import com.example.commons.mvp.IBaseView;

/**
 * @Author skygge.
 * @Date on 2019-08-26.
 * @Github https://github.com/javofxu
 * @Dec:
 * @version: ${VERSION}.
 * @Update :
 */
public interface FetchContract {

    interface FetchModel{

    }

    interface FetchView extends IBaseView{

        void successful();

        void failure();
    }

    interface FetchPresent extends IBasePresenter<FetchView>{
        void getFetchData();
    }
}
