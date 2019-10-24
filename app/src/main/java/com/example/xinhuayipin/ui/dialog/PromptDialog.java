package com.example.xinhuayipin.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Button;
import android.widget.TextView;

import com.example.xinhuayipin.R;

/**
 * @Author skygge.
 * @Date on 2019-08-23.
 * @Github https://github.com/javofxu
 * @Dec: 通用的失败弹出框
 * @version: ${VERSION}.
 * @Update :
 */
public class PromptDialog extends Dialog {

    private Context mContext;
    private TextView mTitle;
    private Button mClick;
    private Button mCancel;
    private listener mListener;
    private String title;
    private String tv_cancel;
    private String tv_confirm;

    public PromptDialog(@NonNull Context context, listener listener) {
        super(context, R.style.custom_dialog);
        this.mContext = context;
        this.mListener = listener;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_prompt);
        setCanceledOnTouchOutside(false);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        mTitle = findViewById(R.id.prompt_title);
        mClick = findViewById(R.id.prompt_click);
        mCancel = findViewById(R.id.prompt_cancel);
    }

    private void initData() {
        mTitle.setText(title);
        mClick.setText(tv_confirm);
        mCancel.setText(tv_cancel);
    }

    private void initListener() {
        mClick.setOnClickListener(v -> mListener.confirm());

        mCancel.setOnClickListener(v -> mListener.cancel());
    }

    public void setDialogMsg(String title, String confirm, String cancel) {
        this.title = title;
        this.tv_confirm = confirm;
        this.tv_cancel = cancel;
    }

    public interface listener{

        void confirm();

        void cancel();
    }
}
