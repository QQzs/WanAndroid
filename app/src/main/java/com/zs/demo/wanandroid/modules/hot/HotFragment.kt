package com.zs.demo.wanandroid.modules.hot

import android.view.View
import com.zs.demo.wanandroid.R
import com.zs.demo.wanandroid.base.BaseFragment
import com.zs.demo.wanandroid.modules.WebViewActivity
import com.zs.demo.wanandroid.modules.hot.adapter.HotTagAdapter
import com.zs.demo.wanandroid.modules.hot.bean.HotBean
import com.zs.demo.wanandroid.modules.hot.view.HotView
import com.zs.demo.wanandroid.modules.mvp.HomePresenter
import com.zs.demo.wanandroid.utils.FieldUtil
import kotlinx.android.synthetic.main.fragment_hot_layout.*
import org.jetbrains.anko.startActivity

/**
 * Created by zs
 * Date：2018年 08月 08日
 * Time：17:01
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */
class HotFragment : BaseFragment(), HotView{

    var mPresenter: HomePresenter? = null

    var mBookAdapter: HotTagAdapter? = null
    var mHotAdapter: HotTagAdapter? = null
    var mCommonAdapter: HotTagAdapter? = null

    override fun setLayoutId(): Int {
        return R.layout.fragment_hot_layout
    }

    override fun initView() {
        swipe_refresh_view?.run {
            setOnRefreshListener {
                mPresenter?.getHotList()
            }
        }

    }

    override fun initData() {
        mPresenter = HomePresenter(this, this)
        showLoading()
        mPresenter?.getHotList()
    }

    override fun getHotSuccess(bookmark: MutableList<HotBean>?, hotList: MutableList<HotBean>?, commonList: MutableList<HotBean>?) {
        dismissLoading()
        swipe_refresh_view?.isRefreshing = false
        mBookAdapter = HotTagAdapter(context, bookmark)
        mHotAdapter = HotTagAdapter(context, hotList)
        mCommonAdapter = HotTagAdapter(context, commonList)

        if (bookmark == null || bookmark.size == 0) {
            bookmarkTitle?.visibility = View.GONE
            bookmarkFlowLayout?.visibility = View.GONE
        }

        bookmarkFlowLayout?.run {
            adapter = mBookAdapter
            setOnTagClickListener { _, position, _ ->
                activity?.startActivity<WebViewActivity>(FieldUtil.WEB_URL to bookmark?.get(position)?.link)
                true
            }
        }

        hotFlowLayout?.run {
            adapter = mHotAdapter
            setOnTagClickListener { _, position, _ ->
                activity?.startActivity<WebViewActivity>(FieldUtil.WEB_URL to hotList?.get(position)?.link)
                true
            }
        }

        commonUseFlowLayout?.run {
            adapter = mCommonAdapter
            setOnTagClickListener { _, position, _ ->
                activity?.startActivity<WebViewActivity>(FieldUtil.WEB_URL to commonList?.get(position)?.link)
                true
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.onDestroyView()

    }

}
