package com.example.xinhuayipin.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;

import com.example.commons.BaseFragment;
import com.example.commons.annotations.ViewInject;
import com.example.commons.view.refresh.CustomRefreshView;
import com.example.xinhuayipin.R;
import com.example.xinhuayipin.app.MyApplication;
import com.example.xinhuayipin.data.record.Lend_history_info;
import com.example.xinhuayipin.databinding.FragmentGiveBackBinding;
import com.example.xinhuayipin.mvp.contract.GiveBackContract;
import com.example.xinhuayipin.mvp.presenter.GiveBackPresenter;
import com.example.xinhuayipin.ui.adapter.GiveBackAdapter;

import java.util.List;

/**
 * 已经归还
 */
@ViewInject(getLayoutId = R.layout.fragment_give_back)
public class GiveBackFragment extends BaseFragment<GiveBackPresenter, FragmentGiveBackBinding> implements GiveBackContract.GiveBackView {

    private int mStudentId;
    private GiveBackAdapter mAdapter;

    @Override
    protected void initPresent() {
        super.initPresent();
        mPresenter = new GiveBackPresenter();
    }

    @Override
    protected void initialize() {
        super.initialize();
        mStudentId = getArguments().getInt("studentId");
    }

    @Override
    protected void initView() {
        super.initView();
        mDataBinding.giveBackRefresh.getSwipeRefreshLayout().setColorSchemeColors(getResources().getColor(R.color.login));
        mDataBinding.giveBackRefresh.getRecyclerView().setLayoutManager(new LinearLayoutManager(mContext));
        mDataBinding.giveBackRefresh.setLoadMoreEnable(false);
        mAdapter = new GiveBackAdapter(mContext);
        mAdapter.setReserveList(null);
        mDataBinding.giveBackRefresh.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.getReserveInfo(mToken, mStudentId, 2);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mDataBinding.giveBackRefresh.setOnLoadListener(new CustomRefreshView.OnLoadListener() {
            @Override
            public void onRefresh() {
                mPresenter.getReserveInfo(mToken, mStudentId, 2);
            }

            @Override
            public void onLoadMore() {

            }
        });
    }

    @Override
    public void hasGiveBack(List<Lend_history_info> dataList) {
        mAdapter.setReserveList(dataList);
        mAdapter.notifyDataSetChanged();
        mDataBinding.giveBackRefresh.complete();
    }

    @Override
    public void noGiveBack() {
        mAdapter.setReserveList(null);
        mAdapter.notifyDataSetChanged();
        mDataBinding.giveBackRefresh.complete();
        mDataBinding.giveBackRefresh.setEmptyView(getString(R.string.no_record), R.mipmap.icon_no_record);
    }

    @Override
    public void netWorkError() {
        mDataBinding.giveBackRefresh.complete();
        mDataBinding.giveBackRefresh.setErrorView();
    }
}
