package com.example.xinhuayipin.mvp.contract;

import android.widget.ImageView;
import android.widget.RadioGroup;

import com.example.commons.mvp.IBasePresenter;
import com.example.commons.mvp.IBaseView;
import com.example.xinhuayipin.data.fetch.FetchBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;

/**
 * @Author skygge.
 * @Date on 2019-08-13.
 * @Github https://github.com/javofxu
 * @Dec:
 * @version: ${VERSION}.
 * @Update :
 */
public interface MainContract {

    interface MainModel{

        Observable<FetchBean> checkStudentFingerData(@Field("access_token") String access_token, @Field("machid") String machid, @Field("finger_id") String finger_id);

        Observable<FetchBean> checkStudentByStudentID(@Field("access_token") String access_token, @Field("machid") String machid, @Field("student_id") String student_id);

    }

    interface MainView extends IBaseView {

        /**
         * 显示日期
         * @param day
         */
        void showDay(String day);

        /**
         * 显示月份
         * @param month
         */
        void showMonth(String month);

        /**
         * 显示名言名句
         * @param quotes
         */
        void showQuotes(List<String> quotes);

        /**
         * 显示轮播图
         * @param img
         */
        void showPagerImg(List<ImageView> img);

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

    interface MainPresent extends IBasePresenter<MainView>{

        /**
         * 获取当前月份和日期
         */
        void getMonthAndDay();

        /**
         * 获取名言名句
         */
        void getQuotes();

        /**
         * 获取轮播图
         * @param pager 轮播数量
         */
        void getBannerImg(int pager, RadioGroup radioGroup);

        /**
         * 动态设置RadioButton长宽
         * @param isClick
         * @return
         */
        RadioGroup.LayoutParams getParams(boolean isClick);

        /**
         * 用户已经登录时候用学生学号判断有无借阅信息
         */
        void checkStudentByStudentID(String token, String mac, int studentID);

        /**
         * 用户没有登录时候用指纹验证获得指纹ID判断有无借阅信息
         */
        void checkStudentFingerData(String token, String mac,int fingerId);
    }
}
