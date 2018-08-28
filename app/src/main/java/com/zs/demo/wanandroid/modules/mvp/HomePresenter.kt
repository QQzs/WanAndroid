package com.zs.demo.wanandroid.modules.mvp

import com.zs.demo.wanandroid.modules.article.view.ArticleView
import com.zs.demo.wanandroid.modules.hot.bean.HotBean
import com.zs.demo.wanandroid.modules.hot.listener.HotResultListener
import com.zs.demo.wanandroid.modules.hot.view.HotView
import com.zs.demo.wanandroid.modules.type.bean.TreeBean
import com.zs.demo.wanandroid.modules.type.view.TypeView
import com.zs.demo.wanandroid.mvp.BasePresenter
import com.zs.demo.wanandroid.mvp.ResultListener
import com.zs.demo.wanandroid.request.BaseImpl
import com.zs.project.bean.android.ArticleBanner
import com.zs.project.bean.android.ArticleList

/**
 *
Created by zs
Date：2018年 08月 13日
Time：11:11
—————————————————————————————————————
About:
—————————————————————————————————————
 */
class HomePresenter: BasePresenter{

    var mArticleView: ArticleView? = null
    var mTypeView: TypeView? = null
    var mHotView: HotView?= null
    var mModel: HomeModel? = null

    constructor(mArticleView: ArticleView?, baseImpl: BaseImpl) {
        this.mArticleView = mArticleView
        this.mModel = HomeModel(baseImpl)
    }

    constructor(mTypeView: TypeView?, baseImpl: BaseImpl) {
        this.mTypeView = mTypeView
        this.mModel = HomeModel(baseImpl)
    }

    constructor(mHotView: HotView?, baseImpl: BaseImpl) {
        this.mHotView = mHotView
        this.mModel = HomeModel(baseImpl)
    }

    fun getBanner(){
        mModel?.articleBanner(object : ResultListener<MutableList<ArticleBanner>>{
            override fun onSuccess(t: MutableList<ArticleBanner>?) {
                mArticleView?.getBannerSuccess(t)

            }

        })
    }

    fun getArticle(page: Int){
        mModel?.articleList(page , object : ResultListener<ArticleList>{
            override fun onSuccess(t: ArticleList?) {
                mArticleView?.getArticleSuccess(t)
            }

        })
    }

    fun getCollectArticle(page: Int){
        mModel?.collectList(page , object : ResultListener<ArticleList>{
            override fun onSuccess(t: ArticleList?) {
                mArticleView?.getCollectSuccess(t)
            }

        })
    }


    fun collectArticle(id: Int){
        mModel?.collectArticle(id , object : ResultListener<Any>{
            override fun onSuccess(t: Any?) {
                mArticleView?.collectArticleSuccess()
            }

        })
    }

    fun unCollectArticle(id: Int){
        mModel?.unCollectArticle(id , object : ResultListener<Any>{
            override fun onSuccess(t: Any?) {
                mArticleView?.unCollectArticleSuccess()
            }

        })
    }

    fun unCollectArticleList(id: Int , originId : Int){
        mModel?.unCollectArticleList(id , originId, object : ResultListener<Any>{
            override fun onSuccess(t: Any?) {
                mArticleView?.unCollectArticleSuccess()
            }

        })
    }

    fun getTypeArticleList(page: Int, cid: Int){
        mModel?.typeArticleList(page , cid , object : ResultListener<ArticleList>{
            override fun onSuccess(t: ArticleList?) {
                mArticleView?.getTypeArticleSuccess(t)
            }

        })
    }


    fun getTypeTree(){
        mModel?.getTypeTree(object : ResultListener<MutableList<TreeBean>>{
            override fun onSuccess(t: MutableList<TreeBean>?) {
                mTypeView?.getTreeSuccess(t)
            }

        })
    }

    fun getHotList(){
        mModel?.getHotList(object : HotResultListener{
            override fun onSuccess(bookmark: MutableList<HotBean>?, hotList: MutableList<HotBean>?, commonList: MutableList<HotBean>?) {
                mHotView?.getHotSuccess(bookmark,hotList,commonList)
            }

        })
    }

    override fun onDestroyView() {
        mArticleView = null
        mTypeView = null
        mHotView = null
    }

}