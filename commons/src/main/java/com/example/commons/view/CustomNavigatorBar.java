package com.example.commons.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.commons.R;

/**
 * @Author skygge.
 * @Date on 2019-08-19.
 * @Github https://github.com/javofxu
 * @Dec:
 * @version: ${VERSION}.
 * @Update :
 */
public class CustomNavigatorBar extends RelativeLayout implements View.OnClickListener {

    private ImageView leftImage;
    private TextView midText;
    private OnCustomClickListener customClickListener ;

    public CustomNavigatorBar(Context context) {
        this(context,null);
    }

    public CustomNavigatorBar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomNavigatorBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        iniView(context);
        /**
         * 两种初始化的不同，请看下面注释讲解
         */
        initOneType(context, attrs);//第一种初始化
//        initTwoType(context, attrs);//第二种初始化
    }

    private void iniView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_title_bar, this, true);
        leftImage = view.findViewById(R.id.custom_back);
        midText = view.findViewById(R.id.custom_center_text);

    }

    /**
     * 有兴趣的请参考鸿洋大神的自定义讲解
     *
     * 初始化属性值：这种写法，不管你在布局中有没有使用该属性，都会执行getXXX方法赋值
     *假设一个场景：
     *    private int attr_mode = 1;//默认为1
     *   //然后你在写getXXX方法的时候，是这么写的：
     *   attr_mode = array.getInt(i, 0);
     *
     *   可能你的自定义属性有个默认的值，然后你在写赋值的时候，一看是整形，就默默的第二个参数就给了个0，
     *   然而用户根本没有在布局文件里面设置这个属性，你却在运行时将其变为了0（而不是默认值），而第二种就不存在这个问题。
     *   当然这个场景可以由规范的书写代码的方式来避免，（把默认值提取出来，都设置对就好了）。
     *
     * 场景二：
     *
     *   其实还有个场景，假设你是继承自某个View，父类的View已经对该成员变量进行赋值了，然后你这边需要根据用户的设置情况，
     *   去更新这个值，第一种写法，如果用户根本没有设置，你可能就将父类的赋值给覆盖了。
     *
     * @param context
     * @param attrs
     */
    private void initTwoType(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomNavigatorBar);
        if (null != typedArray) {
            Drawable leftDrawable = typedArray.getDrawable(R.styleable.CustomNavigatorBar_leftImage);
            leftImage.setImageDrawable(leftDrawable);

            boolean leftImageVisible = typedArray.getBoolean(R.styleable.CustomNavigatorBar_leftImageVisiable, false);
            if (leftImageVisible) {
                leftImage.setVisibility(View.VISIBLE);
            } else {
                leftImage.setVisibility(View.GONE);
            }

            typedArray.recycle();
        }
    }

    /**注：如果switch报错，请改为if-else
     * 初始化属性值：这种写法，只有在布局中设置了该属性值后，才会调用getXXX()方法赋值
     * @param context
     * @param attrs
     */
    private void initOneType(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomNavigatorBar);
        int totalAttributes = typedArray.getIndexCount();
        for (int i = 0 ; i<totalAttributes ;i++) {
            int index = typedArray.getIndex(i);
            if (index == R.styleable.CustomNavigatorBar_leftImage) {
                leftImage.setImageDrawable(typedArray.getDrawable(index));
            } else if (index == R.styleable.CustomNavigatorBar_leftImageVisiable) {
                getVisible(typedArray, leftImage, index);
            } else if (index == R.styleable.CustomNavigatorBar_midText) {
                midText.setText(typedArray.getString(index));
            } else if (index == R.styleable.CustomNavigatorBar_midTextVisiable) {
                getVisible(typedArray, midText, index);
            } else if (index == R.styleable.CustomNavigatorBar_midTextFontSize) {
                midText.setTextSize(typedArray.getDimensionPixelSize(index, (int) sp2px(context, 18)));
            }else if (index == R.styleable.CustomNavigatorBar_midTextFontColor){
                midText.setTextColor(typedArray.getColor(index,Color.WHITE));
            }else if (index == R.styleable.CustomNavigatorBar_titleBarBackground) {
                int titleBarBackgroundColor = typedArray.getColor(index, Color.GREEN);
                setBackgroundColor(titleBarBackgroundColor);
            }
        }
        typedArray.recycle();
    }

    /**
     * 用来隐藏显示View，只有gone 和 visible两种情况，因为inVisible感到在这里用不到，就没有封装
     * @param typedArray
     * @param view
     * @param index
     */
    private void getVisible(TypedArray typedArray ,View view,int index) {
        boolean visible = typedArray.getBoolean(index, false);
        if (visible) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    private void setVisible(View view ,boolean visible) {
        if (visible) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    /**
     * 两种监听只能使用其中一种，不能同时使用
     *
     * ----------------------------第一种点击监听开始处----------------------------------------
     * @param clickListener
     */
    public void setLeftImageOnClickListener(View.OnClickListener clickListener) {
        if (null != clickListener) {
            leftImage.setOnClickListener(clickListener);
        }
    }

    /**
     * ----------------------------第二种点击监听开始处----------------------------------------
     * @return
     */
    public void addViewClickListener(OnCustomClickListener listener) {
        leftImage.setOnClickListener(this);
        this.customClickListener = listener ;
    }

    public interface OnCustomClickListener{
        void onClickListener(View rootView);
    }

    @Override
    public void onClick(View view) {
        customClickListener.onClickListener(view);
    }

    /**
     * ----------------------------第二种点击监听结束处----------------------------------------
     * @return
     */

    public ImageView getLeftImageView(){
        return leftImage;
    }

    public TextView getMidText(){
        return midText;
    }


    public void setMidText(String textDescribe) {
        if (null != textDescribe) {
            midText.setText(textDescribe);
        }
    }


    /**
     * 设置textView的字体颜色
     * @param textColor
     */

    public void setMidTextColor(int textColor) {
        midText.setText(textColor);
    }

    /**
     * 设置title栏背景色
     * @param color
     */
    public void setTitleBarBackground(int color) {
        setBackgroundColor(color);
    }

    /**
     * 左右控件的隐藏显示
     * @param visible
     */
    public void setLeftImageVisible(boolean visible) {
        setVisible(leftImage, visible);
    }


    private float sp2px(Context context, float defaultVal) {
        float applyDimension = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, defaultVal, context.getResources().getDisplayMetrics());
        return applyDimension ;
    }

    private float dp2px(Context context, float defaultVal) {
        float applyDimension = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, defaultVal, context.getResources().getDisplayMetrics());
        return applyDimension ;
    }

    private float dp2px(Context context, int defultVal) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (float) (defultVal*scale + 0.5);
    }
}
