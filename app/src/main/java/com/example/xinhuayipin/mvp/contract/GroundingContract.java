package com.example.xinhuayipin.mvp.contract;

import com.example.commons.mvp.IBasePresenter;
import com.example.commons.mvp.IBaseView;
import com.example.xinhuayipin.data.TokenBean;

import io.reactivex.Observable;
import retrofit2.http.Field;

public interface GroundingContract {
      interface  model{
          Observable<TokenBean.Data> bindBook(@Field("access_token") String access_token,@Field("user_token") String machid,@Field("book_id") String book_id,@Field("bookcase_id") String bookcase_id);

      }
    interface BindView extends IBaseView {

        void loginSuccess(long studentId, long schoolId, String msg);

        void loginFailed(String msg);

        void networkError();
    }
    interface  BindPresent extends IBasePresenter<BindView>{
          void bindBook(String access_token, String machid,String book_id,String bookcase_id);
    }
}
