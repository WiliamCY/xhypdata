package com.example.xinhuayipin.mvp.model;

import com.example.commons.data.RequestValue;
import com.example.commons.http.RetrofitClient;
import com.example.xinhuayipin.http.ApiService;
import com.example.xinhuayipin.mvp.contract.SuccessContract;

import io.reactivex.Observable;

/**
 * @Author skygge.
 * @Date on 2019-09-11.
 * @Github https://github.com/javofxu
 * @Dec:
 * @version: ${VERSION}.
 * @Update :
 */
public class SuccessModel implements SuccessContract.SuccessModel {

    ApiService api = RetrofitClient.getInstance().getApiService(ApiService.class);

    @Override
    public Observable<RequestValue> returnBack(String access_token, String machid, String book_id) {
        return api.returnBack(access_token, machid, book_id).map(requestValue -> requestValue).compose(RetrofitClient.getInstance().schedulersTransformer);
    }
}
