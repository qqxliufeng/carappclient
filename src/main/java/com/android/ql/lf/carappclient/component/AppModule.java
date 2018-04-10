package com.android.ql.lf.carappclient.component;

import android.support.annotation.NonNull;
import android.util.Log;

import com.android.ql.lf.carappclient.application.CarAppClientApplication;
import com.android.ql.lf.carappclient.utils.Constants;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * @author Administrator
 * @date 2017/10/16 0016
 */
@Module
public class AppModule {

    private CarAppClientApplication myApplication;

    public AppModule(CarAppClientApplication myApplication) {
        this.myApplication = myApplication;
    }

    @Singleton
    @Provides
    public OkHttpClient createOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(@NonNull String message) {
                Log.e("HTTP", message);
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(loggingInterceptor);
        builder.readTimeout(10, TimeUnit.SECONDS);
        builder.connectTimeout(10, TimeUnit.SECONDS);
        return builder.build();
    }


    @Singleton
    @Provides
    public Retrofit createRetrofit(OkHttpClient okHttpClient) {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.client(okHttpClient);
        builder.addConverterFactory(ScalarsConverterFactory.create());
        builder.addCallAdapterFactory(RxJavaCallAdapterFactory.create());
        builder.baseUrl(Constants.BASE_IP);
        return builder.build();
    }
}
