package com.example.commons.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Author skygge.
 * @Date on 2019-08-23.
 * @Github https://github.com/javofxu
 * @Dec:
 * @version: ${VERSION}.
 * @Update :
 */
public class FileUtil {

    /**
     * bitmap保存为file
     */
    public static File bitmapToFile(Bitmap bitmap) throws IOException {
        String filePath = Environment.getExternalStorageDirectory() + "/finger.png";
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        if (bitmap != null) {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
            bitmap.compress(Bitmap.CompressFormat.PNG, 50, bos);
            bos.flush();
            bos.close();
        }
        return file;
    }

    /**
     * @param b
     * @param context
     * @param name
     * @return
     */

    public static boolean writeFile(byte[] b, Context context, String name) {
        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(name, Context.MODE_PRIVATE);
            fos.write(b);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 读取文件
     * @param context
     * @param name    文件名
     * @return
     */

    public static byte[] readFile(Context context, String name) {
        FileInputStream fis = null;
        byte[] buff = null;
        try {
            fis = context.openFileInput(name);
            int len = fis.available();
            buff = new byte[len];
            fis.read(buff);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return buff;
    }
}
