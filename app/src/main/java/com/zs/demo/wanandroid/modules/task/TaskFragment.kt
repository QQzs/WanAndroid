package com.zs.demo.wanandroid.modules.task

import android.os.Bundle
import android.view.View
import com.zs.demo.wanandroid.R
import com.zs.demo.wanandroid.base.BaseFragment
import com.zs.demo.wanandroid.modules.task.bean.TaskBean
import com.zs.demo.wanandroid.modules.task.presenter.TaskPresenter
import com.zs.demo.wanandroid.modules.task.view.TaskView
import com.zs.demo.wanandroid.utils.FieldUtil
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
    var mPresenter: TaskPresenter? = null

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

    }

    override fun initData() {
        mPresenter = TaskPresenter(this,this)

        mPresenter?.getNotDoTask(1)

    }

    override fun getTaskNotDoSuccess(data: TaskBean?) {
    }

    override fun getTaskToDoSuccess(data: TaskBean?) {
    }



}