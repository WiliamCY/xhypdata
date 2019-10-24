package com.example.xinhuayipin.app;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

import com.example.xinhuayipin.data.DaoSession;
import com.example.xinhuayipin.data.FingerData;
import com.example.xinhuayipin.data.FingerprintBean;
import com.example.xinhuayipin.data.TokenBean;
import com.example.xinhuayipin.mvp.contract.CommonContract;
import com.example.xinhuayipin.mvp.model.CommonModel;

import java.util.List;

import io.reactivex.Observable;

public class MyService extends Service {

    private static final String TAG = MyService.class.getSimpleName();
    private CommonContract.model model;
    @Override
    public void onCreate() {
        super.onCreate();
        model = new CommonModel();
        Log.i(TAG, "onCreate: ");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        getToken();
        getFingerData();
        return super.onStartCommand(intent, flags, startId);
    }

    @SuppressLint("CheckResult")
    private void getToken() {
//        if (MyApplication.getInstance().firstRun()){
//            Observable<TokenBean.Data> observable = model.token("", "2","");
//            observable.subscribe(data -> {
//                if (!TextUtils.isEmpty(data.getToken())) {
//                    MyApplication.getInstance().getSpUtil().put("token", data.getToken());
//                    Log.i(TAG, "c: 保存成功！");
//                }else {
//                    Log.d(TAG, "getToken: 失败");
//                }
//            });
        MyApplication.getInstance().getSpUtil().put("token","632b667845712a5e27f13227ac554b40");
//        }
    }

    @SuppressLint("CheckResult")
    private void getFingerData(){
        String token = (String) MyApplication.getInstance().getSpUtil().getSharedPreference("token","");
        String machid = (String) MyApplication.getInstance().getSpUtil().getSharedPreference("machid","505019A");
        Observable<FingerData> observable = model.getFingerData(token, machid, "0", "");
        observable.subscribe(fingerData -> {
          if (fingerData.getCode()==0){
              List<FingerData.Data> finger_info = fingerData.getData();
              if (finger_info != null && finger_info.size() > 0) {
                  DaoSession daoSession = MyApplication.getInstance().getDaoSession();
                  daoSession.getFingerprintBeanDao().deleteAll();
                  for (int i = 0; i < finger_info.size(); i++) {
                      FingerprintBean fingerprintBean = new FingerprintBean();
                      fingerprintBean.setId((long) finger_info.get(i).getId());
                      fingerprintBean.setStudent_id((long) finger_info.get(i).getStudent_id());
                      fingerprintBean.setSchool_id(finger_info.get(i).getSchool_id());
                      fingerprintBean.setFingerprint_id(finger_info.get(i).getFingerprint_id());
                      fingerprintBean.setFingerprint_data(finger_info.get(i).getFingerprint_data());
                      fingerprintBean.setFingerprint_pic(finger_info.get(i).getFingerprint_pic());
                      fingerprintBean.setCreate_time(finger_info.get(i).getCreate_time());
                      fingerprintBean.setUpdate_time(finger_info.get(i).getUpdate_time());
                      fingerprintBean.setStatus(finger_info.get(i).getStatus());
                      daoSession.getFingerprintBeanDao().insert(fingerprintBean);
                  }
              }
          }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: service destroy");
    }
}
