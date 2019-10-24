package com.example.xinhuayipin.mvp.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.commons.mvp.BasePresenterImpl;
import com.example.commons.utils.FpUtil;
import com.example.xinhuayipin.app.MyApplication;
import com.example.xinhuayipin.data.DaoSession;
import com.example.xinhuayipin.mvp.contract.VerifyContract;
import com.fgtit.data.Conversions;
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
import winuim.fingerprint.sdk.FpScanner;

/**
 * @Author skygge.
 * @Date on 2019-08-22.
 * @Github https://github.com/javofxu
 * @Dec:
 * @version: ${VERSION}.
 * @Update :
 */
public class VerifyPresenter extends BasePresenterImpl<VerifyContract.VerifyView> implements VerifyContract.VerifyPresent {

    private  final String TAG = VerifyPresenter.class.getSimpleName();
//    /**
//     * 指纹仪打开
//     */
//    private final int MSG_USB_OPEN_DEV = 2000;
//    /**
//     * USB权限
//     */
//    private final int MSG_USB_PERMISSIONS = 1234;
//    /**
//     * 指纹比对成功
//     */
//    private final int FP_COMPARISON_SUCCEED = 4;
//    /**
//     * 指纹比对失败
//     */
//    private final int FP_COMPARISON_FAILED = 5;
   private  fpdevice fpdev = new fpdevice();
    private  boolean isopening = false;
    private String sDirectory = "";
    public byte mRefList[][] = new byte[2048][512];
    public int mRefCount = 0;
    private Context mContext;
    //    private FpScanner mScanner;
    private FpUtil mFpUtil;
    private DaoSession daoSession;
    private  boolean isworking = false;
    private boolean isContinuous = false;
    private Timer mTmContinu = null;
    private TimerTask mTsContinu = null;
    private Timer mTimer = null;
    private TimerTask mTimerTask = null;
    private int mWorkmode = 0;
    private int open_number = 0;
    private int compare_fail = 0;
    private int finger_number = 0;
    private byte bmpdata[] = new byte[74806];
    private int bmpsize[] = new int[1];
    private byte fpdata[] = new byte[512];
    private int fpsize[] = new int[1];
    private byte isodata[] = new byte[512];
    private int enrolCount = 0;

    public VerifyPresenter(Context mContext) {
        this.mContext = mContext;
        FPMatch.getInstance().InitMatch(0, "http://www.fgtit.com/");
        fpdev.SetInstance(mContext);
        CreateDirectory();
        ReadDataFromFile();
    }

    @Override
    public void openFp() {
        if(isopening)return;

        switch (fpdev.OpenDevice()) {
            case 0:
                Log.i(TAG, "handleMessage: 指纹仪打开成功!");
                isopening = true;
                mView.openSuccess();
                if (isopening) {
                    if (isworking) return;
                    mWorkmode = 2;
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
    public void compareFinger(byte[][] fingerList) {
//                mFpUtil.CompareFinger(fingerList, mHandler);
    }

//    @Override
//    public void compareFinger(byte[][] fingerList) {
//        finger_number = fingerList.length;
//        mFpUtil.CompareFinger(fingerList, mHandler);
//    }
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
    public void closeFP() {
      if(isopening){
          fpdev.CloseDevice();
          isopening = false;
          isworking = false;
          Log.i(TAG, "handleMessage: 指纹仪已经关闭");
//          open_number++;
//          mView.openFailed(open_number);

      }
    }

    @Override
    public void saveStudentIdAndGetFingerId(int num) {
        daoSession = MyApplication.getInstance().getDaoSession();
        long studentId = daoSession.getFingerprintBeanDao().loadAll().subList(num, num + 1).get(0).getStudent_id();
        long schoolId = daoSession.getFingerprintBeanDao().loadAll().subList(num, num + 1).get(0).getSchool_id();
        int fingerId = daoSession.getFingerprintBeanDao().loadAll().subList(num, num + 1).get(0).getFingerprint_id();
        MyApplication.getInstance().saveSchoolId((int) schoolId);
        mView.getFingerIdAndStudentId(fingerId, (int) studentId);
    }

//    @SuppressLint("HandlerLeak")
//    private Handler mHandler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            Log.i(TAG, "handleMessage: "+msg.what);
//            switch (msg.what){
//                case MSG_USB_PERMISSIONS:
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
//                case FP_COMPARISON_SUCCEED:
//                    int parse = (int) msg.obj;
//                    mView.compareSuccess(parse);
//                    break;
//                case FP_COMPARISON_FAILED:
//                    Log.i(TAG, "handleMessage: "+msg.obj.toString());
//                    compare_fail++;
//                    mView.compareFailed();
//                    if (compare_fail%finger_number==0){
////                        mView.compareFailed();
//                    }
//                    break;
//            }
//        }
//    };
//}
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
                                    }
                                    break;
                                    case 2: {//match
                                      //  "Enrol OK:" + String.valueOf("Generate Template OK")
                                       // tvStatus.setText("Generate Template OK");
                                        fpdev.GetTemplateByGen(fpdata, fpsize);
                                        // ConverTemplate(fpdata, fpsize[0], isodata);
                                        boolean bok = false;
                                        for (int i = 0; i <= mRefCount; i++) {
                                             int score = MatchTemplate(mRefList[i], 378, fpdata, 378);
                                            if (score > 96) {
                                               // tvStatus.setText("Match Return ID:" + String.valueOf(i + 1) + "   Scope:" + String.valueOf(score));
                                                Log.i(TAG, "Match Return ID:" + String.valueOf(i + 1) + "   Scope:" + String.valueOf(score));
                                                bok = true;
                                                mView.compareSuccess(i+1);
                                                break;
                                            }else {
                                                mView.compareFailed();
                                                Log.i(TAG, "Match Return ID:" + String.valueOf(i + 1) + "   Scope:" + String.valueOf(score));
                                            }
                                        }

                                        //if (!bok) tvStatus.setText("No Match");
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
    public int MatchTemplate(byte[] reftp, int refsize, byte[] mattp, int matsize) {
        byte[] refbuf = new byte[512];
        byte[] matbuf = new byte[512];
        switch (Conversions.getInstance().GetDataType(reftp)) {
            case 1: {    //STD
                System.arraycopy(reftp, 0, refbuf, 0, refsize);
            }
            break;
            case 2: {    //ISO 1
                Conversions.getInstance().IsoToStd(1, reftp, refbuf);
            }
            break;
            case 3: {    //ISO 2
                Conversions.getInstance().IsoToStd(2, reftp, refbuf);
            }
            break;
        }
        switch (Conversions.getInstance().GetDataType(mattp)) {
            case 1: {    //STD
                System.arraycopy(mattp, 0, matbuf, 0, matsize);
            }
            break;
            case 2: {    //ISO 1
                Conversions.getInstance().IsoToStd(1, mattp, matbuf);
            }
            break;
            case 3: {    //ISO 2
                Conversions.getInstance().IsoToStd(2, mattp, matbuf);
            }
            break;
        }
        int mret = FPMatch.getInstance().MatchTemplate(refbuf, matbuf);
        return mret;
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


    public void Resume() {
        fpdev.CloseDevice();
        isopening = false;
        switch (fpdev.OpenDevice()) {
            case 0:
                isopening = true;

                break;
            case -1:

                break;
            case -2:
                break;
            case -3:
                break;
        }

    }

    public void keyDown() {
        WriteDataToFile();
        fpdev.CloseDevice();
        Log.i(TAG, "Close");
        isopening = false;
    }


}