package com.example.xinhuayipin.mvp.contract;

import com.example.commons.data.RequestValue;
import com.example.commons.mvp.IBasePresenter;
import com.example.commons.mvp.IBaseView;

import io.reactivex.Observable;
import retrofit2.http.Field;

/**
 * @Author skygge.
 * @Date on 2019-09-03.
 * @Github https://github.com/javofxu
 * @Dec:
 * @version: ${VERSION}.
 * @Update :
 */
public interface SuccessContract {

    interface SuccessModel{

        Observable<RequestValue> returnBack(@Field("access_token") String access_token, @Field("machid") String machid, @Field("book_id") String book_id);

    }

    interface SuccessView extends IBaseView{

        void successful();

        void failture(String msg);

        void netWorkError();
    }

    interface SuccessPresent extends IBasePresenter<SuccessView>{

        void returnBack(String access_token, String machid, String book_id);

    }
}
