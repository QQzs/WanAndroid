package com.zs.demo.wanandroid.modules.task.view

import com.zs.demo.wanandroid.modules.task.bean.TaskBean
import com.zs.demo.wanandroid.mvp.BaseView

/**
 *
Created by zs
Date：2018年 08月 20日
Time：15:18
—————————————————————————————————————
About:
—————————————————————————————————————
 */
interface TaskView : BaseView {

    fun getTaskNotDoSuccess(data: TaskBean?)

    fun getTaskToDoSuccess(data: TaskBean?)

}