package com.example.commons.utils;

import android.content.Context;

/**
 * @Author skygge.
 * @Date on 2019-08-21.
 * @Github https://github.com/javofxu
 * @Dec:
 * @version: ${VERSION}.
 * @Update :
 */
public class DisplayUtil {
    public static int dp2px(Context context, float dpValue) {
        if (context == null) return (int) (dpValue * 1.5f + 0.5f);
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
