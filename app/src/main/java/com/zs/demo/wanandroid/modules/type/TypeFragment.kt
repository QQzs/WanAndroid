package com.zs.demo.wanandroid.modules.type

import com.zs.demo.wanandroid.R
import com.zs.demo.wanandroid.base.BaseFragment
import com.zs.demo.wanandroid.modules.mvp.HomePresenter
import com.zs.demo.wanandroid.modules.task.TaskActivity
import com.zs.demo.wanandroid.modules.type.adapter.TypeAdapter
import com.zs.demo.wanandroid.modules.type.bean.TreeBean
import com.zs.demo.wanandroid.modules.type.view.TypeView
import com.zs.demo.wanandroid.utils.FieldUtil
import com.zs.demo.wanandroid.utils.RecyclerViewUtil
import com.zs.demo.wanandroid.view.cxrecyclerview.CXRecyclerView
import kotlinx.android.synthetic.main.fragment_type_layout.*
import org.jetbrains.anko.startActivity

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
    var mAdapter: TypeAdapter? = null

    override fun setLayoutId(): Int {
        return R.layout.fragment_type_layout
    }

    override fun initView() {

        mAdapter = TypeAdapter(mutableListOf())
        RecyclerViewUtil.init(activity,recycler_type,mAdapter)
        recycler_type?.setLoadingMoreEnabled(false)
        recycler_type?.setLoadingListener(object : CXRecyclerView.LoadingListener{
            override fun onRefresh() {
                mPresenter?.getTypeTree()
            }

            override fun onLoadMore() {

            }

        })

        mAdapter?.setOnItemClickListener { adapter, view, position ->
            var treeBean = adapter.getItem(position - 1) as TreeBean
            activity?.startActivity<TaskActivity>(FieldUtil.TYPE to "type" , FieldUtil.TITLE_DATE to treeBean.children)
        }

    }

    override fun initData() {
        mPresenter = HomePresenter(this,this)
        showLoading()
        mPresenter?.getTypeTree()
    }

    override fun getTreeSuccess(typeTree: MutableList<TreeBean>?) {
        dismissLoading()
        recycler_type?.refreshComplete()
        typeTree?.run {
            mAdapter?.setNewData(typeTree)
        }

    }

    override fun onDestroy() {
        mPresenter?.onDestroyView()
        super.onDestroy()
    }
}