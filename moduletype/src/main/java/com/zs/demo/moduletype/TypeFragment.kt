package com.zs.demo.moduletype

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.zs.demo.commonlib.app.RouterPath
import com.zs.demo.commonlib.base.BaseFragment
import com.zs.demo.commonlib.bean.type.TreeBean
import com.zs.demo.commonlib.mvp.home.HomePresenter
import com.zs.demo.commonlib.mvp.view.TypeView
import com.zs.demo.commonlib.utils.RecyclerViewUtil
import com.zs.demo.moduletype.adapter.TypeLeftAdapter
import com.zs.demo.moduletype.adapter.TypeRightAdapter
import kotlinx.android.synthetic.main.fragment_type_layout.*

/**
 *
Created by zs
Date：2018年 08月 08日
Time：17:09
—————————————————————————————————————
About: 体系
—————————————————————————————————————
 */
@Route(path = RouterPath.TYPE_FRAGMENT)
class TypeFragment: BaseFragment(), TypeView {

    var mCurrentPosition = 0
    var mTitleHeight = 0
    var mPresenter: HomePresenter?= null
    var mAdapter: TypeLeftAdapter? = null
    var mRightAdapter: TypeRightAdapter? = null
    var mLeftLayoutManager: LinearLayoutManager? = null
    var mRightLayoutManager: LinearLayoutManager? = null
    var mTreeData: MutableList<TreeBean> = mutableListOf()

    override fun setLayoutId(): Int {
        return R.layout.fragment_type_layout
    }

    override fun initView() {

        mAdapter = TypeLeftAdapter(mutableListOf())
        RecyclerViewUtil.initNoDecoration(context,recycler_type,mAdapter)
        mLeftLayoutManager = recycler_type?.layoutManager as LinearLayoutManager
        mAdapter?.setOnItemClickListener { adapter, view, position ->
            mRightLayoutManager?.scrollToPositionWithOffset(position,0)
            mAdapter?.updateStatus(position)
        }

        mRightAdapter = TypeRightAdapter(mutableListOf())
        RecyclerViewUtil.initNoDecoration(context,recycler_tree_type,mRightAdapter)
        mRightLayoutManager = recycler_tree_type?.layoutManager as LinearLayoutManager
        recycler_tree_type?.addOnScrollListener(object : RecyclerView.OnScrollListener(){

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                mTitleHeight = tv_type_title?.height?:0
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                var view = mRightLayoutManager?.findViewByPosition(mCurrentPosition + 1)
                if (view != null && view.top < mTitleHeight){
                    tv_type_title?.y = (-(mTitleHeight - view.top)).toFloat()
                }else{
                    tv_type_title?.y = 0f
                }
                if (mCurrentPosition != mRightLayoutManager?.findFirstVisibleItemPosition()){
                    mCurrentPosition = mRightLayoutManager?.findFirstVisibleItemPosition()?: 0
                    tv_type_title?.y = 0f
                    tv_type_title?.text = mTreeData[mCurrentPosition].name

                    mAdapter?.updateStatus(mCurrentPosition)
                    if (mCurrentPosition > 5){
                        mLeftLayoutManager?.scrollToPositionWithOffset(mCurrentPosition - 5,0)
                    }
                    recycler_type?.smoothScrollToPosition(mCurrentPosition)
                }
            }

        })

    }

    override fun initData() {
        mPresenter = HomePresenter(this, this)
        showLoading()
        mPresenter?.getTypeTree()
    }

    override fun getTreeSuccess(typeTree: MutableList<TreeBean>?) {
        dismissLoading()
        typeTree?.run {
            mTreeData = typeTree
            mAdapter?.setNewData(mTreeData)
            mRightAdapter?.setNewData(mTreeData)
            tv_type_title?.text = mTreeData[0].name
        }

    }

    override fun onDestroy() {
        mPresenter?.onDestroyView()
        super.onDestroy()
    }
}