package com.example.xinhuayipin.mvp.model;

import com.example.commons.http.RetrofitClient;
import com.example.xinhuayipin.data.fetch.FetchBean;
import com.example.xinhuayipin.data.record.RecordBean;
import com.example.xinhuayipin.http.ApiService;
import com.example.xinhuayipin.mvp.contract.RecordContract;
import com.example.xinhuayipin.mvp.contract.WaitFetchContract;

import io.reactivex.Observable;

/**
 * @Author skygge.
 * @Date on 2019-09-10.
 * @Github https://github.com/javofxu
 * @Dec:
 * @version: ${VERSION}.
 * @Update :
 */
public class RecordModel implements RecordContract.RecordModel {

    ApiService api = RetrofitClient.getInstance().getApiService(ApiService.class);

    @Override
    public Observable<RecordBean> lendInfo(String access_token, String student_id, String type) {
        return api.lendInfo(access_token, student_id, type).map(recordBean -> recordBean).compose(RetrofitClient.getInstance().schedulersTransformer);
    }

    @Override
    public Observable<FetchBean> checkStudentByStudentID(String access_token, String machid, String student_id) {
        return api.checkStudentByStudentID(access_token, machid, student_id).map(fetchBean -> fetchBean).compose(RetrofitClient.getInstance().schedulersTransformer);
    }
}
