package com.zs.demo.wanandroid.modules.task

import android.view.View
import com.zs.demo.wanandroid.R
import com.zs.demo.wanandroid.base.BaseActivity
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

    }

    override fun onClick(view: View?) {

    }

}