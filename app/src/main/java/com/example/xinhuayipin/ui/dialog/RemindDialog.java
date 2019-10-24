package com.example.xinhuayipin.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xinhuayipin.R;
import com.example.xinhuayipin.ui.activity.LoginActivity;
import com.example.xinhuayipin.ui.activity.ScanActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author skygge.
 * @Date on 2019-09-04.
 * @Github https://github.com/javofxu
 * @Dec: 通用的提醒弹出框
 * @version: ${VERSION}.
 * @Update :
 */
public class RemindDialog extends Dialog {

    @BindView(R.id.remind_pic)
    ImageView remindPic;
    @BindView(R.id.remind_title)
    TextView remindTitle;
    @BindView(R.id.remind_click)
    Button remindClick;

    private Context mContext;
    private String mTitle;
    private String tv_button;
    private int mType;

    public RemindDialog(@NonNull Context context) {
        super(context, R.style.custom_dialog);
        this.mContext = context;
    }

    public void setMsg(String title, String tv_button, int type) {
        this.mTitle = title;
        this.tv_button = tv_button;
        this.mType = type;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_remind);
        setCanceledOnTouchOutside(false);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        remindTitle.setText(mTitle);
        remindClick.setText(tv_button);
        remindClick.setOnClickListener(v -> {
            switch (mType){
                case 0:
                    dismiss();
                    break;
                case 1:
                    mContext.startActivity(new Intent(mContext, ScanActivity.class));
                    dismiss();
                    break;
                case 2:
                    mContext.startActivity(new Intent(mContext, LoginActivity.class));
                    dismiss();
            }
        });
    }


}
