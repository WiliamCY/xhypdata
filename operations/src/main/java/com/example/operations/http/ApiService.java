package com.example.operations.http;

import com.example.commons.data.RequestValue;
import com.example.operations.data.LoginBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @Author skygge.
 * @Date on 2019-09-19.
 * @Github https://github.com/javofxu
 * @Dec:
 * @version: ${VERSION}.
 * @Update :
 */
public interface ApiService {

    String MANAGER_LOGIN = "manage/managelogin";
    String UPLOAD_DEVICE = "special/uploadDevice";

    @FormUrlEncoded
    @POST(UPLOAD_DEVICE)
    Observable<RequestValue> uploadDevice(@Field("access_token") String access_token, @Field("machid") String machid, @Field("name") String name,
                                          @Field("model") String model, @Field("doorcount") String door_count);

    @FormUrlEncoded
    @POST(MANAGER_LOGIN)
    Observable<LoginBean> login(@Field("access_token") String access_token, @Field("username") String username, @Field("password") String password);
}
