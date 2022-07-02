package com.hotbitmapgg.bilibili.adapter.pager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.hotbitmapgg.bilibili.module.home.attention.HomeAttentionFragment;
import com.hotbitmapgg.bilibili.module.home.bangumi.HomeBangumiFragment;
import com.hotbitmapgg.bilibili.module.home.discover.HomeDiscoverFragment;
import com.hotbitmapgg.bilibili.module.home.live.HomeLiveFragment;
import com.hotbitmapgg.bilibili.module.home.recommend.HomeRecommendedFragment;
import com.hotbitmapgg.bilibili.module.home.region.HomeRegionFragment;
import com.hotbitmapgg.ohmybilibili.R;

/**
 * MainActivity 中的滑动栏
 *
 * @author reghao
 * @date 2022-07-02 01:42:12
 */
public class HomeSlidingPagerAdapter extends FragmentPagerAdapter {
    private final String[] slidingSections;
    private final Fragment[] fragments;

    public HomeSlidingPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        // 获取 res/values/strings.xml 中的 sliding_sections 值
        slidingSections = context.getResources().getStringArray(R.array.sliding_sections);
        fragments = new Fragment[slidingSections.length];
    }

    @Override
    public Fragment getItem(int position) {
        if (fragments[position] == null) {
            switch (position) {
                case 0:
                    // 直播
                    fragments[position] = HomeLiveFragment.newIntance();
                    break;
                case 1:
                    // 推荐
                    fragments[position] = HomeRecommendedFragment.newInstance();
                    break;
                case 2:
                    // 分区
                    fragments[position] = HomeRegionFragment.newInstance();
                    break;
                case 3:
                    // 发现
                    fragments[position] = HomeDiscoverFragment.newInstance();
                    break;
                default:
                    break;
            }
        }

        return fragments[position];
    }

    @Override
    public int getCount() {
        return slidingSections.length;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return slidingSections[position];
    }
}
