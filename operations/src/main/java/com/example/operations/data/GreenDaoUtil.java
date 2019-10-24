package com.example.operations.data;

import android.content.Context;


import org.greenrobot.greendao.database.Database;

/**
 * @Author skygge.
 * @Date on 2019-09-20.
 * @Github https://github.com/javofxu
 * @Dec:
 * @version: ${VERSION}.
 * @Update :
 */
public class GreenDaoUtil {

    private volatile static GreenDaoUtil instance;
    private DaoSession daoSession;
    private DaoMaster.DevOpenHelper helper;

    public static GreenDaoUtil getInstance(){
        if (instance == null){
            synchronized (GreenDaoUtil.class){
                if (instance == null){
                    instance = new GreenDaoUtil();
                }
            }
        }
        return instance;
    }

    /**
     * 初始化GreenDao,直接在Application中进行初始化操作
     */
    public void initGreenDao(Context context) {
        helper = new DaoMaster.DevOpenHelper(context,"device.db");
        Database db = helper.getWritableDb();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession(){
        return daoSession;
    }
}
