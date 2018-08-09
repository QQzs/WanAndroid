package com.zs.demo.wanandroid.modules.article

import com.zs.demo.wanandroid.R
import com.zs.demo.wanandroid.base.BaseFragment
import com.zs.demo.wanandroid.modules.article.adapter.ArticleAdapter
import com.zs.demo.wanandroid.modules.article.presenter.ArticlePresenter
import com.zs.demo.wanandroid.modules.article.presenter.ArticlePresenterImpl
import com.zs.demo.wanandroid.modules.article.view.ArticleView
import com.zs.demo.wanandroid.utils.LogUtil
import com.zs.demo.wanandroid.utils.RecyclerViewUtil
import com.zs.demo.wanandroid.view.cxrecyclerview.CXRecyclerView
import com.zs.project.bean.android.ArticleBanner
import com.zs.project.bean.android.ArticleList
import kotlinx.android.synthetic.main.fragment_article_layout.*

/**
 * Created by zs
 * Date：2018年 08月 08日
 * Time：17:01
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */
class ArticleFragment : BaseFragment() , ArticleView{

    var mStartNum: Int = 0

    var mPresenter: ArticlePresenter? = null
    var mArticleAdapter: ArticleAdapter? = null

    override fun setLayoutId(): Int {
        return R.layout.fragment_article_layout
    }

    override fun initView() {
        recycler_article?.setLoadingListener(object : CXRecyclerView.LoadingListener{
            override fun onRefresh() {
                mStartNum = 0
                mPresenter?.getArticle(mStartNum)
            }

            override fun onLoadMore() {
                mStartNum++
                mPresenter?.getArticle(mStartNum)
            }

        })
    }

    override fun initData() {
        mPresenter = ArticlePresenterImpl(this,this)
        showLoading()
        mPresenter?.getBanner()
        mPresenter?.getArticle(mStartNum)

        mArticleAdapter = ArticleAdapter()
        RecyclerViewUtil.init(activity,recycler_article,mArticleAdapter)
    }

    override fun getBannerSuccess(bannerList: MutableList<ArticleBanner>?) {
        LogUtil.logShow(bannerList.toString())
    }

    override fun getArticleSuccess(articleList: ArticleList?) {
        LogUtil.logShow(articleList.toString())
        dismissLoading()
        if (mStartNum == 0){
            recycler_article?.refreshComplete()
            articleList?.datas?.run {
                mArticleAdapter?.initData(this)
            }

        } else{
            recycler_article?.loadMoreComplete()
            articleList?.datas?.run {
                mArticleAdapter?.appendData(this)
            }
        }


    }

    override fun onDestroy() {
        super.onDestroy()

    }

}
