package com.example.xinhuayipin.mvp.model;

import com.example.commons.http.RetrofitClient;
import com.example.xinhuayipin.data.book.BookBean;
import com.example.xinhuayipin.data.borrow.BorrowBean;
import com.example.xinhuayipin.http.ApiService;
import com.example.xinhuayipin.mvp.contract.BorrowContract;

import io.reactivex.Observable;

/**
 * @Author skygge.
 * @Date on 2019-09-02.
 * @Github https://github.com/javofxu
 * @Dec:
 * @version: ${VERSION}.
 * @Update :
 */
public class BorrowModel implements BorrowContract.BorrowModel {

    ApiService api = RetrofitClient.getInstance().getApiService(ApiService.class);

    @Override
    public Observable<BookBean> getBookCaseInfo(String access_token, String bookcase_no) {
        return api.getBookCaseInfo(access_token, bookcase_no).map(bookBean -> bookBean).compose(RetrofitClient.getInstance().schedulersTransformer);
    }

    @Override
    public Observable<BorrowBean> borrow(String access_token, String machid, String doorid, String student_id) {
        return api.borrow(access_token, machid, doorid, student_id).map(borrowBean -> borrowBean).compose(RetrofitClient.getInstance().schedulersTransformer);
    }
}
