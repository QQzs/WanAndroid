package com.zs.demo.modulehot

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.zs.demo.commonlib.activity.WebViewActivity
import com.zs.demo.commonlib.adapter.HotTagAdapter
import com.zs.demo.commonlib.app.RouterPath
import com.zs.demo.commonlib.base.BaseFragment
import com.zs.demo.commonlib.bean.hot.HotBean
import com.zs.demo.commonlib.mvp.home.HomePresenter
import com.zs.demo.commonlib.mvp.view.HotView
import com.zs.demo.commonlib.utils.FieldUtil
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
@Route(path = RouterPath.HOT_FRAGMENT)
class HotFragment : BaseFragment(), HotView {

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
