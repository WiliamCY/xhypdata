package com.example.commons.http;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 许格.
 * Date on 2019/7/25.
 * dec:
 */
public class RetrofitClient {

    private OkHttpClient okHttpClient;

    private int TIMEOUT = 2;

    public static RetrofitClient getInstance() {
        return SingleHolder.retrofit;
    }

    public static class SingleHolder{
        public static RetrofitClient retrofit = new RetrofitClient();
    }

    public RetrofitClient() {
        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                .build();
    }

    public <T> T getApiService(Class<T> clazz){
        Retrofit retrofit = new Retrofit.Builder().client(okHttpClient)
                .baseUrl("http://118.31.23.47:12480/api.php/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(clazz);
    }

    //处理线程调度的变换
    public ObservableTransformer schedulersTransformer = upstream -> upstream.subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
}
