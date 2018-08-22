package com.zs.demo.wanandroid.modules.task

import android.os.Bundle
import android.view.View
import com.zs.demo.wanandroid.R
import com.zs.demo.wanandroid.base.BaseFragment
import com.zs.demo.wanandroid.modules.task.adapter.TaskAdapter
import com.zs.demo.wanandroid.modules.task.bean.TaskBean
import com.zs.demo.wanandroid.modules.task.bean.TaskItemBean
import com.zs.demo.wanandroid.modules.task.presenter.TaskPresenter
import com.zs.demo.wanandroid.modules.task.view.TaskView
import com.zs.demo.wanandroid.utils.FieldUtil
import com.zs.demo.wanandroid.utils.RecyclerViewUtil
import com.zs.demo.wanandroid.view.cxrecyclerview.CXRecyclerView
import com.zs.demo.wanandroid.view.treeview.Node
import kotlinx.android.synthetic.main.recycler_view_layout.*

/**
 *
Created by zs
Date：2018年 08月 17日
Time：10:57
—————————————————————————————————————
About:
—————————————————————————————————————
 */
class TaskFragment: BaseFragment() , TaskView{

    var mType: Int = 0
    var mPage: Int = 1
    var mPresenter: TaskPresenter? = null
    var mAdapter: TaskAdapter? = null
    var mNodes: MutableList<Node<String,TaskItemBean>> = mutableListOf()

    companion object {
        fun newInstance(cid: Int): TaskFragment {
            val fragment = TaskFragment()
            val args = Bundle()
            args.putInt(FieldUtil.TASK_TYPE, cid)
            fragment.arguments = args
            return fragment
        }
    }

    override fun setLayoutId(): Int {
        return R.layout.recycler_view_layout
    }

    override fun initView() {
        arguments?.run {
            mType = getInt(FieldUtil.TASK_TYPE)
        }
        if (mType == 0){
            tv_bottom?.visibility = View.VISIBLE
        }else{
            tv_bottom?.visibility = View.GONE
        }
        mPresenter = TaskPresenter(this,this)
        recycler_view?.setLoadingListener(object : CXRecyclerView.LoadingListener{
            override fun onRefresh() {
                mPage = 1
                initData()
            }

            override fun onLoadMore() {
                mPage += 1
                initData()
            }

        })

        mAdapter = TaskAdapter(mType,context,mNodes,0,R.mipmap.ic_tree_up,R.mipmap.ic_tree_down)
        RecyclerViewUtil.initNoDecoration(context,recycler_view,mAdapter)

    }

    override fun initData() {

        if (mType == 0){
            mPresenter?.getToDoTask(mPage)
        }else{
            mPresenter?.getDoneTask(mPage)
        }

    }

    override fun getTaskToDoSuccess(data: TaskBean?) {
        updateData(data)
    }

    override fun getTaskDoneSuccess(data: TaskBean?){
        updateData(data)
    }

    private fun updateData(data: TaskBean?){
        mNodes.clear()
        data?.datas?.apply {
            for(index in indices){
                var taskItemBean = get(index)
                var dataStr = taskItemBean?.dateStr
                if (index == 0){
                    mNodes?.add(Node<String,TaskItemBean>(dataStr, "-1", dataStr))
                    mNodes?.add(Node<String,TaskItemBean>(taskItemBean?.id, dataStr, taskItemBean))
                }else{
                    var lastItem = get(index - 1)
                    if (lastItem?.dateStr.equals(taskItemBean?.dateStr)){
                        mNodes?.add(Node<String,TaskItemBean>(taskItemBean?.id, dataStr, taskItemBean))
                    }else{
                        mNodes?.add(Node<String,TaskItemBean>(dataStr, "-1", dataStr))
                        mNodes?.add(Node<String,TaskItemBean>(taskItemBean?.id, dataStr, taskItemBean))
                    }
                }
            }
        }
        if (mPage == 1){
            mAdapter?.initData(mNodes as List<Node<Any, Any>>?)
            recycler_view?.refreshComplete()
        }else{
            mAdapter?.appendData(mNodes as List<Node<Any, Any>>?)
            recycler_view?.loadMoreComplete()
        }
    }

}