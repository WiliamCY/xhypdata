package com.example.xinhuayipin.mvp.contract;

import com.example.commons.mvp.IBasePresenter;
import com.example.commons.mvp.IBaseView;
import com.example.xinhuayipin.data.book.BookBean;
import com.example.xinhuayipin.data.borrow.BorrowBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;

/**
 * @Author diaomao.
 * @Date on 2019-08-26.
 * @Github https://github.com/javofxu
 * @Dec:
 * @version: ${VERSION}.
 * @Update :
 */
public interface BorrowContract {

    interface BorrowModel{

        Observable<BookBean> getBookCaseInfo(@Field("access_token") String access_token, @Field("bookcase_no") String bookcase_no);

        Observable<BorrowBean> borrow(@Field("access_token") String access_token, @Field("machid") String machid, @Field("doorid") String doorid, @Field("student_id") String student_id);
    }

    interface BorrowView extends IBaseView{

        void successful(List<BookBean.Data> beanList);

        void failure();

        void network_error();
    }

    interface BorrowPresent extends IBasePresenter<BorrowView>{

        void getBorrowData(String token, String mac);
    }
}
