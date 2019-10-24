package com.example.xinhuayipin.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.commons.utils.DataTimeUtil;
import com.example.xinhuayipin.R;
import com.example.xinhuayipin.data.record.Lend_history_info;

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
public class GiveBackAdapter extends RecyclerView.Adapter<GiveBackAdapter.RepayHolder> {

    private Context mContext;
    private List<Lend_history_info> reserveList;

    public GiveBackAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setReserveList(List<Lend_history_info> reserveList) {
        this.reserveList = reserveList;
    }

    @NonNull
    @Override
    public RepayHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_give_back, null);
        return new RepayHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RepayHolder repayHolder, int i) {
        Lend_history_info data = reserveList.get(i);
        repayHolder.giveBackName.setText(data.getTitle());
        repayHolder.giveBackAuthor.setText(data.getCreator());
        repayHolder.giveBackLend.setText(mContext.getString(R.string.lend_time) + DataTimeUtil.formatDateTime(data.getLend_time()));
        repayHolder.giveBackReserve.setText(mContext.getString(R.string.reserve_time) + DataTimeUtil.formatDateTime(data.getReturn_time()));
        Glide.with(mContext).load(data.getCoverage()).into(repayHolder.itemGiveBackPic);
    }

    @Override
    public int getItemCount() {
        return reserveList != null ? reserveList.size() : 0;
    }

    class RepayHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_give_back_pic)
        ImageView itemGiveBackPic;
        @BindView(R.id.give_back_name)
        TextView giveBackName;
        @BindView(R.id.give_back_author)
        TextView giveBackAuthor;
        @BindView(R.id.give_back_lend)
        TextView giveBackLend;
        @BindView(R.id.give_back_reserve)
        TextView giveBackReserve;

        public RepayHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
