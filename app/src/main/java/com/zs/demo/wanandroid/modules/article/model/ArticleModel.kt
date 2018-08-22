package com.zs.demo.wanandroid.modules.article.model

import com.zs.demo.wanandroid.mvp.ResultListener
import com.zs.project.bean.android.ArticleBanner
import com.zs.project.bean.android.ArticleList

/**
 *
Created by zs
Date：2018年 08月 02日
Time：17:14
—————————————————————————————————————
About:
—————————————————————————————————————
 */
interface ArticleModel {

    fun articleBanner(bannerListener: ResultListener<MutableList<ArticleBanner>>?)

    fun articleList(page: Int , articleListener: ResultListener<ArticleList>?)

    fun collectArticle(id: Int , articleListener: ResultListener<Any>?)

    fun unCollectArticle(id: Int , articleListener: ResultListener<Any>?)

    fun unCollectArticleList(id: Int , originId : Int , articleListener: ResultListener<Any>?)

    fun collectList(page: Int , articleListener: ResultListener<ArticleList>?)

}