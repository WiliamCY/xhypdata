package com.example.xinhuayipin.mvp.model;

import com.example.commons.http.RetrofitClient;
import com.example.xinhuayipin.data.ScanBean;
import com.example.xinhuayipin.http.ApiService;
import com.example.xinhuayipin.mvp.contract.ScanContract;

import io.reactivex.Observable;

/**
 * @Author skygge.
 * @Date on 2019-09-11.
 * @Github https://github.com/javofxu
 * @Dec:
 * @version: ${VERSION}.
 * @Update :
 */
public class ScanModel implements ScanContract.ScanModel {

    ApiService api = RetrofitClient.getInstance().getApiService(ApiService.class);

    @Override
    public Observable<ScanBean> getBookByIsbn(String access_token, String isbn, String machid, String student_id) {
        return api.getBookByIsbn(access_token, isbn, machid, student_id).map(scanBean -> scanBean).compose(RetrofitClient.getInstance().schedulersTransformer);
    }

}
