package com.zs.demo.wanandroid.modules.task

import android.os.Bundle
import android.view.View
import com.zs.demo.wanandroid.R
import com.zs.demo.wanandroid.base.BaseFragment
import com.zs.demo.wanandroid.event.RefreshEvent
import com.zs.demo.wanandroid.listener.ItemClickListener
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
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.startActivity

/**
 *
Created by zs
Date：2018年 08月 17日
Time：10:57
—————————————————————————————————————
About: 任务列表
—————————————————————————————————————
 */
class TaskFragment: BaseFragment() , TaskView , ItemClickListener{

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
        EventBus.getDefault().register(this)
        arguments?.run {
            mType = getInt(FieldUtil.TASK_TYPE)
        }
        if (mType == 0){
            tv_bottom?.visibility = View.VISIBLE
            tv_bottom?.setOnClickListener(this)
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

        mAdapter = TaskAdapter(mType,context,this,mNodes,1,R.mipmap.ic_tree_up,R.mipmap.ic_tree_down)
        RecyclerViewUtil.initNoDecoration(context,recycler_view,mAdapter)
        showLoading()

    }

    override fun initData() {

        if (mType == 0){
            mPresenter?.getToDoTask(mPage)
        }else{
            mPresenter?.getDoneTask(mPage)
        }

    }

    override fun getTaskToDoSuccess(data: TaskBean?) {
        dismissLoading()
        updateData(data)
    }

    override fun getTaskDoneSuccess(data: TaskBean?){
        dismissLoading()
        updateData(data)
    }

    override fun updateTaskStatusSuccess() {
        super.updateTaskStatusSuccess()
        EventBus.getDefault().post(RefreshEvent("task","all"))
    }

    override fun deleteTaskSuccess() {
        super.deleteTaskSuccess()
        EventBus.getDefault().post(RefreshEvent("task","all"))
    }

    private fun updateData(data: TaskBean?){
        mNodes.clear()
        data?.datas?.apply {
            for(index in indices){
                var currentItem = get(index)
                var lastItem = if (index == 0) null else get(index -1)
                var currentDate = if (mType == 0) currentItem?.dateStr else currentItem?.completeDateStr
                var lastDate = if (mType == 0) lastItem?.dateStr else lastItem?.completeDateStr

                if (index > 0 && currentDate.equals(lastDate)){
                    mNodes?.add(Node<String,TaskItemBean>(currentItem?.id, currentDate, currentItem))
                }else{
                    mNodes?.add(Node<String,TaskItemBean>(currentDate, "-1", currentDate))
                    mNodes?.add(Node<String,TaskItemBean>(currentItem?.id, currentDate, currentItem))
                }
            }
        }
        if (mPage == 1){
            mAdapter?.initData(mNodes as List<Node<Any, Any>>? , 1)
            recycler_view?.refreshComplete()
        }else{
            mAdapter?.appendData(mNodes as List<Node<Any, Any>>?,1)
            recycler_view?.loadMoreComplete()
        }
    }

    override fun onItemClick(position: Int, data: Any?, view: View?) {
        var node = data as Node<String, TaskItemBean>
        var task = node.getBean()
        when(view?.id){
            R.id.iv_task_action ->{
                if (mType == 0){
                    mPresenter?.updateTaskStatus(task.id,1)
                }else{
                    mPresenter?.updateTaskStatus(task.id,0)
                }
            }
            R.id.iv_task_delete ->{
                mPresenter?.deleteTask(task.id)
            }
            R.id.cl_task_item ->{

            }
        }

    }

    override fun onClick(view: View?) {
        super.onClick(view)
        when(view?.id){
            R.id.tv_bottom ->{
                activity?.startActivity<TaskAddActivity>()
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun refreshData(event: RefreshEvent){
        if ("task" == event.mFlag){
            if ("all" == event.mType){
                initData()
            }else if ("todo" == event.mType && mType == 0){
                mPresenter?.getToDoTask(mPage)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.onDestroyView()
        EventBus.getDefault().unregister(this)
    }

}