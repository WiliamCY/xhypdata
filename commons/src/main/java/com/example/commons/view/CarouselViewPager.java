package com.example.commons.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * @Author skygge.
 * @Date on 2019-08-16.
 * @Github https://github.com/javofxu
 * @Dec:
 * @version: ${VERSION}.
 * @Update :
 */
public class CarouselViewPager extends ViewPager {

    //滚动的页面
    private List<ImageView> mViews;

    //滚动的页面数量
    private int count;

    //自动滚动信号
    private final int SCROLL = 100002;

    //创建一个滚动线程
    private Thread thread;

    //间隔时间
    private int SPACE;

    //目前线程状态
    private boolean NORMAL = true;

    public CarouselViewPager(@NonNull Context context) {
        super(context);
    }

    public CarouselViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            setCurrentItem(getCurrentItem()+1);
        }
    };

    public void initViewPager(List<ImageView> mImg, final int space, OnViewpagerChangeListener listener){
        this.mViews = mImg;
        this.count = mViews.size();
        this.SPACE = space;
        setAdapter(new BannerPager());
        setCurrentItem(Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % mViews.size());

        thread = new Thread(() -> {
            while (true){
                try {
                    NORMAL = true;
                    Thread.sleep(space*1000);
                    mHandler.sendEmptyMessage(SCROLL);
                }catch (InterruptedException e){
                    NORMAL = false;
                    try {
                        Thread.sleep(Integer.MAX_VALUE);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        thread.start();

        addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                listener.onChange(i % count);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if ((!NORMAL && state != 1) || (NORMAL && state == 1)){
                    thread.interrupt();
                }
            }
        });
    }

    public class BannerPager extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            int temp = position % count;
            ImageView imageView = mViews.get(temp);
            if (imageView.getParent() == container) {
                container.removeView(imageView);
            }
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        }
    }

    public interface OnViewpagerChangeListener {

        void onChange(int currentPage);
    }
}
