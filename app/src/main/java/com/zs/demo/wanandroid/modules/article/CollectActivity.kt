package com.zs.demo.wanandroid.modules.article

import android.text.TextUtils
import android.view.View
import com.zs.demo.wanandroid.Constant
import com.zs.demo.wanandroid.R
import com.zs.demo.wanandroid.base.BaseActivity
import com.zs.demo.wanandroid.listener.ItemClickListener
import com.zs.demo.wanandroid.modules.WebViewActivity
import com.zs.demo.wanandroid.modules.article.adapter.ArticleAdapter
import com.zs.demo.wanandroid.modules.article.view.ArticleView
import com.zs.demo.wanandroid.modules.login.LoginActivity
import com.zs.demo.wanandroid.modules.mvp.HomePresenter
import com.zs.demo.wanandroid.utils.FieldUtil
import com.zs.demo.wanandroid.utils.RecyclerViewUtil
import com.zs.demo.wanandroid.utils.SpUtil
import com.zs.demo.wanandroid.view.cxrecyclerview.CXRecyclerView
import com.zs.project.bean.android.Article
import com.zs.project.bean.android.ArticleList
import kotlinx.android.synthetic.main.activity_recycler_layout.*
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
class CollectActivity: BaseActivity(), ArticleView , ItemClickListener {

    var mStartNum: Int = 0
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
        mArticleAdapter = ArticleAdapter(true,this)
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
                    startActivity<LoginActivity>(FieldUtil.LOGIN to FieldUtil.LOGIN)
                }else{
                    mArticleAdapter?.deleteData(article)
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
    }
}