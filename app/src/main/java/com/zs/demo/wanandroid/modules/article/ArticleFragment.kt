package com.zs.demo.wanandroid.modules.article

import android.view.View
import com.zs.demo.wanandroid.R
import com.zs.demo.wanandroid.base.BaseFragment
import com.zs.demo.wanandroid.modules.article.adapter.ArticleAdapter
import com.zs.demo.wanandroid.modules.article.bean.ArticleEntry
import com.zs.demo.wanandroid.modules.article.presenter.ArticlePresenter
import com.zs.demo.wanandroid.modules.article.presenter.ArticlePresenterImpl
import com.zs.demo.wanandroid.modules.article.view.ArticleView
import com.zs.demo.wanandroid.utils.RecyclerViewUtil
import com.zs.demo.wanandroid.utils.tranform.DepthPageTransformer
import com.zs.demo.wanandroid.view.banner.BannerEntry
import com.zs.demo.wanandroid.view.banner.view.BannerView
import com.zs.demo.wanandroid.view.cxrecyclerview.CXRecyclerView
import com.zs.project.bean.android.ArticleBanner
import com.zs.project.bean.android.ArticleList
import kotlinx.android.synthetic.main.fragment_article_layout.*
import kotlinx.android.synthetic.main.header_article_layout.view.*

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

    var mHeadView : View?= null

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
        mArticleAdapter = ArticleAdapter()
        RecyclerViewUtil.init(activity,recycler_article,mArticleAdapter)
        mHeadView = View.inflate(activity,R.layout.header_article_layout,null)
        recycler_article?.addHeaderView(mHeadView)
    }

    override fun initData() {
        mPresenter = ArticlePresenterImpl(this,this)
        showLoading()
//        mPresenter?.getBanner()
        mPresenter?.getArticle(mStartNum)

    }

    /**
     * 获取banner数据
     */
    private fun getbannerData(data : MutableList<ArticleBanner>) : MutableList<ArticleEntry>{
        var items = ArrayList<ArticleEntry>()
        data.mapTo(items) { ArticleEntry(it.title,it.imagePath,it.url) }
        return items
    }

    override fun getBannerSuccess(bannerList: MutableList<ArticleBanner>?) {
        bannerList?.run {
            mHeadView?.banner_view_top?.setPageTransformer(true, DepthPageTransformer())
            mHeadView?.banner_view_top?.entries = getbannerData(this)
            mHeadView?.banner_view_top?.setOnPageClickListener(object : BannerView.OnPageClickListener(){
                override fun onPageClick(entry: BannerEntry<*>?, index: Int) {

                }
            })
        }
    }

    override fun getArticleSuccess(articleList: ArticleList?) {
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
