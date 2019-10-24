package com.example.xinhuayipin.mvp.contract;

import com.example.xinhuayipin.data.FingerData;
import com.example.xinhuayipin.data.TokenBean;

import io.reactivex.Observable;
import retrofit2.http.Field;

/**
 * @Author skygge.
 * @Date on 2019-08-27.
 * @Github https://github.com/javofxu
 * @Dec:
 * @version: ${VERSION}.
 * @Update :
 */
public interface CommonContract {

    interface model{

        Observable<TokenBean.Data> token(@Field("access_token") String access_token, @Field("type") String type, @Field("content") String content);

        Observable<FingerData> getFingerData(@Field("access_token") String access_token, @Field("machid") String machid, @Field("operate_type") String operate_type, @Field("finger_ids") String finger_ids);

    }
}
