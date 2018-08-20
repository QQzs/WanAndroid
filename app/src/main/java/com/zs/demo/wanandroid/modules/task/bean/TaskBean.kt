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
    var doneList: List<DoneData?>?,
    var todoList: List<TodoData?>?,
    var type: Int?
)

data class DoneData(
    var date: Long?,
    var todoList: List<Todo?>?
)


data class TodoData(
    var date: Long?,
    var todoList: List<Todo?>?
)

data class Todo(
    var completeDate: Any?,
    var completeDateStr: String?,
    var content: String?,
    var date: Long?,
    var dateStr: String?,
    var id: Int?,
    var status: Int?,
    var title: String?,
    var type: Int?,
    var userId: Int?
)