package com.example.xinhuayipin.ui.activity;

import android.text.TextUtils;
import android.util.Log;

import com.example.commons.BaseActivity;
import com.example.commons.annotations.ViewInject;
import com.example.commons.bus.LiveDataBus;
import com.example.xinhuayipin.R;
import com.example.xinhuayipin.databinding.ActivityFpEditorBinding;
import com.example.xinhuayipin.mvp.contract.EditorContract;
import com.example.xinhuayipin.mvp.presenter.EditorPresenter;

@ViewInject(getLayoutId = R.layout.activity_fp_editor)
public class FpEditorActivity extends BaseActivity<EditorPresenter, ActivityFpEditorBinding> implements EditorContract.EditorView {

    private Long id;

    @Override
    protected void initPresent() {
        super.initPresent();
        mPresenter = new EditorPresenter(mContext);
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected void initData() {
        super.initData();
        id = getIntent().getLongExtra("id",0);
        Log.i(TAG, "initData: "+id);
        if (id!=0){
            mPresenter.getFingerprint(id);
        }else {
            mDataBinding.editorName.setText(R.string.noneFinger);
            mDataBinding.editorSave.setEnabled(false);
            mDataBinding.editorDelete.setEnabled(false);
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        mDataBinding.editorSave.setOnClickListener(v -> {
            String newName = mDataBinding.editorName.getText().toString();
            if (!TextUtils.isEmpty(newName)){
                mPresenter.saveFingerName(newName);
            }else {
                showToast(getString(R.string.finger_name_tip));
            }
        });
        mDataBinding.editorDelete.setOnClickListener(v -> mPresenter.deleteFingerName());
        mDataBinding.editorTextDelete.setOnClickListener(v -> mDataBinding.editorName.setText(""));
    }

    @Override
    public void setFingerName(String name) {
        mDataBinding.editorName.setText(name);
    }

    @Override
    public void complete() {
        LiveDataBus.get().with("update").setValue("");
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
