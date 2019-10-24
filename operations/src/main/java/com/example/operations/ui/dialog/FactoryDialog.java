package com.example.operations.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.example.commons.utils.SpHelperUtil;
import com.example.operations.R;
import com.example.operations.data.DaoSession;
import com.example.operations.data.GreenDaoUtil;

/**
 * @Author skygge.
 * @Date on 2019-09-20.
 * @Github https://github.com/javofxu
 * @Dec:
 * @version: ${VERSION}.
 * @Update :
 */
public class FactoryDialog extends Dialog {

    private Context mContext;
    private callBack mCallBack;

    public FactoryDialog(@NonNull Context context, callBack back) {
        super(context, com.example.commons.R.style.custom_dialog);
        this.mContext = context;
        this.mCallBack = back;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_fectory);
        setCanceledOnTouchOutside(true);
        findViewById(R.id.factory_click).setOnClickListener(v -> {
            mCallBack.refresh();
            dismiss();
        });
        findViewById(R.id.factory_cancel).setOnClickListener(v -> dismiss());
    }

    public interface callBack{
        void refresh();
    }
}
