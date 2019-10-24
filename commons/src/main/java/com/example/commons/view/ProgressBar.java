package com.example.commons.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.commons.R;

import pl.droidsonroids.gif.GifImageView;

/**
 * @Author skygge.
 * @Date on 2019-08-20.
 * @Github https://github.com/javofxu
 * @Dec:
 * @version: ${VERSION}.
 * @Update :
 */
public class ProgressBar extends RelativeLayout {

    private Context mContext;
    private AttributeSet attrs;
    private int styleAttr;
    private GifImageView imageView;
    private Drawable imageFile;
    private TextView textMsg;
    private String customMsg;
    private int textColor, enlarge;
    private float textSize;

    public ProgressBar(Context context) {
        super(context);
        initView();
    }

    public ProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext=context;
        this.attrs=attrs;
        initView();
    }

    public ProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext=context;
        this.attrs=attrs;
        this.styleAttr=defStyleAttr;
        initView();
    }

    private void initView(){
        inflate(mContext, R.layout.layout_progress_bar, this);

        TypedArray arr = mContext.obtainStyledAttributes(attrs, R.styleable.ProgBar, styleAttr,0);

        imageFile = arr.getDrawable(R.styleable.ProgBar_barType);
        customMsg = arr.getString(R.styleable.ProgBar_text);
        textColor = arr.getColor(R.styleable.ProgBar_androidtextColor, Color.BLACK);
        textSize = arr.getDimension(R.styleable.ProgBar_textSize, 18);
        enlarge = arr.getInt(R.styleable.ProgBar_enlarge,2);
        imageView = findViewById(R.id.progressImg);
        textMsg = findViewById(R.id.textMsg);

//        imageView.setImageResource(R.drawable.progress);

        if (customMsg!=null){
            setTextMsg(customMsg);
        }

        setTextColor(textColor);
        setTextSize(textSize);
        enlarge(enlarge);

        arr.recycle();
    }

    public void setScaleType(ImageView.ScaleType scaleType){
        imageView.setScaleType(scaleType);
    }

    public void setImageFile(int draw){
        imageView.setImageResource(draw);
    }

    public void enlarge(int enlarge){
        if (enlarge>=1 && enlarge<=10 )
            imageView.getLayoutParams().height = enlarge*100;
    }

    public void setTextMsg(String message){
        textMsg.setText(message);
    }

    public void setTextColor(int color){
        textMsg.setTextColor(color);
    }

    public void setTextSize(float size){
        textMsg.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
    }

}
