package com.zs.demo.wanandroid.modules.task.model

import com.zs.demo.wanandroid.modules.task.bean.TaskBean
import com.zs.demo.wanandroid.mvp.ResultListener

/**
 *
Created by zs
Date：2018年 08月 20日
Time：15:20
—————————————————————————————————————
About:
—————————————————————————————————————
 */
interface TaskModel{

    fun getNotDoTask(page: Int , taskListener: ResultListener<TaskBean>?)

    fun getToDoTask(page: Int , taskListener: ResultListener<TaskBean>?)

}