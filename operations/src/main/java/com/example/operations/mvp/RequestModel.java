package com.example.operations.mvp;

import com.example.commons.data.RequestValue;
import com.example.commons.http.RetrofitClient;
import com.example.operations.data.LoginBean;
import com.example.operations.http.ApiService;
import com.example.operations.mvp.contract.InformationContract;
import com.example.operations.mvp.contract.ManagerLoginContract;

import io.reactivex.Observable;

/**
 * @Author skygge.
 * @Date on 2019-09-19.
 * @Github https://github.com/javofxu
 * @Dec:
 * @version: ${VERSION}.
 * @Update :
 */
public class RequestModel implements InformationContract.Model , ManagerLoginContract.Model {

    ApiService apiService = RetrofitClient.getInstance().getApiService(ApiService.class);

    @Override
    public Observable<RequestValue> uploadDevice(String access_token, String machid, String name, String model, String door_count) {
        return apiService.uploadDevice(access_token, machid, name, model, door_count).map(requestValue -> requestValue).compose(RetrofitClient.getInstance().schedulersTransformer);
    }

    @Override
    public Observable<LoginBean> login(String access_token, String username, String password) {
        return apiService.login(access_token, username, password).map(loginBean -> loginBean).compose(RetrofitClient.getInstance().schedulersTransformer);
    }
}
