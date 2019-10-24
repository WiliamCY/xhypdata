package com.example.xinhuayipin.ui.fragment;


import android.support.v7.widget.LinearLayoutManager;

import com.example.commons.BaseFragment;
import com.example.commons.annotations.ViewInject;
import com.example.commons.view.refresh.CustomRefreshView;
import com.example.xinhuayipin.R;
import com.example.xinhuayipin.app.MyApplication;
import com.example.xinhuayipin.data.record.Lend_info;
import com.example.xinhuayipin.databinding.FragmentWaitRepayBinding;
import com.example.xinhuayipin.mvp.contract.WaitRepayContract;
import com.example.xinhuayipin.mvp.presenter.WaitRepayPresenter;
import com.example.xinhuayipin.ui.adapter.WaitRepayAdapter;

import java.util.List;

/**
 * 未还
 */
@ViewInject(getLayoutId = R.layout.fragment_wait_repay)
public class WaitRepayFragment extends BaseFragment<WaitRepayPresenter, FragmentWaitRepayBinding> implements WaitRepayContract.WaitRepayView {

    private int mStudentId;
    private WaitRepayAdapter mAdapter;

    @Override
    protected void initPresent() {
        super.initPresent();
        mPresenter = new WaitRepayPresenter();
    }

    @Override
    protected void initialize() {
        super.initialize();
        mStudentId = getArguments().getInt("studentId");
    }

    @Override
    protected void initView() {
        super.initView();
        mDataBinding.waitRepayRefresh.getSwipeRefreshLayout().setColorSchemeColors(getResources().getColor(R.color.login));
        mDataBinding.waitRepayRefresh.getRecyclerView().setLayoutManager(new LinearLayoutManager(mContext));
        mDataBinding.waitRepayRefresh.setLoadMoreEnable(false);
        mAdapter = new WaitRepayAdapter(mContext);
        mAdapter.setLendInfo(null);
        mDataBinding.waitRepayRefresh.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.getReserveInfo(mToken, mStudentId, 1);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mDataBinding.waitRepayRefresh.setOnLoadListener(new CustomRefreshView.OnLoadListener() {
            @Override
            public void onRefresh() {
                mPresenter.getReserveInfo(mToken, mStudentId, 1);
            }

            @Override
            public void onLoadMore() {

            }
        });
    }

    @Override
    public void hasRepay(List<Lend_info> dataList) {
        mAdapter.setLendInfo(dataList);
        mAdapter.notifyDataSetChanged();
        mDataBinding.waitRepayRefresh.complete();
    }

    @Override
    public void noRepay() {
        mAdapter.setLendInfo(null);
        mAdapter.notifyDataSetChanged();
        mDataBinding.waitRepayRefresh.complete();
        mDataBinding.waitRepayRefresh.setEmptyView(getString(R.string.no_record), R.mipmap.icon_no_record);
    }

    @Override
    public void netWorkError() {
        mDataBinding.waitRepayRefresh.complete();
        mDataBinding.waitRepayRefresh.setErrorView();
    }
}
