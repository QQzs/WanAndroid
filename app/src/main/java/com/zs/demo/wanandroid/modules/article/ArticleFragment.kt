package com.zs.demo.wanandroid.modules.article

import android.view.View
import com.zs.demo.wanandroid.R
import com.zs.demo.wanandroid.base.BaseFragment
import com.zs.demo.wanandroid.event.BannerEvent
import com.zs.demo.wanandroid.modules.article.adapter.ArticleAdapter
import com.zs.demo.wanandroid.modules.article.view.ArticleView
import com.zs.demo.wanandroid.modules.mvp.HomePresenter
import com.zs.demo.wanandroid.utils.RecyclerViewUtil
import com.zs.demo.wanandroid.view.banner.BannerViewData
import com.zs.demo.wanandroid.view.cxrecyclerview.CXRecyclerView
import com.zs.project.bean.android.ArticleBanner
import com.zs.project.bean.android.ArticleList
import kotlinx.android.synthetic.main.fragment_article_layout.*
import kotlinx.android.synthetic.main.header_article_layout.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

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

    var mPresenter: HomePresenter? = null
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
        EventBus.getDefault().register(this)
        mPresenter = HomePresenter(this,this)
        showLoading()
        mPresenter?.getBanner()
        mPresenter?.getArticle(mStartNum)

    }

    override fun getBannerSuccess(bannerList: MutableList<ArticleBanner>?) {
        var items = mutableListOf<BannerViewData>()
        bannerList?.run {
            bannerList.mapTo(items){BannerViewData(it.title,it.imagePath,it.url)}
            mHeadView?.banner_page?.initData(items)
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun initBanner(event: BannerEvent){
        if (event.mInit as Boolean){
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.onDestroyView()
        EventBus.getDefault().unregister(this)
    }

}
