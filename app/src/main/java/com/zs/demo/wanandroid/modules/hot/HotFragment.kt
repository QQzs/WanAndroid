package com.zs.demo.wanandroid.modules.hot

import android.view.View
import com.zs.demo.wanandroid.R
import com.zs.demo.wanandroid.base.BaseFragment
import com.zs.demo.wanandroid.modules.hot.adapter.HotTagAdapter
import com.zs.demo.wanandroid.modules.hot.bean.HotBean
import com.zs.demo.wanandroid.modules.hot.view.HotView
import com.zs.demo.wanandroid.modules.mvp.HomePresenter
import kotlinx.android.synthetic.main.fragment_hot_layout.*

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

    }

    override fun initData() {
        mPresenter = HomePresenter(this,this)
        showLoading()
        mPresenter?.getHotList()
    }

    override fun getHotSuccess(bookmark: MutableList<HotBean>?, hotList: MutableList<HotBean>?, commonList: MutableList<HotBean>?) {
        dismissLoading()
        mBookAdapter = HotTagAdapter(context,bookmark)
        mHotAdapter = HotTagAdapter(context,hotList)
        mCommonAdapter = HotTagAdapter(context,commonList)

        if (bookmark == null || bookmark.size == 0){
            bookmarkTitle?.visibility = View.GONE
            bookmarkFlowLayout?.visibility = View.GONE
        }

        bookmarkFlowLayout?.run {
            adapter = mBookAdapter
        }

        hotFlowLayout?.run {
            adapter = mHotAdapter
        }

        commonUseFlowLayout?.run {
            adapter = mCommonAdapter
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.onDestroyView()

    }

}
