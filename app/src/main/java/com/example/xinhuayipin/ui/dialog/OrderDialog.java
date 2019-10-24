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
 * @Date on 2019-09-02.
 * @Github https://github.com/javofxu
 * @Dec: 无图书弹出框
 * @version: ${VERSION}.
 * @Update :
 */
public class OrderDialog extends Dialog {

    @BindView(R.id.order_title)
    TextView orderTitle;
    @BindView(R.id.order_click)
    Button orderClick;

    public OrderDialog(@NonNull Context context) {
        super(context, R.style.custom_dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_order);
        setCanceledOnTouchOutside(false);
        ButterKnife.bind(this);
        orderClick.setOnClickListener(v -> dismiss());
    }
}
