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

    fun addTask(map: HashMap<String, String> , taskListener: ResultListener<Any>?)

    fun deleteTask(id: String? , taskListener: ResultListener<Any>?)

    fun getToDoTask(page: Int , taskListener: ResultListener<TaskBean>?)

    fun getDoneTask(page: Int , taskListener: ResultListener<TaskBean>?)

    fun updateTaskStatus(id: String? , status: Int , taskListener: ResultListener<Any>?)


}