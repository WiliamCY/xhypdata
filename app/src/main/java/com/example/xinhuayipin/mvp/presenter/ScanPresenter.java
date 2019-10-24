package com.example.xinhuayipin.mvp.presenter;

import com.example.commons.mvp.BasePresenterImpl;
import com.example.xinhuayipin.data.ScanBean;
import com.example.xinhuayipin.mvp.contract.ScanContract;
import com.example.xinhuayipin.mvp.model.ScanModel;

import io.reactivex.Observable;

/**
 * @Author skygge.
 * @Date on 2019-08-28.
 * @Github https://github.com/javofxu
 * @Dec:
 * @version: ${VERSION}.
 * @Update :
 */
public class ScanPresenter extends BasePresenterImpl<ScanContract.ScanView> implements ScanContract.ScanPresent {

    private ScanContract.ScanModel model;

    @Override
    public void getBookByIsbn(String access_token, String isbn, String machid, String student_id) {
        model = new ScanModel();
        Observable<ScanBean> observable = model.getBookByIsbn(access_token, isbn, machid, student_id);
        observable.subscribe(scanBean -> {
            if (scanBean.getCode() == 0){
                if (scanBean.getData().getLend_bookcase_no().equals(machid)){
                    mView.getBookId(scanBean.getData());
                }else {
                    mView.bookOutOfCase();
                }
            }else {
                mView.scanBookError(scanBean.getMsg());
            }
        }, throwable -> mView.netWorkError());
    }
}
