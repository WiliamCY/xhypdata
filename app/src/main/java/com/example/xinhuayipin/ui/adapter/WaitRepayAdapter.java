package com.example.xinhuayipin.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.xinhuayipin.R;
import com.example.xinhuayipin.data.record.Lend_info;
import com.example.xinhuayipin.ui.activity.ScanActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author skygge.
 * @Date on 2019-09-05.
 * @Github https://github.com/javofxu
 * @Dec:
 * @version: ${VERSION}.
 * @Update :
 */
public class WaitRepayAdapter extends RecyclerView.Adapter<WaitRepayAdapter.RepayHolder> {

    private Context mContext;
    private List<Lend_info> lendInfoList;

    public WaitRepayAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setLendInfo(List<Lend_info> lendInfo) {
        this.lendInfoList = lendInfo;
    }

    @NonNull
    @Override
    public RepayHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_wait, null);
        return new RepayHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RepayHolder repayHolder, int i) {
        Lend_info data = lendInfoList.get(i);
        repayHolder.repayName.setText(data.getMarc_info().getTitle());
        repayHolder.repayAuthor.setText(data.getMarc_info().getCreator());
        repayHolder.repayAddress.setText(mContext.getString(R.string.local_address)+data.getBook_info().getBookcase_name());
        Glide.with(mContext).load(data.getMarc_info().getCoverage()).into(repayHolder.itemRepayPic);
        repayHolder.repayButton.setText(mContext.getString(R.string.return_back));
        repayHolder.repayButton.setBackgroundResource(R.drawable.repay_button);
        repayHolder.repayButton.setOnClickListener(v -> mContext.startActivity(new Intent(mContext, ScanActivity.class)));
    }

    @Override
    public int getItemCount() {
        return lendInfoList != null ? lendInfoList.size() : 0;
    }

    class RepayHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_repay_pic)
        ImageView itemRepayPic;
        @BindView(R.id.repay_name)
        TextView repayName;
        @BindView(R.id.repay_author)
        TextView repayAuthor;
        @BindView(R.id.repay_address)
        TextView repayAddress;
        @BindView(R.id.repay_button)
        Button repayButton;

        public RepayHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
