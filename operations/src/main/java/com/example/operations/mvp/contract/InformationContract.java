package com.example.operations.mvp.contract;

import com.example.commons.data.RequestValue;
import com.example.commons.mvp.IBasePresenter;
import com.example.commons.mvp.IBaseView;

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
public interface InformationContract {

    interface Model {
        Observable<RequestValue> uploadDevice(@Field("access_token") String access_token, @Field("machid") String machid, @Field("name") String name,
                                              @Field("model") String model, @Field("doorcount") String door_count);
    }

    interface InformationView extends IBaseView {
        void successful();

        void failture(String msg);

        void netWorkError();
    }

    interface InformationPresent extends IBasePresenter<InformationView>{

        void saveInformation(String access_token, String machid, String name, String model, String door_count);
    }
}
