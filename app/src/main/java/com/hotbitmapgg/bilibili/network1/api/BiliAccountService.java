package com.hotbitmapgg.bilibili.network1.api;

import com.hotbitmapgg.bilibili.entity.user.UserDetailsInfo;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author reghao
 * @date 2022-06-30 10:59:12
 */
public interface BiliAccountService {

    /**
     * 用户详情数据
     */
    @GET("api/user/getCardByMid")
    Observable<UserDetailsInfo> getUserInfoById(@Query("mid") int mid);
}
