package com.example.xinhuayipin.mvp.model;

import com.example.commons.http.RetrofitClient;
import com.example.xinhuayipin.data.TokenBean;
import com.example.xinhuayipin.http.ApiService;
import com.example.xinhuayipin.mvp.contract.GroundingContract;
import com.example.xinhuayipin.mvp.contract.UndercarriageContract;

import io.reactivex.Observable;

/**
 * @Author skygge.
 * @Date on 2019-09-02.
 * @Github https://github.com/javofxu
 * @Dec:
 * @version: ${VERSION}.
 * @Update :
 */
public class UnbercarriageModel implements UndercarriageContract.model {

    ApiService api = RetrofitClient.getInstance().getApiService(ApiService.class);


    @Override
    public Observable<TokenBean.Data> clearBook(String access_token, String machid, String book_id, String bookcase_id) {
        return api.getBookClear(access_token, machid, book_id, bookcase_id);
    }
}
