package com.hotbitmapgg.bilibili.network1.api;

import com.hotbitmapgg.bilibili.entity.user.UserInterestQuanInfo;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author reghao
 * @date 2022-06-30 11:24:12
 */
public interface BiliGeoService {
    @POST("api/geo")
    Observable<UserInterestQuanInfo> sendDeviceGeo(@Query("mid") int mid);
}
