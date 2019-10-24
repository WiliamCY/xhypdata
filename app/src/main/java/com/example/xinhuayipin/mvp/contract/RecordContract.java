package com.example.xinhuayipin.mvp.contract;

import com.example.commons.mvp.IBasePresenter;
import com.example.commons.mvp.IBaseView;
import com.example.xinhuayipin.data.fetch.FetchBean;
import com.example.xinhuayipin.data.record.RecordBean;

import io.reactivex.Observable;
import retrofit2.http.Field;

/**
 * @Author skygge.
 * @Date on 2019-09-11.
 * @Github https://github.com/javofxu
 * @Dec:
 * @version: ${VERSION}.
 * @Update :
 */
public interface RecordContract {

    interface RecordModel {

        Observable<RecordBean> lendInfo(@Field("access_token") String access_token, @Field("student_id") String student_id, @Field("type") String type);

        Observable<FetchBean> checkStudentByStudentID(@Field("access_token") String access_token, @Field("machid") String machid, @Field("student_id") String student_id);

    }

    interface RecordView extends IBaseView{

        /**
         * 有未归还书籍
         */
        void hasDidNotReturn();

        /**
         * 有预约图书
         */
        void hasReserve();

        /**
         * 无预约图书
         */
        void noReserve();

    }

    interface RecordPresent extends IBasePresenter<RecordView>{

        /**
         * 用户已经登录时候用学生学号判断有无借阅信息
         */
        void checkStudentByStudentID(String token, String mac, int studentID);
    }
}
