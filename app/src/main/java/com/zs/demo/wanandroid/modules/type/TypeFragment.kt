package com.zs.demo.wanandroid.modules.type

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.zs.demo.wanandroid.R
import com.zs.demo.wanandroid.base.BaseFragment
import com.zs.demo.wanandroid.modules.mvp.HomePresenter
import com.zs.demo.wanandroid.modules.type.adapter.TypeLeftAdapter
import com.zs.demo.wanandroid.modules.type.adapter.TypeRightAdapter
import com.zs.demo.wanandroid.modules.type.bean.TreeBean
import com.zs.demo.wanandroid.modules.type.view.TypeView
import com.zs.demo.wanandroid.utils.RecyclerViewUtil
import kotlinx.android.synthetic.main.fragment_type_layout.*

/**
 *
Created by zs
Date：2018年 08月 08日
Time：17:09
—————————————————————————————————————
About:
—————————————————————————————————————
 */
class TypeFragment: BaseFragment(), TypeView{

    var mPresenter: HomePresenter?= null
    var mAdapter: TypeLeftAdapter? = null
    var mRightAdapter: TypeRightAdapter? = null
    var mLeftLayoutManager: LinearLayoutManager? = null
    var mRightLayoutManager: LinearLayoutManager? = null

    override fun setLayoutId(): Int {
        return R.layout.fragment_type_layout
    }

    override fun initView() {

        mAdapter = TypeLeftAdapter(mutableListOf())
        RecyclerViewUtil.init(activity,recycler_type,mAdapter)
        mLeftLayoutManager = recycler_type?.layoutManager as LinearLayoutManager
        mAdapter?.setOnItemClickListener { adapter, view, position ->
            var treeBean = adapter.getItem(position) as TreeBean
            mRightLayoutManager?.scrollToPositionWithOffset(position,0)
            mAdapter?.updateStatus(position)
//            activity?.startActivity<PageActivity>(FieldUtil.TYPE to "type" , FieldUtil.TITLE_DATE to treeBean.children , FieldUtil.TITLE to treeBean.name)
        }
        mRightAdapter = TypeRightAdapter(mutableListOf())
        RecyclerViewUtil.init(activity,recycler_tree_type,mRightAdapter)
        mRightLayoutManager = recycler_tree_type?.layoutManager as LinearLayoutManager
        recycler_tree_type?.addOnScrollListener(object : RecyclerView.OnScrollListener(){

            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
            }

        })
        mRightAdapter?.setOnItemClickListener { adapter, view, position ->
            var treeBean = adapter.getItem(position) as TreeBean
            mAdapter?.updateStatus(position)
            recycler_type?.smoothScrollToPosition(position)
        }

    }

    override fun initData() {
        mPresenter = HomePresenter(this,this)
        showLoading()
        mPresenter?.getTypeTree()
    }

    override fun getTreeSuccess(typeTree: MutableList<TreeBean>?) {
        dismissLoading()
//        recycler_type?.refreshComplete()
        typeTree?.run {
            mAdapter?.setNewData(typeTree)
            mRightAdapter?.setNewData(typeTree)
        }

    }

    override fun onDestroy() {
        mPresenter?.onDestroyView()
        super.onDestroy()
    }
}