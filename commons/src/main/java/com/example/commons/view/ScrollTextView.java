package com.example.commons.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.example.commons.R;

import java.util.List;

/**
 * @Author skygge.
 * @Date on 2019-08-15.
 * @Github https://github.com/javofxu
 * @Dec:
 * @version: ${VERSION}.
 * @Update :
 */
public class ScrollTextView extends LinearLayout {

    private CustomTextView mBannerTV1;
    private CustomTextView mBannerTV2;
    private LinearLayout linear1;
    private LinearLayout linear2;
    private List<String> list;
    private int position = 0;
    private Handler handler;
    private Runnable runnable;
    private boolean hasPostRunnable = false;

    public ScrollTextView(Context context) {
        this(context, null);
    }

    public ScrollTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View view = LayoutInflater.from(context).inflate(R.layout.layout_scroll_text, this);
        mBannerTV1 = view.findViewById(R.id.tv_banner1);
        mBannerTV2 = view.findViewById(R.id.tv_banner2);
        linear1 = view.findViewById(R.id.scroll_1);
        linear2 = view.findViewById(R.id.scroll_2);
        handler = new Handler();
        runnable = () -> {
            if (position == list.size() - 1) {
                position = 0;
            }
            mBannerTV1.setText(list.get(position++));
            mBannerTV2.setText(list.get(position));

            ObjectAnimator.ofFloat(linear1, "translationY", 50, 0).setDuration(500).start();

            ObjectAnimator.ofFloat(linear2, "translationY", 50, 0).setDuration(1000).start();

            handler.postDelayed(runnable, 5000);
        };
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
        //处理最后一条数据切换到第一条数据 太快的问题
        if (list.size() > 1) {
            list.add(list.get(0));
        }
    }

    public void startScroll() {
        mBannerTV1.setText(list.get(0));
        mBannerTV2.setText(list.get(1));
        if (list.size() > 1) {
            if(!hasPostRunnable) {
                hasPostRunnable = true;
                //处理第一次进入 第一条数据切换第二条 太快的问题
                handler.postDelayed(runnable,5000);
            }
        } else {
            //只有一条数据不进行滚动
            hasPostRunnable = false;
        }
    }

    public void stopScroll() {
        handler.removeCallbacks(runnable);
        hasPostRunnable = false;
    }

}
