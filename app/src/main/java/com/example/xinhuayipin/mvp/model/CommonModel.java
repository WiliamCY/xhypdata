package com.example.xinhuayipin.mvp.model;

import com.example.commons.http.RetrofitClient;
import com.example.xinhuayipin.data.FingerData;
import com.example.xinhuayipin.data.TokenBean;
import com.example.xinhuayipin.http.ApiService;
import com.example.xinhuayipin.mvp.contract.CommonContract;

import io.reactivex.Observable;

/**
 * @Author skygge.
 * @Date on 2019-08-27.
 * @Github https://github.com/javofxu
 * @Dec:
 * @version: ${VERSION}.
 * @Update :
 */
public class CommonModel implements CommonContract.model {

    ApiService api = RetrofitClient.getInstance().getApiService(ApiService.class);

    @Override
    public Observable<TokenBean.Data> token(String access_token, String type, String content) {
        return api.token(access_token, type, content).map(tokenBean -> tokenBean.getData()).compose(RetrofitClient.getInstance().schedulersTransformer);
    }

    @Override
    public Observable<FingerData> getFingerData(String access_token, String machid, String operate_type, String finger_ids) {
        return api.getFingerData(access_token, machid, operate_type, finger_ids).map(fingerData -> fingerData).compose(RetrofitClient.getInstance().schedulersTransformer);
    }

}
