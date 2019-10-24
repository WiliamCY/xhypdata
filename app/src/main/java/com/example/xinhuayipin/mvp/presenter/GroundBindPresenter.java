package com.example.xinhuayipin.mvp.presenter;

import com.example.commons.mvp.BasePresenterImpl;
import com.example.xinhuayipin.data.TokenBean;
import com.example.xinhuayipin.data.book.BookBean;
import com.example.xinhuayipin.data.fetch.FetchBean;
import com.example.xinhuayipin.mvp.contract.GiveBackContract;
import com.example.xinhuayipin.mvp.contract.GroundingContract;
import com.example.xinhuayipin.mvp.model.GroudingModel;

import io.reactivex.Observable;

public class GroundBindPresenter extends BasePresenterImpl<GroundingContract.BindView> implements GroundingContract.BindPresent {
    private static final String TAG = BasePresenterImpl.class.getSimpleName();
    private GroundingContract.model model;


    @Override
    public void bindBook(String access_token, String machid, String book_id, String bookcase_id) {
        model = new GroudingModel();
        Observable<TokenBean.Data> observable = model.bindBook(access_token, machid, book_id, bookcase_id);
        observable.subscribe(fetchBean -> {
//            if (fetchBean.getCode()==0){
//                if (fetchBean.getData().getLend_info().getCount()>0){
//                    mView.hasDidNotReturn();
//                }else {
//                    if (fetchBean.getData().getBook_reserve_info().getCount()>0){
//                        mView.hasReserve();
//                    }else {
//                        mView.noReserve();
//                    }
//                }
//            }else {
//            }
        });

    }


}
