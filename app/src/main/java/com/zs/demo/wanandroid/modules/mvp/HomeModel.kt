package com.zs.demo.wanandroid.modules.mvp

import com.zs.demo.wanandroid.modules.article.model.ArticleModel
import com.zs.demo.wanandroid.modules.hot.bean.BookmarkBean
import com.zs.demo.wanandroid.modules.hot.bean.CommonListBean
import com.zs.demo.wanandroid.modules.hot.bean.HotBean
import com.zs.demo.wanandroid.modules.hot.bean.HotListBean
import com.zs.demo.wanandroid.modules.hot.listener.HotResultListener
import com.zs.demo.wanandroid.modules.hot.model.HotModel
import com.zs.demo.wanandroid.modules.type.bean.TreeBean
import com.zs.demo.wanandroid.modules.type.model.TypeModel
import com.zs.demo.wanandroid.mvp.BaseModel
import com.zs.demo.wanandroid.mvp.ResultListener
import com.zs.demo.wanandroid.request.BaseImpl
import com.zs.demo.wanandroid.request.BaseResponse
import com.zs.demo.wanandroid.request.DefaultObserver
import com.zs.demo.wanandroid.request.RequestApi
import com.zs.demo.wanandroid.request.more.MoreObserver
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
class HomeModel(baseImpl: BaseImpl?): BaseModel(baseImpl), ArticleModel,TypeModel, HotModel {

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

    override fun collectArticle(id: Int, articleListener: ResultListener<Any>?) {
        RequestApi.getInstance().service
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
        RequestApi.getInstance().service
                .unCollectArticle(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : DefaultObserver<BaseResponse<Any>>(mBaseImpl){

                    override fun onSuccess(response: BaseResponse<Any>?) {
                        articleListener?.onSuccess(response?.data)
                    }

                })

    }

    override fun getTypeTree(treeListener: ResultListener<MutableList<TreeBean>>?) {
        RequestApi.getInstance().service
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

        var mRequestService = RequestApi.getInstance().service
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