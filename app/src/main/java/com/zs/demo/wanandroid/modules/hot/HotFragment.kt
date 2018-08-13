package com.zs.demo.wanandroid.modules.hot

import com.zs.demo.wanandroid.R
import com.zs.demo.wanandroid.base.BaseFragment
import com.zs.demo.wanandroid.modules.hot.bean.HotBean
import com.zs.demo.wanandroid.modules.hot.view.HotView
import com.zs.demo.wanandroid.modules.mvp.HomePresenter
import com.zs.demo.wanandroid.utils.LogUtil

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

    override fun setLayoutId(): Int {
        return R.layout.fragment_hot_layout
    }

    override fun initView() {
    }

    override fun initData() {
        mPresenter = HomePresenter(this,this)
        mPresenter?.getHotList()
    }

    override fun getHotSuccess(bookmark: MutableList<HotBean>?, hotList: MutableList<HotBean>?, commonList: MutableList<HotBean>?) {

        LogUtil.logShow(bookmark?.toString())
        LogUtil.logShow(hotList?.toString())
        LogUtil.logShow(commonList?.toString())


    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.onDestroyView()

    }

}
