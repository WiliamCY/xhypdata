package com.example.xinhuayipin.mvp.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.commons.data.RequestValue;
import com.example.commons.mvp.BasePresenterImpl;
import com.example.commons.utils.FpUtil;
import com.example.xinhuayipin.mvp.contract.FingerprintContract;
import com.example.xinhuayipin.mvp.model.FingerprintModel;
import com.fgtit.data.wsq;
import com.fgtit.fpcore.FPMatch;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Timer;
import java.util.TimerTask;

import fgtit.fpengine.constants;
import fgtit.fpengine.fpdevice;
import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import winuim.fingerprint.sdk.FpScanner;

/**
 * @Author skygge.
 * @Date on 2019-08-19.
 * @Github https://github.com/javofxu
 * @Dec:
 * @version: ${VERSION}.
 * @Update :
 */
public class FingerprintPresenter extends BasePresenterImpl<FingerprintContract.FingerprintView> implements FingerprintContract.FingerprintPresent {

    private static final String TAG = FingerprintPresenter.class.getSimpleName();
//    /**
//     * 指纹仪打开
//     */
//    private final int MSG_USB_OPEN_DEV = 2000;
//    /**
//     * USB权限
//     */
//    private final int MSG_USB_PERMISSIONS = 1234;
//    /**
//     * 录入成功
//     */
//    private final int FP_CREATE_SUCCEED = 3;
//    /**
//     * 图像显示成功
//     */
//    private final int PICTURE_SHOW_SUCCEED = 6;
//    /**
//     * 图像显示成功
//     */
//    private final int COUNTDOWN_START = 0x11;
//
//    private int open_number = 0;
//    private int countdown = 3;
    private Context mContext;
//    private FpScanner mScanner;
//    private FpUtil mFpUtil;
//    private byte[] mFpTemp;
private static boolean isworking = false;
    private int open_number = 0;
    private static fpdevice fpdev = new fpdevice();
    private static boolean isopening = false;
    private String sDirectory = "";
    public byte mRefList[][] = new byte[2048][512];
    private int mWorkmode = 0;
    public int mRefCount = 0;
    private FingerprintContract.FingerprintModel model;
    private Timer mTmContinu = null;
    private TimerTask mTsContinu = null;
    private Timer mTimer = null;
    private TimerTask mTimerTask = null;
    private byte bmpdata[] = new byte[74806];
    private int bmpsize[] = new int[1];
    private byte fpdata[] = new byte[512];
    private int fpsize[] = new int[1];
    private boolean isContinuous = false;
    private int enrolCount = 0;

    public FingerprintPresenter(Context mContext) {
        this.mContext = mContext;
        model = new FingerprintModel();
        FPMatch.getInstance().InitMatch(0, "http://www.fgtit.com/");
        fpdev.SetInstance(mContext);
        CreateDirectory();
        ReadDataFromFile();
    }

    @Override
    public void openFp() {
//        if (mFpUtil == null) mFpUtil = new FpUtil();
//        try{
//            mScanner = mFpUtil.startFP(mContext, mHandler);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        if(isopening)return;

        switch (fpdev.OpenDevice()) {
            case 0:
                Log.i(TAG, "handleMessage: 指纹仪打开成功!");
                mView.openSuccess();
                isopening = true;
                if (isopening) {
                    if (isworking) return;
                    mWorkmode = 1;
                    enrolCount = 1;
                    TimerStart();
                    fpdev.GenerateTemplate();
                    isworking = true;
                }


                break;
            case -1:
                Log.i(TAG, "handleMessage: 指纹仪连接失败");
                break;
            case -2:
                Log.i(TAG, "handleMessage: 指纹仪发生异常");
                break;
            case -3:
                Log.i(TAG, "handleMessage: 指纹仪打开失败!");
                mView.openFailed(3);
                break;
        }
    }

    @Override
    public void initInputFinger() {
//        if (mFpUtil!=null) mFpUtil.InputFinger(mContext, mHandler);
    }

    @Override
    public void uploadFinger(String token, String fingerData, File file, String studentId, String schoolId) {
        RequestBody access_token = RequestBody.create(MediaType.parse("multipart/form-data"), token);
        RequestBody fingerprint = RequestBody.create(MediaType.parse("multipart/form-data"), fingerData);
//        MultipartBody.Part filePart = MultipartBody.Part.createFormData("finger_pic", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
        RequestBody student_Id = RequestBody.create(MediaType.parse("multipart/form-data"), studentId);
        RequestBody school_Id = RequestBody.create(MediaType.parse("multipart/form-data"), schoolId);

        Observable<RequestValue> observable = model.uploadFinger(access_token, fingerprint, null, student_Id, school_Id);
        observable.subscribe(requestValue -> {
            if (requestValue.getCode()==0){
                mView.registeredSuccess();
            }else {
                mView.registeredFailed();
            }
        });
    }

    @Override
    public void closeFP() {
        if(isopening){
            fpdev.CloseDevice();
            isopening = false;
            isworking = false;
            Log.i(TAG, "handleMessage: 指纹仪已经关闭");

        }
    }
    public void CreateDirectory() {
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            sDirectory = Environment.getExternalStorageDirectory() + "/FingerprintReader";
            File destDir = new File(sDirectory);
            if (!destDir.exists()) {
                destDir.mkdirs();
            }
        }
    }

    public void ReadDataFromFile() {
        File f = new File(sDirectory + "/fingerprint.dat");
        if (f.exists()) {
        }
        try {
            RandomAccessFile randomFile = new RandomAccessFile(sDirectory + "/fingerprint.dat", "rw");
            long fileLength = randomFile.length();
            mRefCount = (int) (fileLength / 512);
            if (mRefCount > 2000) mRefCount = 2000;
            for (int i = 0; i < mRefCount; i++) {
                randomFile.read(mRefList[i]);
            }
            randomFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void countdownFinish() {
//        mHandler.sendEmptyMessage(COUNTDOWN_START);
    }

    @Override
    public void closeHandler() {
//        mHandler.removeMessages(COUNTDOWN_START);
//        mHandler = null;
    }

//    @SuppressLint("HandlerLeak")
//    private Handler mHandler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            Log.i(TAG, "handleMessage: "+msg.what);
//            switch (msg.what){
//                case MSG_USB_PERMISSIONS:
//                    if (msg.arg1 == 1) {// 获得 权限
//                        Log.i(TAG, "handleMessage: 获取权限成功！");
//                    } else {
//                        Log.i(TAG, "handleMessage: 获取权限失败！");
//                        break;
//                    }
//                    break;
//                case MSG_USB_OPEN_DEV:
//                    if (!mScanner.Open()) {
//                        open_number++;
//                        mView.openFailed(open_number);
//                        Log.i(TAG, "handleMessage: 指纹仪打开失败!");
//                    } else {
//                        mView.openSuccess();
//                        Log.i(TAG,  "handleMessage: 指纹仪打开成功!");
//                    }
//                    break;
//                case PICTURE_SHOW_SUCCEED:
//                    Log.i(TAG, "handleMessage: 获取指纹图像成功");
//                    break;
//                case FP_CREATE_SUCCEED:
//                    mFpTemp = (byte[]) msg.obj;
//                    mView.getFingerSuccess(mFpTemp, null);
//                    Log.i(TAG, "handleMessage: 录入指纹成功");
//                    break;
//                case COUNTDOWN_START:
//                    mView.countdown(countdown);
//                    countdown--;
//                    break;
//            }
//        }
//    };
private Handler  handler = new Handler() {
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case 1: {
                int work = fpdev.GetWorkMsg();
                int ret = fpdev.GetRetMsg();
                switch (work) {
                    case constants.FPM_DEVICE:
                        Log.i(TAG, "Please Open Device");
                        break;
                    case constants.FPM_PLACE:
                        Log.i(TAG, "Please Finger");
                        break;
                    case constants.FPM_LIFT:
                        Log.i(TAG, "Lift Finger");
                        break;
                    case constants.FPM_GENCHAR: {
                        TimerStop();
                        isworking = false;
                        if (ret == 1) {
                            switch (mWorkmode) {
                                case 1: {//enrol
                                    //fpdev.GetBmpImage(bmpdata,bmpsize);
                                    Bitmap bm1 = BitmapFactory.decodeByteArray(bmpdata, 0, 74806);
                                    //PNG
                                    SavePngFile(bm1, String.valueOf(mRefCount + 1) + ".png");
                                    //WSQ
                                    byte[] inpdata = new byte[73728];
                                    int inpsize = 73728;
                                    System.arraycopy(bmpdata, 1078, inpdata, 0, inpsize);
                                    SaveWsqFile(inpdata, inpsize, String.valueOf(mRefCount + 1) + ".wsq");

                                    // tvStatus.setText("Enrol Template OK");
                                    fpdev.GetTemplateByGen(fpdata, fpsize);
                                    // ConverTemplate(fpdata, fpsize[0], isodata);
                                    if (mRefCount < 2048) {
                                        System.arraycopy(fpdata, 0, mRefList[mRefCount], 0, 512);
                                        mRefCount++;
                                        Log.i(TAG, "Enrol OK:" + String.valueOf(mRefCount));
                                        //  tvStatus.setText("Enrol OK:" + String.valueOf(mRefCount));
                                        WriteDataToFile();
                                    }
                                    mView.getFingerSuccess(fpdata, null);
                                }
                                break;

                            }
                        } else {
                            // tvStatus.setText("Fail");
                        }
                    }
                    if (isContinuous) {
                        NextMatch();
                    }
                    break;
                    case constants.FPM_NEWIMAGE: {
                        fpdev.GetBmpImage(bmpdata, bmpsize);
                        Bitmap bm1 = BitmapFactory.decodeByteArray(bmpdata, 0, 74806);
//                            ivImage.setImageBitmap(bm1);

                    }
                    break;
                    case constants.FPM_TIMEOUT:
                        //  tvStatus.setText("Time Out");
                        Log.i(TAG, "Time Out");
                        isworking = false;
                        break;
                }
            }
            break;
        }
        super.handleMessage(msg);
    }
};
    public void TimerStop() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
            mTimerTask.cancel();
            mTimerTask = null;
        }
    }
    public void SaveWsqFile(byte[] rawdata, int rawsize, String filename) {
        byte[] outdata = new byte[73728];
        int[] outsize = new int[1];
        wsq.getInstance().RawToWsq(rawdata, rawsize, 256, 288, outdata, outsize, 2.833755f);
        try {
            File fs = new File(sDirectory + "/" + filename);
            if (fs.exists()) {
                fs.delete();
            }
            new File(sDirectory + "/" + filename);
            RandomAccessFile randomFile = new RandomAccessFile(sDirectory + "/" + filename, "rw");
            long fileLength = randomFile.length();
            randomFile.seek(fileLength);
            randomFile.write(outdata, 0, outsize[0]);
            randomFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void SavePngFile(Bitmap bmp, String filename) {
        File f = new File(sDirectory + "/" + filename);
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
        } catch (IOException e) {
        }
    }
    public void WriteDataToFile() {
        File f = new File(sDirectory + "/fingerprint.dat");
        if (f.exists()) {
            f.delete();
        }
        new File(sDirectory + "/fingerprint.dat");
        try {
            RandomAccessFile randomFile = new RandomAccessFile(sDirectory + "/fingerprint.dat", "rw");
            for (int i = 0; i < mRefCount; i++) {
                randomFile.write(mRefList[i]);
            }
            randomFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void NextMatch() {
        if (mTmContinu == null) {
            mTmContinu = new Timer();
        }
        if (mTsContinu == null) {
            mTsContinu = new TimerTask() {
                @Override
                public void run() {
                    if (isopening) {
                        if (isworking) return;
                        mWorkmode = 2;
                        TimerStart();
                        fpdev.GenerateTemplate();
                        isworking = true;
                    }
                    if (mTmContinu != null) {
                        mTmContinu.cancel();
                        mTmContinu = null;
                        mTsContinu.cancel();
                        mTsContinu = null;
                    }
                }
            };
        }
        if (mTmContinu != null && mTsContinu != null) mTmContinu.schedule(mTsContinu, 1000, 1000);

    }
    public void TimerStart() {
        if (mTimer == null) {
            mTimer = new Timer();
        }
        if (mTimerTask == null) {
            mTimerTask = new TimerTask() {
                @Override
                public void run() {
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);
                }
            };
        }
        if (mTimer != null && mTimerTask != null) mTimer.schedule(mTimerTask, 200, 200);
    }

}
