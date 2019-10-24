package com.example.xinhuayipin.mvp.contract;

import com.example.commons.data.RequestValue;
import com.example.commons.mvp.IBasePresenter;
import com.example.commons.mvp.IBaseView;

import java.io.File;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Part;

/**
 * @Author skygge.
 * @Date on 2019-08-19.
 * @Github https://github.com/javofxu
 * @Dec:
 * @version: ${VERSION}.
 * @Update :
 */
public interface FingerprintContract{

    interface FingerprintModel{
        Observable<RequestValue> uploadFinger(@Part("access_token") RequestBody access_token, @Part("fingerdata") RequestBody fingerdata, @Part MultipartBody.Part finger_pic,
                                              @Part("student_id") RequestBody student_id, @Part("school_id") RequestBody school_id);
    }

    interface FingerprintView extends IBaseView{

        void openSuccess();

        void openFailed(int number);

        void getFingerSuccess(byte[] fingerData, File img);

        void registeredSuccess();

        void registeredFailed();

        void countdown(int time);
    }

    interface FingerprintPresent extends IBasePresenter<FingerprintView>{

        /**
         * 打开指纹仪
         */
         void openFp();

        /**
         * 指纹扫描
         */
        void initInputFinger();

        /**
         * 上传指纹
         */
        void uploadFinger(String token, String fingerData, File file, String studentId, String schoolId);

        /**
         * 关闭指纹仪
         */
        void closeFP();

        /**
         * 倒计时关闭
         */
        void countdownFinish();

        /**
         * 关闭Handler
         */
        void closeHandler();
    }
}
