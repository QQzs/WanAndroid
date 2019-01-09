package com.zs.demo.commonlib.mvp.home

import com.zs.demo.commonlib.bean.hot.BookmarkBean
import com.zs.demo.commonlib.bean.hot.CommonListBean
import com.zs.demo.commonlib.bean.hot.HotBean
import com.zs.demo.commonlib.bean.hot.HotListBean
import com.zs.demo.commonlib.bean.type.TreeBean
import com.zs.demo.commonlib.mvp.BaseModel
import com.zs.demo.commonlib.mvp.ResultListener
import com.zs.demo.commonlib.mvp.listener.HotResultListener
import com.zs.demo.commonlib.mvp.model.ArticleModel
import com.zs.demo.commonlib.mvp.model.HotModel
import com.zs.demo.commonlib.mvp.model.TypeModel
import com.zs.demo.commonlib.request.BaseImpl
import com.zs.demo.commonlib.request.BaseResponse
import com.zs.demo.commonlib.request.DefaultObserver
import com.zs.demo.commonlib.request.more.MoreObserver
import com.zs.project.bean.android.ArticleBanner
import com.zs.project.bean.android.ArticleList
import io.reactivex.Observable
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
class HomeModel(baseImpl: BaseImpl?): BaseModel(baseImpl), ArticleModel, TypeModel, HotModel {

    override fun articleBanner(bannerListener: ResultListener<MutableList<ArticleBanner>>?) {
        mRequestApi.service
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
        mRequestApi.service
                .getArticleList(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : DefaultObserver<BaseResponse<ArticleList>>(mBaseImpl){

                    override fun onSuccess(response: BaseResponse<ArticleList>?) {
                        articleListener?.onSuccess(response?.data)
                    }

                })
    }

    override fun collectArticle(id: Int, articleListener: ResultListener<Any>?) {
        mRequestApi.service
                .collectArticle(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : DefaultObserver<BaseResponse<Any>>(mBaseImpl){

                    override fun onSuccess(response: BaseResponse<Any>?) {
                        articleListener?.onSuccess(response?.data)
                    }

                })
    }

    override fun unCollectArticle(id: Int, articleListener: ResultListener<Any>?) {
        mRequestApi.service
                .unCollectArticle(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : DefaultObserver<BaseResponse<Any>>(mBaseImpl){

                    override fun onSuccess(response: BaseResponse<Any>?) {
                        articleListener?.onSuccess(response?.data)
                    }

                })

    }

    override fun unCollectArticleList(id: Int , originId : Int, articleListener: ResultListener<Any>?) {
        mRequestApi.service
                .unCollectArticleList(id,originId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : DefaultObserver<BaseResponse<Any>>(mBaseImpl){

                    override fun onSuccess(response: BaseResponse<Any>?) {
                        articleListener?.onSuccess(response?.data)
                    }

                })

    }

    override fun collectList(page: Int, articleListener: ResultListener<ArticleList>?) {
        mRequestApi.service
                .getCollectList(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : DefaultObserver<BaseResponse<ArticleList>>(mBaseImpl){

                    override fun onSuccess(response: BaseResponse<ArticleList>?) {
                        articleListener?.onSuccess(response?.data)
                    }

                })
    }

    override fun typeArticleList(page: Int, cid: Int, articleListener: ResultListener<ArticleList>?) {
        mRequestApi.service
                .getTypeArticleList(page,cid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : DefaultObserver<BaseResponse<ArticleList>>(mBaseImpl){

                    override fun onSuccess(response: BaseResponse<ArticleList>?) {
                        articleListener?.onSuccess(response?.data)
                    }

                })
    }

    override fun getTypeTree(treeListener: ResultListener<MutableList<TreeBean>>?) {
        mRequestApi.service
                .typeTreeList
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : DefaultObserver<BaseResponse<MutableList<TreeBean>>>(mBaseImpl){
                    override fun onSuccess(response: BaseResponse<MutableList<TreeBean>>?) {
                        treeListener?.onSuccess(response?.data)
                    }

                })
    }

    override fun getHotList(hotResultListener: HotResultListener){

        var mRequestService = mRequestApi.service
        var bookmarkResult: MutableList<HotBean> = mutableListOf()
        var hotResult: MutableList<HotBean> = mutableListOf()
        var commonResult: MutableList<HotBean> = mutableListOf()

        Observable.merge(mRequestService.bookmarkList,mRequestService.hotKeyList,mRequestService.commonList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : MoreObserver<Any>(mBaseImpl){
                    override fun onSuccess(response: Any?) {
                        if (response is BookmarkBean){
                            response?.data?.run {
                                bookmarkResult = response?.data!!
                            }
                        }else if (response is HotListBean){
                            response?.data?.run {
                                hotResult = response?.data!!
                            }

                        }else if (response is CommonListBean){
                            response?.data?.run {
                                commonResult = response?.data!!
                            }
                            hotResultListener?.onSuccess(bookmarkResult,hotResult,commonResult)
                        }

                    }
                })

    }


}