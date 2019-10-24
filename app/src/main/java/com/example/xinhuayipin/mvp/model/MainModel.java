package com.example.xinhuayipin.mvp.model;

import com.example.commons.http.RetrofitClient;
import com.example.xinhuayipin.data.fetch.FetchBean;
import com.example.xinhuayipin.http.ApiService;
import com.example.xinhuayipin.mvp.contract.MainContract;

import io.reactivex.Observable;
/**
 * @Author skygge.
 * @Date on 2019-08-30.
 * @Github https://github.com/javofxu
 * @Dec:
 * @version: ${VERSION}.
 * @Update :
 */
public class MainModel implements MainContract.MainModel {

    ApiService api = RetrofitClient.getInstance().getApiService(ApiService.class);

    @Override
    public Observable<FetchBean> checkStudentFingerData(String access_token, String machid, String finger_id) {
        return api.checkStudentFingerData(access_token, machid, finger_id).map(fetchBean -> fetchBean).compose(RetrofitClient.getInstance().schedulersTransformer);
    }

    @Override
    public Observable<FetchBean> checkStudentByStudentID(String access_token, String machid, String student_id) {
        return api.checkStudentByStudentID(access_token, machid, student_id).map(fetchBean -> fetchBean).compose(RetrofitClient.getInstance().schedulersTransformer);
    }
}
