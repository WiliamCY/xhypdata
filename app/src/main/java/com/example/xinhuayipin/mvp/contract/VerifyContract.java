package com.example.xinhuayipin.mvp.contract;

import android.graphics.Bitmap;

import com.example.commons.mvp.IBasePresenter;
import com.example.commons.mvp.IBaseView;

/**
 * @Author skygge.
 * @Date on 2019-08-22.
 * @Github https://github.com/javofxu
 * @Dec:
 * @version: ${VERSION}.
 * @Update :
 */
public interface VerifyContract {

    interface VerifyView extends IBaseView{

        void openSuccess();

        void openFailed(int number);

        void compareSuccess(int parse);

        void compareFailed();

        void getFingerIdAndStudentId(int fingerId, int studentId);


    }

    interface VerifyPresent extends IBasePresenter<VerifyView>{
        /**
         * 打开指纹仪
         */
        void openFp();

        /**
         * 指纹比对
         */
        void compareFinger(byte[][] fingerList);

        /**
         * 关闭指纹仪
         */
        void closeFP();

        /**
         * 指纹比对成功后保存对应的学号和得到指纹Id
         */
        void saveStudentIdAndGetFingerId(int num);
    }
    
}
