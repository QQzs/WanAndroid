package com.zs.demo.wanandroid.modules.type

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.zs.demo.wanandroid.Constant
import com.zs.demo.wanandroid.R
import com.zs.demo.wanandroid.base.BaseFragment
import com.zs.demo.wanandroid.event.LoginEvent
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
import kotlinx.android.synthetic.main.fragment_article_layout.*
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
 * About:体系中文章列表
 * —————————————————————————————————————
 */
class TypeArticleFragment : BaseFragment() , ArticleView , ItemClickListener{

    var mStartNum: Int = 0
    var mCid: Int = 0
    var mPresenter: HomePresenter? = null
    var mArticleAdapter: ArticleAdapter? = null

    companion object {
        fun newInstance(cid: Int): TypeArticleFragment {
            val fragment = TypeArticleFragment()
            val args = Bundle()
            args.putInt(FieldUtil.CID, cid)
            fragment.arguments = args
            return fragment
        }
    }

    override fun setLayoutId(): Int {
        return R.layout.fragment_article_layout
    }

    override fun initView() {
        arguments?.run {
            mCid = getInt(FieldUtil.CID)
        }
        recycler_article?.setLoadingListener(object : CXRecyclerView.LoadingListener{
            override fun onRefresh() {
                mStartNum = 0
                mPresenter?.getTypeArticleList(mStartNum,mCid)
            }

            override fun onLoadMore() {
                mStartNum++
                mPresenter?.getTypeArticleList(mStartNum,mCid)
            }

        })
        mArticleAdapter = ArticleAdapter(this)
        RecyclerViewUtil.init(activity,recycler_article,mArticleAdapter)
    }

    override fun initData() {
        EventBus.getDefault().register(this)
        mPresenter = HomePresenter(this,this)
//        showLoading()
        mPresenter?.getTypeArticleList(mStartNum,mCid)

    }

    override fun getTypeArticleSuccess(articleList: ArticleList?) {
        super.getTypeArticleSuccess(articleList)
//        dismissLoading()
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
                activity?.startActivity<WebViewActivity>(FieldUtil.WEB_URL to article.link)
            }
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
                    mArticleAdapter?.updateDataType(position,article)
                }
            }
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun loginResult(event: LoginEvent){
        mStartNum = 0
        mPresenter?.getTypeArticleList(mStartNum,mCid)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.onDestroyView()
        EventBus.getDefault().unregister(this)
    }

}
