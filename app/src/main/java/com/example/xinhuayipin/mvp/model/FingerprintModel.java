package com.example.xinhuayipin.mvp.model;

import com.example.commons.data.RequestValue;
import com.example.commons.http.RetrofitClient;
import com.example.xinhuayipin.http.ApiService;
import com.example.xinhuayipin.mvp.contract.FingerprintContract;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @Author skygge.
 * @Date on 2019-08-28.
 * @Github https://github.com/javofxu
 * @Dec:
 * @version: ${VERSION}.
 * @Update :
 */
public class FingerprintModel implements FingerprintContract.FingerprintModel {

    ApiService api = RetrofitClient.getInstance().getApiService(ApiService.class);

    @Override
    public Observable<RequestValue> uploadFinger(RequestBody access_token, RequestBody fingerdata, MultipartBody.Part finger_pic, RequestBody student_id, RequestBody school_id) {
        return api.uploadFinger(access_token, fingerdata, finger_pic, student_id, school_id).map(requestValue -> requestValue).compose(RetrofitClient.getInstance().schedulersTransformer);
    }
}
