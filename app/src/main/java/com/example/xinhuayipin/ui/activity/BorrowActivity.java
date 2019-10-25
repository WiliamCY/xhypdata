package com.example.xinhuayipin.ui.activity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;

import com.example.commons.BaseActivity;
import com.example.commons.annotations.ViewInject;
import com.example.commons.bus.LiveDataBus;
import com.example.commons.view.refresh.CustomRefreshView;
import com.example.xinhuayipin.R;
import com.example.xinhuayipin.data.UserDataBean;
import com.example.xinhuayipin.data.book.BookBean;
import com.example.xinhuayipin.data.book.Marc_info;
import com.example.xinhuayipin.data.borrow.BorrowBean;
import com.example.xinhuayipin.databinding.ActivityBorrowBinding;
import com.example.xinhuayipin.mvp.contract.BorrowContract;
import com.example.xinhuayipin.mvp.model.BorrowModel;
import com.example.xinhuayipin.mvp.presenter.BorrowPresenter;
import com.example.xinhuayipin.ui.adapter.BorrowAdapter;
import com.example.xinhuayipin.ui.dialog.FailureDialog;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

@ViewInject(getLayoutId = R.layout.activity_borrow)
public class BorrowActivity extends BaseActivity<BorrowPresenter, ActivityBorrowBinding> implements BorrowContract.BorrowView {

    private BorrowAdapter mAdapter;
    private String book_no;

    @Override
    protected void initPresent() {
        super.initPresent();
        mPresenter = new BorrowPresenter();
    }

    @Override
    protected void initView() {
        super.initView();
        mAdapter = new BorrowAdapter(mContext, mToken, mMachId);
        mDataBinding.borrowRefresh.getSwipeRefreshLayout().setColorSchemeColors(getResources().getColor(R.color.login));
        mDataBinding.borrowRefresh.getRecyclerView().setLayoutManager(new GridLayoutManager(mContext,3));
        mDataBinding.borrowRefresh.setLoadMoreEnable(false);
        mAdapter.setBorrowList(null);
        mDataBinding.borrowRefresh.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        showProgressDialog();
        mPresenter.getBorrowData(mToken, mMachId);
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initListener() {
        super.initListener();
        mDataBinding.borrowRefresh.setOnLoadListener(new CustomRefreshView.OnLoadListener() {
            @Override
            public void onRefresh() {
                mPresenter.getBorrowData(mToken, mMachId);
            }

            @Override
            public void onLoadMore() {

            }
        });

        LiveDataBus.get().with("borrow_success", String.class).observe(this, s -> {
            book_no = s;
            skipAnotherActivity(VerifyActivity.class);
        });

        LiveDataBus.get().with("verify", UserDataBean.class).observe(this, userDataBean -> {
            String studentId = String.valueOf(userDataBean.getStudentId());
            BorrowContract.BorrowModel model = new BorrowModel();
            Observable<BorrowBean> observable = model.borrow(mToken, mMachId, book_no, studentId);
            observable.subscribe(borrowBean -> {
                if (borrowBean.getCode() == 0){
                    Intent intent = new Intent();
                    intent.setClass(mContext, SuccessfulActivity.class);
                    intent.putExtra("name",borrowBean.getMarc_info().getTitle());
                    intent.putExtra("address", borrowBean.getBook_info().getBookcase_name());
                    intent.putExtra("borrower", borrowBean.getStudent_info().getName());
                    intent.putExtra("time", borrowBean.getLend_info().getNeed_return_time());
                    intent.putExtra("isbn", borrowBean.getMarc_info().getIsbn());
                    mContext.startActivity(intent);
                    mPresenter.getBorrowData(mToken, mMachId);
                }else {
                    FailureDialog dialog = new FailureDialog(mContext, borrowBean.getMsg());
                    dialog.show();
                }
            },throwable -> Log.i(TAG, "onBindViewHolder: "+ throwable));
        });
    }

    @Override
    public void successful(List<BookBean.Data> beanList) {
        dismissProgressDialog();
        List<BookBean.Data> dataList = new ArrayList<>();
        for (int i = 0; i < beanList.size(); i++) {
            Marc_info marcInfo = beanList.get(i).getMarc_info();
            if (marcInfo!=null){
                dataList.add(beanList.get(i));
            }
        }
        mAdapter.setBorrowList(dataList);
        mAdapter.notifyDataSetChanged();
        mDataBinding.borrowRefresh.complete();
    }

    @Override
    public void failure() {
        dismissProgressDialog();
        mAdapter.setBorrowList(null);
        mAdapter.notifyDataSetChanged();
        mDataBinding.borrowRefresh.setEmptyView(getString(R.string.no_book),0);
        mDataBinding.borrowRefresh.complete();
    }

    @Override
    public void network_error() {
        dismissProgressDialog();
        mDataBinding.borrowRefresh.setErrorView();
        mDataBinding.borrowRefresh.complete();
    }
}
