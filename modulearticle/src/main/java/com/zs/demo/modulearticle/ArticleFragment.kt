package com.zs.demo.modulearticle

import android.support.v4.app.Fragment
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.zs.demo.commonlib.adapter.ArticleAdapter
import com.zs.demo.commonlib.app.RouterPath
import com.zs.demo.commonlib.base.BaseFragment
import com.zs.demo.commonlib.bean.type.TreeBean
import com.zs.demo.commonlib.event.LoginEvent
import com.zs.demo.commonlib.listener.ItemClickListener
import com.zs.demo.commonlib.mvp.home.HomePresenter
import com.zs.demo.commonlib.mvp.view.ArticleView
import com.zs.demo.commonlib.utils.FieldUtil
import com.zs.demo.commonlib.utils.IntentUtil
import com.zs.demo.commonlib.utils.RecyclerViewUtil
import com.zs.demo.commonlib.view.banner.BannerViewData
import com.zs.demo.commonlib.view.cxrecyclerview.CXRecyclerView
import com.zs.project.bean.android.Article
import com.zs.project.bean.android.ArticleBanner
import com.zs.project.bean.android.ArticleList
import kotlinx.android.synthetic.main.fragment_article_layout.*
import kotlinx.android.synthetic.main.header_article_layout.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.toast
import java.io.Serializable

/**
 * Created by zs
 * Date：2018年 08月 08日
 * Time：17:01
 * —————————————————————————————————————
 * About:文章列表
 * —————————————————————————————————————
 */
@Route(path = RouterPath.ARTICLE_FRAGMENT)
class ArticleFragment : BaseFragment() , ArticleView, ItemClickListener{

    var mStartNum: Int = 0

    var mHeadView : View?= null

    var mPresenter: HomePresenter? = null
    var mArticleAdapter: ArticleAdapter? = null

    companion object {
        fun getArtileFragment() : Fragment?{
            return ARouter.getInstance().build(RouterPath.ARTICLE_FRAGMENT).navigation() as ArticleFragment?
        }
    }


    override fun setLayoutId(): Int {
        return R.layout.fragment_article_layout
    }

    override fun initView() {
        recycler_article?.setLoadingListener(object : CXRecyclerView.LoadingListener{
            override fun onRefresh() {
                mStartNum = 0
                mPresenter?.getBanner()
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
            mHeadView?.banner_page?.setOnBannerListener { position, obj ->
                if (IntentUtil.isLogin(RouterPath.COMMON_PAGE_ACTIVITY)){
                    var titles = mutableListOf<TreeBean.Children>()
                    titles.add(TreeBean.Children(0,getString(R.string.taskNotDo)))
                    titles.add(TreeBean.Children(1,getString(R.string.taskToDo)))
                    ARouter.getInstance().build(RouterPath.COMMON_PAGE_ACTIVITY)
                            .withString(FieldUtil.TYPE , "task")
                            .withSerializable(FieldUtil.TITLE_DATE , titles as Serializable)
                            .navigation()
                }
            }
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
            R.id.rl_layout ->{
                ARouter.getInstance().build(RouterPath.COMMON_WEBVIEW)
                        .withString(FieldUtil.WEB_URL , article.link)
                        .navigation()
            }
            R.id.homeItemLike ->{
                
                if (IntentUtil.isLogin()){
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
    fun loginResult(event: LoginEvent){
        mStartNum = 0
        mPresenter?.getArticle(mStartNum)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.onDestroyView()
        EventBus.getDefault().unregister(this)
    }

}
