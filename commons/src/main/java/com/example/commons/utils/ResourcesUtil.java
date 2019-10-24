package com.example.commons.utils;

import com.example.commons.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author skygge.
 * @Date on 2019-08-16.
 * @Github https://github.com/javofxu
 * @Dec:
 * @version: ${VERSION}.
 * @Update :
 */
public class ResourcesUtil {

    /**
     * 获取操作列表背景
     * @return
     */
    public static List<Integer> getBackground(){
        List<Integer> background = new ArrayList<>();
        background.add(R.mipmap.main_slideshow1);
        background.add(R.mipmap.main_slideshow2);
        background.add(R.mipmap.main_slideshow3);
        background.add(R.mipmap.main_slideshow4);
        background.add(R.mipmap.main_slideshow5);
        return background;
    }
}
