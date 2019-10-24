package com.example.xinhuayipin.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.xinhuayipin.R;
import com.example.xinhuayipin.data.FingerprintBean;
import com.example.xinhuayipin.ui.activity.FpEditorActivity;

import java.util.List;

/**
 * @Author skygge.
 * @Date on 2019-08-21.
 * @Github https://github.com/javofxu
 * @Dec: 指纹列表适配器
 * @version: ${VERSION}.
 * @Update :
 */
public class FingerprintAdapter extends RecyclerView.Adapter<FingerprintAdapter.ItemHolder> {

    private Context mContext;
    private List<FingerprintBean> fingerprintBeanList;

    public FingerprintAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setFingerprintBeanList(List<FingerprintBean> fingerprintBeanList) {
        this.fingerprintBeanList = fingerprintBeanList;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_fingerprint, null);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder itemHolder, int i) {
        FingerprintBean bean = fingerprintBeanList.get(i);
        itemHolder.fingerName.setText("指纹"+bean.getFingerprint_id());
        itemHolder.fingerEditor.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(mContext, FpEditorActivity.class);
            intent.putExtra("id",bean.getId());
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return fingerprintBeanList!=null?fingerprintBeanList.size():0;
    }

    class ItemHolder extends RecyclerView.ViewHolder {

        TextView fingerName;
        Button fingerEditor;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            fingerName = itemView.findViewById(R.id.item_finger_name);
            fingerEditor = itemView.findViewById(R.id.item_finger_editor);
        }

    }
}
