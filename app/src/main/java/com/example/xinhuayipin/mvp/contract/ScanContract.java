package com.example.xinhuayipin.mvp.contract;

import com.example.commons.mvp.IBasePresenter;
import com.example.commons.mvp.IBaseView;
import com.example.xinhuayipin.data.ScanBean;

import io.reactivex.Observable;
import retrofit2.http.Field;

/**
 * @Author skygge.
 * @Date on 2019-08-28.
 * @Github https://github.com/javofxu
 * @Dec:
 * @version: ${VERSION}.
 * @Update :
 */
public interface ScanContract {

    interface ScanModel{

        Observable<ScanBean> getBookByIsbn(@Field("access_token") String access_token, @Field("isbn") String isbn, @Field("machid") String machid, @Field("student_id") String student_id);


    }

    interface ScanView extends IBaseView{

        void bookOutOfCase();

        void getBookId(ScanBean.Data data);

        void scanBookError(String msg);

        void netWorkError();
    }

    interface ScanPresent extends IBasePresenter<ScanView>{

        void getBookByIsbn(String access_token, String isbn, String machid, String student_id);

    }
}
