package com.hotbitmapgg.bilibili.network1.api;

import com.hotbitmapgg.bilibili.entity.recommend.RecommendBannerInfo;
import com.hotbitmapgg.bilibili.entity.recommend.RecommendInfo;
import com.hotbitmapgg.bilibili.entity.region.RegionDetailsInfo;
import com.hotbitmapgg.bilibili.entity.region.RegionRecommendInfo;
import com.hotbitmapgg.bilibili.entity.search.*;
import com.hotbitmapgg.bilibili.entity.video.VideoDetailsInfo;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author reghao
 * @date 2022-06-30 10:59:12
 */
public interface BiliVideoService {
    /**
     * 首页推荐banner
     */
    @GET("api/media/m/banner?plat=4&build=411007&channel=bilih5")
    Observable<RecommendBannerInfo> getRecommendedBannerInfo();

    /**
     * 首页推荐数据
     */
    @GET("api/media/m/recommend?platform=android&device=&build=412001")
    Observable<RecommendInfo> getRecommendedInfo();

    /**
     * 视频详情数据
     */
    @GET("api/media/m/video?access_key=19946e1ef3b5cad1a756c475a67185bb&actionKey=appkey&appkey=27eb53fc9058f8c3&build=3940&device=phone&mobi_app=iphone&platform=ios&sign=1206255541e2648c1badb87812458046&ts=1478349831")
    Observable<VideoDetailsInfo> getVideoDetail(@Query("videoId") long videoId);

    /**
     * 分区推荐
     */
    @GET("x/v2/region/show?access_key=67cbf6a1e9ad7d7f11bfbd918e50c837&actionKey=appkey&appkey=27eb53fc9058f8c3&build=3600&device=phone&mobi_app=iphone&plat=1&platform=ios&sign=959d7b8c09c65e7a66f7e58b1a2bdab9&ts=1472310694")
    Observable<RegionRecommendInfo> getRegionRecommends(@Query("rid") int rid);

    /**
     * 分区类型详情
     */
    @GET("x/v2/region/show/child?build=3600")
    Observable<RegionDetailsInfo> getRegionDetails(@Query("rid") int rid);
}
