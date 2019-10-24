package com.example.xinhuayipin.mvp.presenter;

import com.example.commons.mvp.BasePresenterImpl;
import com.example.xinhuayipin.data.book.BookBean;
import com.example.xinhuayipin.mvp.contract.BorrowContract;
import com.example.xinhuayipin.mvp.model.BorrowModel;

import io.reactivex.Observable;

/**
 * @Author skygge.
 * @Date on 2019-08-26.
 * @Github https://github.com/javofxu
 * @Dec:
 * @version: ${VERSION}.
 * @Update :
 */
public class BorrowPresenter extends BasePresenterImpl<BorrowContract.BorrowView> implements BorrowContract.BorrowPresent {

    private static final String TAG = BorrowPresenter.class.getSimpleName();
    private BorrowContract.BorrowModel model;

    @Override
    public void getBorrowData(String token, String mac) {
        model = new BorrowModel();
        Observable<BookBean> observable = model.getBookCaseInfo(token, mac);
        observable.subscribe(borrowBean -> {
            if (borrowBean.getCode()==0){
                mView.successful(borrowBean.getData());
            }else {
                mView.failure();
            }
        },throwable -> {
            mView.network_error();
        });
    }
}
