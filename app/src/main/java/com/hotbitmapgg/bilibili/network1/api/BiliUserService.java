package com.hotbitmapgg.bilibili.network1.api;

import com.hotbitmapgg.bilibili.entity.user.UserCoinsInfo;
import com.hotbitmapgg.bilibili.entity.user.UserContributeInfo;
import com.hotbitmapgg.bilibili.entity.user.UserPlayGameInfo;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author reghao
 * @date 2022-06-30 10:59:12
 */
public interface BiliUserService {

    /**
     * 用户所玩游戏
     */
    @GET("ajax/game/GetLastPlay")
    Observable<UserPlayGameInfo> getUserPlayGames(@Query("mid") int mid);

    /**
     * 用户投币视频
     */
    @GET("ajax/member/getCoinVideos")
    Observable<UserCoinsInfo> getUserCoinVideos(@Query("mid") int mid);


    /**
     * 用户投稿视频
     */
    @GET("ajax/member/getSubmitVideos")
    Observable<UserContributeInfo> getUserContributeVideos(
            @Query("mid") int mid, @Query("page") int page, @Query("pagesize") int pageSize);
}
