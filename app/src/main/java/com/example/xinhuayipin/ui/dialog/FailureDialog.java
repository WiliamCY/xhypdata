package com.example.xinhuayipin.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Button;
import android.widget.TextView;

import com.example.xinhuayipin.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author skygge.
 * @Date on 2019-09-04.
 * @Github https://github.com/javofxu
 * @Dec: 借阅失败弹出框
 * @version: ${VERSION}.
 * @Update :
 */
public class FailureDialog extends Dialog {

    @BindView(R.id.failure_title)
    TextView failureTitle;
    @BindView(R.id.failure_click)
    Button failureClick;

    private String mTitle;
    private callBack mCallBack;

    public FailureDialog(@NonNull Context context, String title) {
        super(context, R.style.custom_dialog);
        this.mTitle = title;
    }

    public void setCallBack(callBack mCallBack) {
        this.mCallBack = mCallBack;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_failure);
        setCanceledOnTouchOutside(false);
        ButterKnife.bind(this);
        failureTitle.setText(mTitle);
        failureClick.setOnClickListener(v -> {
            dismiss();
            if (mCallBack!=null){
                mCallBack.onClose();
            }
        });
    }

    public interface callBack{
        void onClose();
    }
}
