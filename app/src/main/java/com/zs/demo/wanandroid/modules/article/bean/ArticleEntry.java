package com.zs.demo.wanandroid.modules.article.bean;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zs.demo.wanandroid.R;
import com.zs.demo.wanandroid.view.banner.BannerEntry;
import com.zs.project.bean.android.ArticleBanner;

/**
 * 创建人 kelin
 * 创建时间 2017/7/25  下午5:12
 * 版本 v 1.0.0
 */

public class ArticleEntry implements BannerEntry<String> {

    private String title;
    private String imgUrl;
    private String alt;
    private ArticleBanner mBanner;

    public ArticleEntry(String title, String imgUrl, String alt) {
        this.title = title;
        this.imgUrl = imgUrl;
        this.alt = alt;
    }

    public ArticleEntry(ArticleBanner mBanner) {
        this.mBanner = mBanner;
    }

    /**
     * 获取当前页面的布局视图。
     *
     * @param parent 当前的布局视图的父节点布局。
     * @return 返回当前页面所要显示的View。
     */
    @Override
    public View onCreateView(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.banner_item_layout, parent, false);
//        ImageView imageView = view.findViewById(R.id.iv_image);
//        ImageLoaderUtil.displayImage(imgUrl,imageView);
        return view;
    }

    /**
     * 获取标题。
     *
     * @return 返回当前条目的标题。
     */
    @Override
    public String getTitle() {
        return mBanner.getTitle();
    }

    private String getImgUrl() {
        return imgUrl;
    }

    /**
     * 获取子标题。
     *
     * @return 返回当前条目的子标题。
     */
    @Nullable
    @Override
    public String getSubTitle() {
        return null;
    }

    /**
     * 获取当前页面的数据。改方法为辅助方法，是为了方便使用者调用而提供的，Api本身并没有任何调用。如果你不需要该方法可以空实现。
     *
     * @return 返回当前页面的数据。
     */
    @Override
    public String getValue() {
        return title;
    }

    @Override
    public boolean same(BannerEntry newEntry) {
        return false;
    }
}
