package com.example.xinhuayipin.mvp.contract;

import com.example.commons.mvp.IBasePresenter;
import com.example.commons.mvp.IBaseView;
import com.example.xinhuayipin.data.record.RecordBean;
import com.example.xinhuayipin.data.record.Reserve_info;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;

/**
 * @Author skygge.
 * @Date on 2019-09-05.
 * @Github https://github.com/javofxu
 * @Dec:
 * @version: ${VERSION}.
 * @Update :
 */
public interface WaitFetchContract {


    interface WaitFetchView extends IBaseView{

        void hasFetch(List<Reserve_info> dataList);

        void noFetch();

        void netWorkError();
    }

    interface WaitFetchPresent extends IBasePresenter<WaitFetchView>{
        /**
         * 获取用户预约信息
         */
        void getReserveInfo(String token, int studentID, int type);
    }
}
