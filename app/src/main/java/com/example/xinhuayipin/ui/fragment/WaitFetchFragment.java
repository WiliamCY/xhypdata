package com.example.xinhuayipin.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.example.commons.BaseFragment;
import com.example.commons.annotations.ViewInject;
import com.example.commons.view.refresh.CustomRefreshView;
import com.example.xinhuayipin.R;
import com.example.xinhuayipin.app.MyApplication;
import com.example.xinhuayipin.data.record.Reserve_info;
import com.example.xinhuayipin.databinding.FragmentWaitFetchBinding;
import com.example.xinhuayipin.mvp.contract.WaitFetchContract;
import com.example.xinhuayipin.mvp.presenter.WaitFetchPresenter;
import com.example.xinhuayipin.ui.adapter.WaitFetchAdapter;

import java.util.List;

/**
 * 未取
 */

@ViewInject(getLayoutId = R.layout.fragment_wait_fetch)
public class WaitFetchFragment extends BaseFragment<WaitFetchPresenter, FragmentWaitFetchBinding> implements WaitFetchContract.WaitFetchView {

    private int mStudentId;
    private WaitFetchAdapter mAdapter;
    
    @Override
    protected void initPresent() {
        super.initPresent();
        mPresenter = new WaitFetchPresenter();
    }

    @Override
    protected void initialize() {
        super.initialize();
        mStudentId = getArguments().getInt("studentId");
    }

    @Override
    protected void initView() {
        super.initView();
        mDataBinding.waitFetchRefresh.getSwipeRefreshLayout().setColorSchemeColors(getResources().getColor(R.color.login));
        mDataBinding.waitFetchRefresh.getRecyclerView().setLayoutManager(new LinearLayoutManager(mContext));
        mDataBinding.waitFetchRefresh.setLoadMoreEnable(false);
        mAdapter = new WaitFetchAdapter(mContext);
        mAdapter.setReserveList(null);
        mDataBinding.waitFetchRefresh.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        showProgressDialog();
        mPresenter.getReserveInfo(mToken, mStudentId, 4);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mDataBinding.waitFetchRefresh.setOnLoadListener(new CustomRefreshView.OnLoadListener() {
            @Override
            public void onRefresh() {
                mPresenter.getReserveInfo(mToken, mStudentId, 4);
            }

            @Override
            public void onLoadMore() {

            }
        });
    }

    @Override
    public void hasFetch(List<Reserve_info> dataList) {
        dismissProgressDialog();
        mAdapter.setReserveList(dataList);
        mAdapter.notifyDataSetChanged();
        mDataBinding.waitFetchRefresh.complete();
        Log.i(TAG, "hasFetch: ");
    }

    @Override
    public void noFetch() {
        dismissProgressDialog();
        mAdapter.setReserveList(null);
        mAdapter.notifyDataSetChanged();
        mDataBinding.waitFetchRefresh.complete();
        mDataBinding.waitFetchRefresh.setEmptyView(getString(R.string.no_record), R.mipmap.icon_no_record);
        Log.i(TAG, "noFetch: ");
    }

    @Override
    public void netWorkError() {
        dismissProgressDialog();
        mDataBinding.waitFetchRefresh.setErrorView();
    }
}
