package com.zs.demo.wanandroid.modules.article.bean

import android.view.View
import android.view.ViewGroup
import com.zs.demo.wanandroid.R
import com.zs.demo.wanandroid.view.banner.BannerEntry
import com.zs.project.bean.android.ArticleBanner

/**
 *
Created by zs
Date：2018年 08月 09日
Time：16:43
—————————————————————————————————————
About:
—————————————————————————————————————
 */
class ArticleBannerEntry: BannerEntry<ArticleBanner?>{

//    private var title: String?= null
//    private var imagePath: String?= null
//    private var desc: String?= null
//    private var url: String?= null
    private var mBanner: ArticleBanner? = null

    constructor(mBanner: ArticleBanner?) {
        this.mBanner = mBanner
    }

    override fun onCreateView(parent: ViewGroup?): View {
        val view = View.inflate(parent?.context,R.layout.banner_item_layout,null)
        return view
    }

    override fun getTitle(): CharSequence? {
        return mBanner?.title
    }

    override fun getSubTitle(): CharSequence? {
        return mBanner?.title
    }

    override fun getValue(): ArticleBanner? {
        return mBanner
    }

    override fun same(newEntry: BannerEntry<*>?): Boolean {
        return false
    }

}