package com.zs.demo.wanandroid.modules.article.view

import com.zs.demo.wanandroid.mvp.BaseView
import com.zs.project.bean.android.ArticleBanner
import com.zs.project.bean.android.ArticleList

/**
 *
Created by zs
Date：2018年 08月 02日
Time：18:01
—————————————————————————————————————
About:
—————————————————————————————————————
 */
interface ArticleView: BaseView {

    fun getBannerSuccess(bannerList: MutableList<ArticleBanner>?)

    fun getArticleSuccess(articleList: ArticleList?)

    fun collectArticleSuccess()

    fun unCollectArticleSuccess()

}