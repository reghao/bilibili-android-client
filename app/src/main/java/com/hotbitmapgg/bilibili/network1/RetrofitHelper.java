package com.hotbitmapgg.bilibili.network1;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.hotbitmapgg.bilibili.BilibiliApp;
import com.hotbitmapgg.bilibili.network1.api.*;
import com.hotbitmapgg.bilibili.network1.auxiliary.BiliApi;
import com.hotbitmapgg.bilibili.utils.CommonUtil;
import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by hcc on 16/8/4 21:18
 * 100332338@qq.com
 * <p/>
 * Retrofit帮助类
 */
public class RetrofitHelper {
    private static OkHttpClient mOkHttpClient;

    static {
        initOkHttpClient();
    }

    public static BiliVideoService biliVideoService() {
        return createApi(BiliVideoService.class, BiliApi.baseApiUrl);
    }

    public static BiliPlayService biliPlayService() {
        return createApi(BiliPlayService.class, BiliApi.baseFileUrl);
    }

    public static BiliUserService biliUserService() {
        return createApi(BiliUserService.class, BiliApi.baseApiUrl);
    }



    public static BiliLiveService getLiveAPI() {
        return createApi(BiliLiveService.class, BiliApi.LIVE_BASE_URL);
    }

    public static RankService getRankAPI() {
        return createApi(RankService.class, BiliApi.RANK_BASE_URL);
    }

    public static VipService getVipAPI() {
        return createApi(VipService.class, BiliApi.VIP_BASE_URL);
    }

    public static BangumiService getBangumiAPI() {
        return createApi(BangumiService.class, BiliApi.BANGUMI_BASE_URL);
    }

    public static BiliSearchService getSearchAPI() {
        return createApi(BiliSearchService.class, BiliApi.SEARCH_BASE_URL);
    }

    public static BiliAccountService getAccountAPI() {
        return createApi(BiliAccountService.class, BiliApi.ACCOUNT_BASE_URL);
    }

    public static BiliGeoService getIm9API() {
        return createApi(BiliGeoService.class, BiliApi.IM9_BASE_URL);
    }



    /**
     * 根据传入的baseUrl，和api创建retrofit
     */
    private static <T> T createApi(Class<T> clazz, String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(mOkHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(clazz);
    }


    /**
     * 初始化OKHttpClient,设置缓存,设置超时时间,设置打印日志,设置UA拦截器
     */
    private static void initOkHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        if (mOkHttpClient == null) {
            synchronized (RetrofitHelper.class) {
                if (mOkHttpClient == null) {
                    //设置Http缓存
                    Cache cache = new Cache(new File(BilibiliApp.getInstance().getCacheDir(), "HttpCache"), 1024 * 1024 * 10);
                    mOkHttpClient = new OkHttpClient.Builder()
                            .cache(cache)
                            .addInterceptor(interceptor)
                            .addNetworkInterceptor(new CacheInterceptor())
                            .addNetworkInterceptor(new StethoInterceptor())
                            .retryOnConnectionFailure(true)
                            .connectTimeout(30, TimeUnit.SECONDS)
                            .writeTimeout(20, TimeUnit.SECONDS)
                            .readTimeout(20, TimeUnit.SECONDS)
                            .addInterceptor(new UserAgentInterceptor())
                            .build();
                }
            }
        }
    }


    /**
     * 添加UA拦截器，B站请求API需要加上UA才能正常使用
     */
    private static class UserAgentInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();
            Request requestWithUserAgent = originalRequest.newBuilder()
                    .removeHeader("User-Agent")
                    .addHeader("User-Agent", BiliApi.COMMON_UA_STR)
                    .build();
            return chain.proceed(requestWithUserAgent);
        }
    }

    /**
     * 为okhttp添加缓存，这里是考虑到服务器不支持缓存时，从而让okhttp支持缓存
     */
    private static class CacheInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            // 有网络时 设置缓存超时时间1个小时
            int maxAge = 60 * 60;
            // 无网络时，设置超时为1天
            int maxStale = 60 * 60 * 24;
            Request request = chain.request();
            /*if (CommonUtil.isNetworkAvailable(BilibiliApp.getInstance())) {
                //有网络时只从网络获取
                System.out.println("从服务器获取数据");
                request = request.newBuilder().cacheControl(CacheControl.FORCE_NETWORK).build();
            } else {
                //无网络时只从缓存中读取
                System.out.println("从本地缓存获取数据");
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
            }*/
            request = request.newBuilder().cacheControl(CacheControl.FORCE_NETWORK).build();
            Response response = chain.proceed(request);
            /*if (CommonUtil.isNetworkAvailable(BilibiliApp.getInstance())) {
                response = response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                response = response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }*/
            response = response.newBuilder()
                    .removeHeader("Pragma")
                    //.header("Cache-Control", "public, max-age=" + maxAge)
                    .build();
            return response;
        }
    }
}
