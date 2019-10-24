package com.example.xinhuayipin.mvp.presenter;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.commons.mvp.BasePresenterImpl;
import com.example.commons.utils.ResourcesUtil;
import com.example.xinhuayipin.R;
import com.example.xinhuayipin.data.fetch.FetchBean;
import com.example.xinhuayipin.mvp.contract.MainContract;
import com.example.xinhuayipin.mvp.model.MainModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.Observable;

/**
 * @Author skygge.
 * @Date on 2019-08-13.
 * @Github https://github.com/javofxu
 * @Dec:
 * @version: ${VERSION}.
 * @Update :
 */
public class MainPresenter extends BasePresenterImpl<MainContract.MainView> implements MainContract.MainPresent {

    private static final String TAG = MainPresenter.class.getSimpleName();
    private Context mContext;
    private List<String> quotes;
    private List<ImageView> mPagerList;
    private MainContract.MainModel mModel;

    public MainPresenter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getMonthAndDay() {
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        mView.showDay(String.valueOf(day));
        String[] months = mContext.getResources().getStringArray(R.array.month);
        mView.showMonth(months[month]);
    }

    @Override
    public void getQuotes() {
        quotes = new ArrayList<>();
        String[] sentences = mContext.getResources().getStringArray(R.array.quotes);
        if (sentences.length>0) {
            for (String sentence : sentences) {
                quotes.add(sentence);
            }
            mView.showQuotes(quotes);
        }
    }

    @Override
    public void getBannerImg(int pager, RadioGroup radioGroup) {
        mPagerList = new ArrayList<>();
        for (int i = 0; i < pager; i++) {
            ImageView imageView = new ImageView(mContext);
            imageView.setImageResource(ResourcesUtil.getBackground().get(i));
            mPagerList.add(imageView);

            RadioButton radioButton = new RadioButton(mContext);
            radioButton.setButtonDrawable(android.R.color.transparent);
            radioButton.setBackgroundResource(R.drawable.main_line);
            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(20,10);
            params.setMargins(0, 0, 10, 0);
            radioGroup.addView(radioButton, params);
        }
        mView.showPagerImg(mPagerList);
    }

    @Override
    public RadioGroup.LayoutParams getParams(boolean isClick) {
        RadioGroup.LayoutParams params;
        if (isClick){
            params = new RadioGroup.LayoutParams(40,10);
        }else {
            params = new RadioGroup.LayoutParams(20,10);
        }
        params.setMargins(0, 0, 10, 0);
        return params;
    }

    @Override
    public void checkStudentByStudentID(String token, String mac, int studentID) {
        mModel = new MainModel();
        Observable<FetchBean> observable = mModel.checkStudentByStudentID(token, mac, String.valueOf(studentID));
        observable.subscribe(fetchBean -> {
            if (fetchBean.getCode()==0){
                if (fetchBean.getData().getLend_info().getCount()>0){
                    mView.hasDidNotReturn();
                }else {
                    if (fetchBean.getData().getBook_reserve_info().getCount()>0){
                        mView.hasReserve();
                    }else {
                        mView.noReserve();
                    }
                }
            }else {
            }
        });
    }

    @Override
    public void checkStudentFingerData(String token, String mac, int fingerId) {
        mModel = new MainModel();
        Observable<FetchBean> observable = mModel.checkStudentFingerData(token, mac, String.valueOf(fingerId));
        observable.subscribe(fetchBean -> {
                if (fetchBean.getData().getLend_info().getCount()>0){
                    mView.hasDidNotReturn();
                }else {
                    if (fetchBean.getData().getBook_reserve_info().getCount()>0){
                        mView.hasReserve();
                    }else {
                        mView.noReserve();
                    }
                }
        }, throwable -> {});
    }

}
