package com.example.xinhuayipin.mvp.presenter;

import com.example.commons.mvp.BasePresenterImpl;
import com.example.xinhuayipin.data.TokenBean;
import com.example.xinhuayipin.mvp.contract.GroundingContract;
import com.example.xinhuayipin.mvp.contract.UndercarriageContract;
import com.example.xinhuayipin.mvp.model.GroudingModel;
import com.example.xinhuayipin.mvp.model.UnbercarriageModel;

import io.reactivex.Observable;

public class UnbercarriagePresenter extends BasePresenterImpl<UndercarriageContract.ClearView> implements UndercarriageContract.ClearPresent {
    private static final String TAG = BasePresenterImpl.class.getSimpleName();
    private UndercarriageContract.model model;





    @Override
    public void clearBook(String access_token, String machid, String book_id, String bookcase_id) {
        model = new UnbercarriageModel();
        Observable<TokenBean.Data> observable = model.clearBook(access_token, machid, book_id, bookcase_id);
    }
}
