package com.zs.demo.wanandroid.modules.article.presenter

import com.zs.demo.wanandroid.modules.article.model.ArticleModelImpl
import com.zs.demo.wanandroid.modules.article.view.ArticleView
import com.zs.demo.wanandroid.mvp.ResultListener
import com.zs.demo.wanandroid.request.BaseImpl
import com.zs.project.bean.android.ArticleBanner
import com.zs.project.bean.android.ArticleList

/**
 *
Created by zs
Date：2018年 08月 09日
Time：11:24
—————————————————————————————————————
About:
—————————————————————————————————————
 */
class ArticlePresenterImpl: ArticlePresenter{

    var mView: ArticleView? = null
    var mModel: ArticleModelImpl? = null

    constructor(view: ArticleView , baseImpl: BaseImpl) {
        this.mView = view
        mModel = ArticleModelImpl(baseImpl)

    }

    override fun getBanner() {
        mModel?.articleBanner(object : ResultListener<MutableList<ArticleBanner>>{
            override fun onSuccess(t: MutableList<ArticleBanner>?) {
                mView?.getBannerSuccess(t)
            }

        })
    }

    override fun getArticle(page: Int) {
        mModel?.articleList(page , object : ResultListener<ArticleList>{
            override fun onSuccess(t: ArticleList?) {
                mView?.getArticleSuccess(t)
            }

        })
    }

    override fun onDestroyView() {
        mView = null
    }
}