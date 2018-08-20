package com.zs.demo.wanandroid.modules.task.bean

/**
 *
Created by zs
Date：2018年 08月 20日
Time：15:14
—————————————————————————————————————
About:
—————————————————————————————————————
 */


data class TaskBean(
    var curPage: Int?,
    var datas: List<TaskItemBean?>?,
    var offset: Int?,
    var over: Boolean?,
    var pageCount: Int?,
    var size: Int?,
    var total: Int?
)

data class TaskItemBean(
    var completeDate: Any?,
    var completeDateStr: String?,
    var content: String?,
    var date: Long?,
    var dateStr: String?,
    var id: String?,
    var status: Int?,
    var title: String?,
    var type: Int?,
    var userId: Int?
)