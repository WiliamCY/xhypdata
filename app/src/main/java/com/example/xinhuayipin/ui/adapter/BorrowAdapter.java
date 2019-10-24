package com.example.xinhuayipin.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.commons.bus.LiveDataBus;
import com.example.xinhuayipin.R;
import com.example.xinhuayipin.app.MyApplication;
import com.example.xinhuayipin.data.UserDataBean;
import com.example.xinhuayipin.data.book.BookBean;
import com.example.xinhuayipin.data.book.Book_info;
import com.example.xinhuayipin.data.book.Marc_info;
import com.example.xinhuayipin.data.borrow.BorrowBean;
import com.example.xinhuayipin.mvp.contract.BorrowContract;
import com.example.xinhuayipin.mvp.model.BorrowModel;
import com.example.xinhuayipin.ui.activity.SuccessfulActivity;
import com.example.xinhuayipin.ui.activity.VerifyActivity;
import com.example.xinhuayipin.ui.dialog.FailureDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

/**
 * @Author skygge.
 * @Date on 2019-08-26.
 * @Github https://github.com/javofxu
 * @Dec:
 * @version: ${VERSION}.
 * @Update :
 */
public class BorrowAdapter extends RecyclerView.Adapter<BorrowAdapter.BorrowItemHolder> {

    private static final String TAG = BorrowAdapter.class.getSimpleName();
    private Context mContext;
    private String mToken;
    private String mMac;
    private BorrowContract.BorrowModel model;

    private List<BookBean.Data> borrowList;

    public BorrowAdapter(Context mContext, String token, String mac) {
        this.mContext = mContext;
        this.mToken = token;
        this.mMac = mac;
    }

    public void setBorrowList(List<BookBean.Data> borrow) {
        this.borrowList = borrow;
    }

    @NonNull
    @Override
    public BorrowItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_borrow_sudoku, null);
        return new BorrowItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BorrowItemHolder itemHolder, int i) {
        Marc_info info = borrowList.get(i).getMarc_info();
        Book_info bookInfo = borrowList.get(i).getBook_info();
        Glide.with(mContext).load(info.getCoverage()).into(itemHolder.itemBorrowPic);
        itemHolder.itemBorrowName.setText(mContext.getString(R.string.book_left)+info.getTitle()+mContext.getString(R.string.book_right));
        if (bookInfo.getLend_flag() == 0) {
            itemHolder.itemBorrowFlag.setVisibility(View.GONE);
            itemHolder.itemBorrowButton.setEnabled(true);
        } else if (bookInfo.getLend_flag() == 1) {
            itemHolder.itemBorrowButton.setText(mContext.getString(R.string.has_borrow));
            itemHolder.itemBorrowButton.setBackgroundResource(R.mipmap.unclickable_borrow_button);
            itemHolder.itemBorrowButton.setEnabled(false);
            itemHolder.itemBorrowFlag.setVisibility(View.VISIBLE);
        }
        itemHolder.itemBorrowButton.setOnClickListener(v -> {
            LiveDataBus.get().with("borrow_success").setValue(borrowList.get(i).getNo());
        });
    }

    @Override
    public int getItemCount() {
        return borrowList != null ? borrowList.size() : 0;
    }

    class BorrowItemHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_borrow_pic)
        ImageView itemBorrowPic;
        @BindView(R.id.item_borrow_name)
        TextView itemBorrowName;
        @BindView(R.id.item_borrow_button)
        Button itemBorrowButton;
        @BindView(R.id.borrow_flag)
        ImageView itemBorrowFlag;

        public BorrowItemHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
