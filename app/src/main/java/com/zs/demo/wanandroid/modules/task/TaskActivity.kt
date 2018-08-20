package com.zs.demo.wanandroid.modules.task

import android.support.design.widget.TabLayout
import android.view.View
import com.zs.demo.wanandroid.R
import com.zs.demo.wanandroid.base.BaseActivity
import com.zs.demo.wanandroid.modules.task.adapter.TaskAdapter
import kotlinx.android.synthetic.main.activity_task_layout.*

/**
 *
Created by zs
Date：2018年 08月 16日
Time：16:41
—————————————————————————————————————
About:
—————————————————————————————————————
 */
class TaskActivity: BaseActivity() {

    var mTaskTitles = mutableListOf<String>()
    var mTaskAdapter: TaskAdapter? = null

    override fun setLayoutId(): Int {
        return R.layout.activity_task_layout
    }

    override fun initView() {

        tool_bar_view?.run {
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            setNavigationOnClickListener({
                finish()
            })
        }

    }

    override fun initData() {

        mTaskTitles.add(getString(R.string.taskNotDo))
        mTaskTitles.add(getString(R.string.taskToDo))

//        tab_layout?.addTab(tab_layout.newTab().setText("ddd"))
//        tab_layout?.addTab(tab_layout.newTab().setText("ddddd"))

        mTaskAdapter = TaskAdapter(mTaskTitles,supportFragmentManager)
        view_pager_task?.adapter = mTaskAdapter
        tab_layout?.setupWithViewPager(view_pager_task)


        tab_layout?.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(view_pager_task))
        view_pager_task?.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tab_layout))

    }

    override fun onClick(view: View?) {

    }

}