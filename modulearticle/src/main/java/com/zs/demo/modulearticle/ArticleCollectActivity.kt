package com.zs.demo.modulearticle

import android.text.TextUtils
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.zs.demo.commonlib.activity.WebViewActivity
import com.zs.demo.commonlib.adapter.ArticleAdapter
import com.zs.demo.commonlib.app.Constant
import com.zs.demo.commonlib.app.RouterPath
import com.zs.demo.commonlib.base.BaseActivity
import com.zs.demo.commonlib.event.LoginEvent
import com.zs.demo.commonlib.listener.ItemClickListener
import com.zs.demo.commonlib.mvp.home.HomePresenter
import com.zs.demo.commonlib.mvp.view.ArticleView
import com.zs.demo.commonlib.utils.FieldUtil
import com.zs.demo.commonlib.utils.RecyclerViewUtil
import com.zs.demo.commonlib.utils.SpUtil
import com.zs.demo.commonlib.view.cxrecyclerview.CXRecyclerView
import com.zs.project.bean.android.Article
import com.zs.project.bean.android.ArticleList
import kotlinx.android.synthetic.main.activity_recycler_layout.*
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 *
Created by zs
Date：2018年 08月 22日
Time：17:02
—————————————————————————————————————
About:
—————————————————————————————————————
 */
@Route(path = RouterPath.ARTICLE_COLLECT_ACTIVITY)
class ArticleCollectActivity: BaseActivity(), ArticleView, ItemClickListener {

    var mStartNum: Int = 0
    var mIsAction: Boolean = false
    var mPresenter: HomePresenter? = null
    var mArticleAdapter: ArticleAdapter? = null

    override fun setLayoutId(): Int {
        return R.layout.activity_recycler_layout
    }

    override fun initView() {

        recycler_view?.setLoadingListener(object : CXRecyclerView.LoadingListener{
            override fun onRefresh() {
                mStartNum = 0
                mPresenter?.getCollectArticle(mStartNum)
            }

            override fun onLoadMore() {
                mStartNum++
                mPresenter?.getCollectArticle(mStartNum)
            }

        })
        mArticleAdapter = ArticleAdapter(true, this)
        RecyclerViewUtil.init(this,recycler_view,mArticleAdapter)

    }

    override fun initData() {

        mPresenter = HomePresenter(this,this)
        showLoading()
        mPresenter?.getCollectArticle(mStartNum)

    }

    override fun onItemClick(position: Int, data: Any?, view: View?) {
        var article = data as Article
        when(view?.id){
            R.id.rl_layout ->{
                startActivity<WebViewActivity>(FieldUtil.WEB_URL to article.link)
            }
            R.id.homeItemLike ->{
                if (TextUtils.isEmpty(SpUtil.getString(Constant.APP_USER_ID,null))){
                    ARouter.getInstance().build(RouterPath.LOGIN_ACTIVITY)
                            .withString(FieldUtil.LOGIN , FieldUtil.LOGIN)
                            .navigation()
//                    startActivity<LoginActivity>(FieldUtil.LOGIN to FieldUtil.LOGIN)
                }else{
                    mArticleAdapter?.deleteData(position,article)
                    mPresenter?.unCollectArticleList(article.id,article.originId)
                }
            }
        }

    }

    override fun getCollectSuccess(articleList: ArticleList?) {
        super.getCollectSuccess(articleList)
        dismissLoading()
        if (mStartNum == 0){
            recycler_view?.refreshComplete()
            articleList?.datas?.run {
                mArticleAdapter?.initData(this)
            }

        } else{
            recycler_view?.loadMoreComplete()
            articleList?.datas?.run {
                mArticleAdapter?.appendData(this)

            }
        }

    }

    override fun unCollectArticleSuccess() {
        super.unCollectArticleSuccess()
        toast("取消收藏")
        mIsAction = true
    }

    override fun onDestroy() {
        super.onDestroy()
        // 如果有取消收藏操作，页面关闭通知文章列表刷新
        if (mIsAction){
            EventBus.getDefault().post(LoginEvent("login"))
        }

    }
}