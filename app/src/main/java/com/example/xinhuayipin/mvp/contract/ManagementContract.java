package com.example.xinhuayipin.mvp.contract;

import com.example.commons.mvp.IBasePresenter;
import com.example.commons.mvp.IBaseView;
import com.example.xinhuayipin.data.FingerprintBean;

import java.util.List;

/**
 * @Author skygge.
 * @Date on 2019-08-21.
 * @Github https://github.com/javofxu
 * @Dec:
 * @version: ${VERSION}.
 * @Update :
 */
public interface ManagementContract {

    interface ManagementView extends IBaseView{
        /**
         * 显示指纹数据
         */
        void setFingerprintList(List<FingerprintBean> fingerprintList);

        /**
         * 无指纹数据
         */
        void noFingerprintData();
    }

    interface ManagementPresent extends IBasePresenter<ManagementView>{

        /**
         * 获取指纹
         * @param userId
         */
        void getFingerprint(String userId);
    }
}
