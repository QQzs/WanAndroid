package com.zs.demo.commonlib.mvp.view

import com.zs.demo.commonlib.bean.task.TaskBean
import com.zs.demo.commonlib.mvp.BaseView

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

    fun getTaskToDoSuccess(data: TaskBean?){}

    fun getTaskDoneSuccess(data: TaskBean?){}

    fun addTaskSuccess(){}

    fun deleteTaskSuccess(){}

    fun updateTaskStatusSuccess(){}

}