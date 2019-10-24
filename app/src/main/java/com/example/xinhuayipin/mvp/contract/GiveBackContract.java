package com.example.xinhuayipin.mvp.contract;

import com.example.commons.mvp.IBasePresenter;
import com.example.commons.mvp.IBaseView;
import com.example.xinhuayipin.data.record.Lend_history_info;

import java.util.List;

/**
 * @Author skygge.
 * @Date on 2019-09-05.
 * @Github https://github.com/javofxu
 * @Dec:
 * @version: ${VERSION}.
 * @Update :
 */
public interface GiveBackContract {

    interface GiveBackView extends IBaseView{

        void hasGiveBack(List<Lend_history_info> dataList);

        void noGiveBack();

        void netWorkError();
    }

    interface GiveBackPresent extends IBasePresenter<GiveBackView>{
        /**
         * 获取用户预约信息
         */
        void getReserveInfo(String token, int studentID, int type);
    }
}
