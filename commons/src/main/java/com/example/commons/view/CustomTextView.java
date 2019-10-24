package com.example.commons.view;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * @Author skygge.
 * @Date on 2019-08-14.
 * @Github https://github.com/javofxu
 * @Dec:
 * @version: ${VERSION}.
 * @Update :
 */
public class CustomTextView extends android.support.v7.widget.AppCompatTextView {

    public CustomTextView(Context context) {
        super(context);
        initType(context);
    }

    public CustomTextView(Context context,  AttributeSet attrs) {
        super(context, attrs);
        initType(context);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initType(context);
    }

    @Override
    public boolean isFocused() {
        return true;
    }

    private void initType(Context context){
        AssetManager manager = context.getAssets();
        Typeface typeface = Typeface.createFromAsset(manager, "fonts/common.TTF");
        setTypeface(typeface);
    }
}
