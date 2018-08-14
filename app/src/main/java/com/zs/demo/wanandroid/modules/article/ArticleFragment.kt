package com.zs.demo.wanandroid.modules.article

import android.text.TextUtils
import android.view.View
import com.zs.demo.wanandroid.Constant
import com.zs.demo.wanandroid.R
import com.zs.demo.wanandroid.base.BaseFragment
import com.zs.demo.wanandroid.event.BannerEvent
import com.zs.demo.wanandroid.listener.ItemClickListener
import com.zs.demo.wanandroid.modules.article.adapter.ArticleAdapter
import com.zs.demo.wanandroid.modules.article.view.ArticleView
import com.zs.demo.wanandroid.modules.login.LoginActivity
import com.zs.demo.wanandroid.modules.mvp.HomePresenter
import com.zs.demo.wanandroid.utils.FieldUtil
import com.zs.demo.wanandroid.utils.RecyclerViewUtil
import com.zs.demo.wanandroid.utils.SpUtil
import com.zs.demo.wanandroid.view.banner.BannerViewData
import com.zs.demo.wanandroid.view.cxrecyclerview.CXRecyclerView
import com.zs.project.bean.android.Article
import com.zs.project.bean.android.ArticleBanner
import com.zs.project.bean.android.ArticleList
import kotlinx.android.synthetic.main.fragment_article_layout.*
import kotlinx.android.synthetic.main.header_article_layout.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 * Created by zs
 * Date：2018年 08月 08日
 * Time：17:01
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */
class ArticleFragment : BaseFragment() , ArticleView , ItemClickListener{

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
        mArticleAdapter = ArticleAdapter(this)
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

    override fun collectArticleSuccess() {
        activity?.toast("收藏成功")
    }

    override fun unCollectArticleSuccess() {
        activity?.toast("取消收藏")

    }

    override fun onItemClick(position: Int, data: Any?, view: View?) {
        var article = data as Article
        when(view?.id){
            R.id.homeItemLike ->{
                if (TextUtils.isEmpty(SpUtil.getString(Constant.APP_USER_ID,null))){
                    activity?.startActivity<LoginActivity>(FieldUtil.LOGIN to FieldUtil.LOGIN)
                }else{
                    var collect = article.collect
                    if (collect){
                        mPresenter?.unCollectArticle(article.id)
                    }else{
                        mPresenter?.collectArticle(article.id)
                    }
                    article.collect = !collect
                    mArticleAdapter?.updateData(position,article)
                }
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
