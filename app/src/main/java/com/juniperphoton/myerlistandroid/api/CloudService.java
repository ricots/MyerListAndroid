package com.juniperphoton.myerlistandroid.api;

import com.juniperphoton.myerlistandroid.api.response.CheckUserResponse;
import com.juniperphoton.myerlistandroid.api.response.GetSaltResponse;
import com.juniperphoton.myerlistandroid.api.response.LoginResponse;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

@SuppressWarnings("UnusedDeclaration")
public class CloudService {
    public static final String BASE_URL = "http://juniperphoton.net/schedule/";
    private static final int DEFAULT_TIMEOUT = 10;

    private Retrofit mRetrofit;

    public CloudService() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        mRetrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
    }

    private static class SingletonHolder {
        private static final CloudService INSTANCE = new CloudService();
    }

    public static CloudService getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public Observable<CheckUserResponse> checkUserExist(String email) {
        return mRetrofit.create(UserService.class).checkUserExist(email);
    }

    public Observable<GetSaltResponse> getSalt(String email) {
        return mRetrofit.create(UserService.class).getSalt(email);
    }

    public Observable<LoginResponse> login(String email, String pwd) {
        return mRetrofit.create(UserService.class).login(email, pwd);
    }
}
