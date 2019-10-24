package com.example.xinhuayipin.mvp.model;

import com.example.commons.http.RetrofitClient;
import com.example.xinhuayipin.data.TokenBean;
import com.example.xinhuayipin.data.book.BookBean;
import com.example.xinhuayipin.data.borrow.BorrowBean;
import com.example.xinhuayipin.http.ApiService;
import com.example.xinhuayipin.mvp.contract.BorrowContract;
import com.example.xinhuayipin.mvp.contract.GiveBackContract;
import com.example.xinhuayipin.mvp.contract.GroundingContract;

import io.reactivex.Observable;

/**
 * @Author skygge.
 * @Date on 2019-09-02.
 * @Github https://github.com/javofxu
 * @Dec:
 * @version: ${VERSION}.
 * @Update :
 */
public class GroudingModel implements GroundingContract.model {

    ApiService api = RetrofitClient.getInstance().getApiService(ApiService.class);

    @Override
    public Observable<TokenBean.Data> bindBook(String access_token, String machid,String book_id,String bookcase_id) {
       return api.getBind(access_token,machid,book_id, bookcase_id);
    }


}
