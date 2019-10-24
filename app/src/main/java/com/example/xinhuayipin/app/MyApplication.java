package com.example.xinhuayipin.app;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.example.commons.utils.ConversionUtil;
import com.example.commons.utils.SpHelperUtil;
import com.example.operations.data.GreenDaoUtil;
import com.example.xinhuayipin.R;
import com.example.xinhuayipin.data.DaoMaster;
import com.example.xinhuayipin.data.DaoSession;
import com.example.xinhuayipin.data.FingerprintBean;

import org.greenrobot.greendao.database.Database;

import java.util.List;

import io.reactivex.plugins.RxJavaPlugins;

/**
 * @Author skygge.
 * @Date on 2019-08-14.
 * @Github https://github.com/javofxu
 * @Dec:
 * @version: ${VERSION}.
 * @Update :
 */
public class MyApplication extends Application {

    private static final String TAG = MyApplication.class.getSimpleName();
    private volatile static MyApplication instance  = null;
    private SpHelperUtil mSpUtil;
    private DaoSession daoSession;
    private DaoMaster.DevOpenHelper helper;
    private byte[][] fingerList;

    public MyApplication() {
    }

    public static MyApplication getInstance(){
        if (instance == null){
            synchronized (MyApplication.class){
                if (instance == null){
                    instance = new MyApplication();
                }
            }
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initGreenDao(this);
        mSpUtil = new SpHelperUtil(this);
        RxJavaPlugins.setErrorHandler(throwable -> getString(R.string.network_error));
    }

    public DaoSession getDaoSession(){
        return daoSession;
    }

    public SpHelperUtil getSpUtil() {
        return mSpUtil;
    }

    /**
     * 获取当前学校Id
     * @return
     */
    public int getSchoolId(){
        int schoolId = (Integer) mSpUtil.getSharedPreference("schoolId",0);
        return schoolId;
    }

    public void saveSchoolId(int schoolId){
        mSpUtil.put("schoolId", schoolId);
    }
    

    /**
     * 获取本地所有指纹数据
     * 第一次进行循环耗时操作
     * @return
     */
    public byte[][] getFingerprintData(){
        List<FingerprintBean> beanList = daoSession.getFingerprintBeanDao().loadAll();
        int fingerNumber = beanList.size();
        Log.i(TAG, "compareFinger: "+fingerNumber);
        if (fingerNumber>0){
            fingerList = new byte[fingerNumber][];
            for (int i = 0; i < fingerNumber; i++) {
                byte[] fingerprint = ConversionUtil.toByteArray(beanList.get(i).getFingerprint_data());
                fingerList[i] = fingerprint;
            }
        }
        return fingerList;
    }

    /**
     * 判断软件是否第一次启动
     * 第一次启动保存本地配置数据
     */
    public boolean firstRun() {
        Boolean first_run = (Boolean) mSpUtil.getSharedPreference("First", true);
        if (first_run){
            mSpUtil.put("First",false);
            return true;
        }
        return false;
    }

    /**
     * 初始化GreenDao,直接在Application中进行初始化操作
     */
    private void initGreenDao(Context context) {
        helper = new DaoMaster.DevOpenHelper(context,"xinhuayipin.db");
        Database db = helper.getWritableDb();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        Log.d(TAG, "initGreenDao: ");
    }


    /**
     * 关闭数据库
     */
    public void closeDataBase() {
        closeHelper();
        closeDaoSession();
    }

    public void closeDaoSession() {
        if (null != daoSession) {
            daoSession.clear();
            daoSession = null;
        }
    }
    public void closeHelper() {
        if (helper != null) {
            helper.close();
            helper = null;
        }
    }
}
