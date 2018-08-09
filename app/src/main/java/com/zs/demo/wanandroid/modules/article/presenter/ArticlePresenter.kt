package com.zs.demo.wanandroid.modules.article.presenter

import com.zs.demo.wanandroid.mvp.BasePresenter

/**
 *
Created by zs
Date：2018年 08月 09日
Time：11:23
—————————————————————————————————————
About:
—————————————————————————————————————
 */
interface ArticlePresenter: BasePresenter {

    fun getBanner()

    fun getArticle(page: Int)

}