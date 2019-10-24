package com.example.xinhuayipin.mvp.contract;

import com.example.commons.mvp.IBasePresenter;
import com.example.commons.mvp.IBaseView;
import com.example.xinhuayipin.data.record.Lend_info;

import java.util.List;

/**
 * @Author skygge.
 * @Date on 2019-09-05.
 * @Github https://github.com/javofxu
 * @Dec:
 * @version: ${VERSION}.
 * @Update :
 */
public interface WaitRepayContract {

    interface WaitRepayView extends IBaseView{

        void hasRepay(List<Lend_info> dataList);

        void noRepay();

        void netWorkError();
    }

    interface WaitRepayPresent extends IBasePresenter<WaitRepayView>{
        /**
         * 获取用户预约信息
         */
        void getReserveInfo(String token, int studentID, int type);
    }
}
