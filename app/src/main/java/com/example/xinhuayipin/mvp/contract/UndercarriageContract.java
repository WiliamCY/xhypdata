package com.example.xinhuayipin.mvp.contract;

import com.example.commons.mvp.IBasePresenter;
import com.example.commons.mvp.IBaseView;
import com.example.xinhuayipin.data.TokenBean;

import io.reactivex.Observable;
import retrofit2.http.Field;

public interface UndercarriageContract {
      interface  model{
          Observable<TokenBean.Data> clearBook(@Field("access_token") String access_token,@Field("bookcase_id") String machid,@Field("grid_ids") String book_id,@Field("user_token") String bookcase_id);

      }
    interface ClearView extends IBaseView {

        void loginSuccess(long studentId, long schoolId, String msg);

        void loginFailed(String msg);

        void networkError();
    }
    interface  ClearPresent extends IBasePresenter<ClearView>{
          void clearBook(String access_token, String machid, String book_id, String bookcase_id);
    }
}
