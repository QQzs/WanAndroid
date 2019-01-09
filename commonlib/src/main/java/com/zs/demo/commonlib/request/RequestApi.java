package com.zs.demo.commonlib.request;

import android.text.TextUtils;
import android.util.Log;

import com.zs.demo.commonlib.app.MyApp;
import com.zs.demo.commonlib.request.cookie.CookieJarImpl;
import com.zs.demo.commonlib.request.cookie.SPCookieStore;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zs
 * Date：2017年 09月 22日
 * Time：10:36
 * —————————————————————————————————————
 * About: 初始化请求Api
 * —————————————————————————————————————
 */
public class RequestApi {

    private final static int CONNECT_TIMEOUT = 20;
    private final static int READ_TIMEOUT = 10;

    public static final String BASE_WAN_ANDROID = "http://www.wanandroid.com/";

    private static RequestApi mRetrofitApi;
    private HttpLoggingInterceptor mLoggingInterceptor;

    public RequestApi() {

    }

    public static RequestApi getInstance() {
        if (mRetrofitApi == null) {
            mRetrofitApi = new RequestApi();
        }
        return mRetrofitApi;
    }

    /**
     * retrofit
     *
     * @param baseurl
     * @return
     */
    public Retrofit getRetrofit(String baseurl) {

        if (TextUtils.isEmpty(baseurl)) {
            baseurl = BASE_WAN_ANDROID;
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseurl)
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())   // 默认转换器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit;
    }

    /**
     * 请求玩Android地址
     *
     * @return
     */
    public RequestService getService() {
        return getRetrofit(BASE_WAN_ANDROID).create(RequestService.class);
    }

    /**
     * OKHttp
     *
     * @return
     */
    private OkHttpClient getOkHttpClient() {
        if (mLoggingInterceptor == null) {
            mLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    //打印retrofit日志
                    Log.i("RetrofitLog", "retrofitBack = " + message);
                }
            });
            mLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        }
// 添加header
//        Interceptor newInterceptor = new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                Request request = chain.request()
//                        .newBuilder()
//                        .addHeader("Authorization", Constant.TOKEN)
//                        .build();
//                return chain.proceed(request);
//            }
//        };

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)//连接失败后是否重新连接
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)//超时时间
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(mLoggingInterceptor)
//                .addInterceptor(newInterceptor)
                .cookieJar(new CookieJarImpl(new SPCookieStore(MyApp.getAppContext())));
        return builder.build();
    }

}
