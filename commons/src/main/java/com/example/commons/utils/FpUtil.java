package com.example.commons.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import winuim.fingerprint.sdk.FpScanner;

/**
 * @Author skygge.
 * @Date on 2019-08-19.
 * @Github https://github.com/javofxu
 * @Dec: 指纹扫描
 * @version: ${VERSION}.
 * @Update :
 */
public class FpUtil {

    private FpScanner mScanner;
    /**
     * Raw转Bmp失败
     */
    private final static int RAW_CHANGE_BMP_FAIL = 0;
    /**
     * 提取指纹特征失败
     */
    private final static int TAKE_FP_FEATURE_FAIL = 1;

    /**
     * 生成模板出错
     */
    private final static int CREATE_MODEL_FAIL = 2;
    /**
     * 录入成功
     */
    private final static int FP_CREATE_SUCCEED = 3;
    /**
     * 指纹对比成功
     */
    private final static int FP_VERIFY_SUCCEED = 4;
    /**
     * 指纹对比失败
     */
    private final static int FP_VERIFY_FAIL = 5;
    /**
     * 获取图像成功
     */
    private final static int PICTURE_SHOW_SUCCEED = 6;
    /**
     * 指纹图案
     */
    private byte[] fpBmpPic;
    /**
     * 指纹特征
     */
    private byte[] fpFeature;

    /**
     * 指纹模板
     */
    private byte[] mFingerTemplate;


    /**
     * 打开指纹仪
     * @param context
     * @param handler
     * @return
     */
    public FpScanner startFP(Context context, Handler handler) {
        if (mScanner == null) {
            mScanner = FpScanner.getInstance();
            mScanner.Init(context, handler, "com.example.xinhuayipin");
        }

        if (!mScanner.getRequestingPermission()) {
            Message Msg = handler.obtainMessage();
            Msg.what = 2000;
            handler.sendMessageDelayed(Msg, 500);
        }
        return mScanner;
    }

    /**
     * 关闭指纹仪
     */
    public void stop() {
        if (mScanner != null) {
            mScanner.Close();
        }
    }

    /**
     * 录入指纹
     * @param handler
     */
    public void InputFinger(Context context, Handler handler) {
        initFp(context, handler);
        byte[] captureRaw;
        synchronized (this) {
            captureRaw = mScanner.getCaptureRaw();
        }
        if (captureRaw != null) {//获取到了指纹的图片
            init(captureRaw, handler);
        }
    }

    /**
     * 比对指纹
     * @param mFpTemp
     * @param m
     */
    public void CompareFinger(byte[][] mFpTemp, Handler m) {
        byte[] captureRaw = mScanner.getCaptureRaw();//获取指纹的图片
        if (captureRaw != null) {//获取到了指纹的图片
            compare(mFpTemp, captureRaw, m);
        }
    }

    private void initFp(Context context, Handler handler) {
        if (mScanner == null) {
            mScanner = FpScanner.getInstance();
            mScanner.Init(context, handler, "com.example.xinhuayipin");
        }
    }

    private void init(byte[] captureRaw, Handler handler) {
        synchronized (this) {
            fpBmpPic = mScanner.toBitmapBuf(captureRaw, true);
            if (fpBmpPic == null) {
                sendMesToUiByHandle(handler, RAW_CHANGE_BMP_FAIL, "Raw转Bmp失败！" + FomatErrorMsg(mScanner.getLastError()));
            } else {
                sendMesToUiByHandle(handler, PICTURE_SHOW_SUCCEED, fpBmpPic);
            }
            fpFeature = mScanner.getFeature(captureRaw);
            if (fpFeature == null || fpFeature.length == 0) {
                sendMesToUiByHandle(handler, TAKE_FP_FEATURE_FAIL, "录入失败，提取特征出错！" + FomatErrorMsg(mScanner.getLastError()));
            } else {
                mFingerTemplate = mScanner.getTemplate(fpFeature, fpFeature, fpFeature);

                if (mFingerTemplate == null) {
                    sendMesToUiByHandle(handler, CREATE_MODEL_FAIL, "录入失败，生成模板出错！" + FomatErrorMsg(mScanner.getLastError()));
                } else {
                    sendMesToUiByHandle(handler, FP_CREATE_SUCCEED, mFingerTemplate);
                }
            }
        }
    }

    private void compare(byte[][] tem, byte[] captureRaw, Handler handler) {
        synchronized (this) {
            // 显示指纹图像
            fpBmpPic = mScanner.toBitmapBuf(captureRaw, true);
            if (fpBmpPic == null) {
                sendMesToUiByHandle(handler, RAW_CHANGE_BMP_FAIL, "Raw转Bmp失败！" + FomatErrorMsg(mScanner.getLastError()));
            } else {
                sendMesToUiByHandle(handler, PICTURE_SHOW_SUCCEED, fpBmpPic);
            }

            fpFeature = mScanner.getFeature(captureRaw);

            if (fpFeature == null || fpFeature.length == 0) {
                sendMesToUiByHandle(handler, TAKE_FP_FEATURE_FAIL, "录入失败，提取特征出错！" + FomatErrorMsg(mScanner.getLastError()));
            } else {
                for (int i = 0; i < tem.length; i++) {
                    int nScore = mScanner.getVerify(fpFeature, tem[i]);
                    if (nScore >= 100) {
                        sendMesToUiByHandle(handler, FP_VERIFY_SUCCEED, i);
                        return;
                    }
                }
                sendMesToUiByHandle(handler, FP_VERIFY_FAIL, "比对失败！" + FomatErrorMsg(mScanner.getLastError()));
            }
        }
    }

    /**
     * 通过handler 返回指纹仪返回的数据
     *
     * @param handler
     * @param tip     需要返回的信息 标识
     */

    private void sendMesToUiByHandle(Handler handler, int tip, Object obj) {
        Message message = handler.obtainMessage();
        message.what = tip;
        message.obj = obj;
        handler.sendMessage(message);
    }

    private String FomatErrorMsg(int nErrorCode) {
        if (nErrorCode == FpScanner.ERROR_SUCCESS)
            return "操作成功";
        if (nErrorCode == FpScanner.ERROR_INVALID_PARAM)
            return "错误码: " + nErrorCode + " 参数错误";
        if (nErrorCode == FpScanner.ERROR_NO_PERMISSION)
            return "错误码: " + nErrorCode + " 没有权限操作";
        if (nErrorCode == FpScanner.ERROR_DEV_NOT_FOUND)
            return "错误码: " + nErrorCode + " 未找到设备";
        if (nErrorCode == FpScanner.ERROR_DEV_NOT_OPEN)
            return "错误码: " + nErrorCode + " 设备未打开";
        if (nErrorCode == FpScanner.ERROR_DEV_HAS_OPEN)
            return "错误码: " + nErrorCode + " 设备已打开";
        if (nErrorCode == FpScanner.ERROR_DEV_INVALID)
            return "错误码: " + nErrorCode + " 无效或非法设备";
        if (nErrorCode == FpScanner.ERROR_DEV_ERROR)
            return "错误码: " + nErrorCode + " 设备错误(一般为通信错误)";
        if (nErrorCode == FpScanner.ERROR_DEV_NO_FINGER)
            return "错误码: " + nErrorCode + " 没有手指图像";
        if (nErrorCode == FpScanner.ERROR_BMP_FORMAT_ERROR)
            return "错误码: " + nErrorCode + " BMP图像格式错误";
        if (nErrorCode == FpScanner.ERROR_IMAGE_INVALID)
            return "错误码: " + nErrorCode + " 无效指纹图像（可能图像不是指纹，无法提取到指纹特征）";
        if (nErrorCode == FpScanner.ERROR_UNKNOWN)
            return "错误码: " + nErrorCode + " 未知错误";

        return "异常错误";
    }
}
