package com.example.xinhuayipin.mvp.model;

import com.example.commons.http.RetrofitClient;
import com.example.xinhuayipin.data.StudentBean;
import com.example.xinhuayipin.http.ApiService;
import com.example.xinhuayipin.mvp.contract.ToLoginContract;

import io.reactivex.Observable;

/**
 * @Author skygge.
 * @Date on 2019-08-26.
 * @Github https://github.com/javofxu
 * @Dec:
 * @version: ${VERSION}.
 * @Update :
 */
public class ToLoginModel implements ToLoginContract.ToLoginModel {

    ApiService api = RetrofitClient.getInstance().getApiService(ApiService.class);

    @Override
    public Observable<StudentBean> login(String access_token, String machid, String username, String password) {
        return api.login(access_token, machid, username, password).map(studentBean -> studentBean).compose(RetrofitClient.getInstance().schedulersTransformer);
    }
}
