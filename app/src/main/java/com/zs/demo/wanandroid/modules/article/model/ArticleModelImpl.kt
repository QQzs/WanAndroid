package com.zs.demo.wanandroid.modules.article.model

import com.zs.demo.wanandroid.mvp.BaseModel
import com.zs.demo.wanandroid.mvp.ResultListener
import com.zs.demo.wanandroid.request.BaseImpl
import com.zs.demo.wanandroid.request.BaseResponse
import com.zs.demo.wanandroid.request.DefaultObserver
import com.zs.demo.wanandroid.request.RequestApi
import com.zs.project.bean.android.ArticleBanner
import com.zs.project.bean.android.ArticleList
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 *
Created by zs
Date：2018年 08月 09日
Time：11:10
—————————————————————————————————————
About:
—————————————————————————————————————
 */
class ArticleModelImpl(baseImpl: BaseImpl?): BaseModel(baseImpl), ArticleModel {

    override fun articleBanner(bannerListener: ResultListener<MutableList<ArticleBanner>>?) {
        RequestApi.getInstance().service
                .articleBanner
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : DefaultObserver<BaseResponse<MutableList<ArticleBanner>>>(mBaseImpl){

                    override fun onSuccess(response: BaseResponse<MutableList<ArticleBanner>>?) {
                        bannerListener?.onSuccess(response?.data)
                    }
                })
    }

    override fun articleList(page: Int, articleListener: ResultListener<ArticleList>?) {
        RequestApi.getInstance().service
                .getArticleList(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : DefaultObserver<BaseResponse<ArticleList>>(mBaseImpl){

                    override fun onSuccess(response: BaseResponse<ArticleList>?) {
                        articleListener?.onSuccess(response?.data)
                    }

                })
    }


}