package com.zs.demo.commonlib.mvp.presenter

import com.zs.demo.commonlib.bean.task.TaskBean
import com.zs.demo.commonlib.mvp.BasePresenter
import com.zs.demo.commonlib.mvp.ResultListener
import com.zs.demo.commonlib.mvp.model.TaskModelImpl
import com.zs.demo.commonlib.mvp.view.TaskView
import com.zs.demo.commonlib.request.BaseImpl
import java.util.*

/**
 *
Created by zs
Date：2018年 08月 20日
Time：15:23
—————————————————————————————————————
About:
—————————————————————————————————————
 */
class TaskPresenter: BasePresenter{

    var mView: TaskView?= null
    var mModel: TaskModelImpl? = null

    constructor(mView: TaskView?, baseImpl: BaseImpl) {
        this.mView = mView
        this.mModel = TaskModelImpl(baseImpl)
    }


    fun getToDoTask(page: Int){
        mModel?.getToDoTask(page , object : ResultListener<TaskBean>{
            override fun onSuccess(t: TaskBean?) {
                mView?.getTaskToDoSuccess(t)
            }
        })
    }

    fun getDoneTask(page: Int){

        mModel?.getDoneTask(page , object : ResultListener<TaskBean>{
            override fun onSuccess(t: TaskBean?) {
                mView?.getTaskDoneSuccess(t)
            }

        })

    }

    fun updateTaskStatus(id: String? , status: Int){
        mModel?.updateTaskStatus(id, status , object : ResultListener<Any>{
            override fun onSuccess(t: Any?) {
                mView?.updateTaskStatusSuccess()
            }
        })
    }

    fun addTask(map: HashMap<String, String>){
        mModel?.addTask(map,object : ResultListener<Any>{
            override fun onSuccess(t: Any?) {
                mView?.addTaskSuccess()
            }
        })

    }


    fun deleteTask(id: String?){
        mModel?.deleteTask(id, object : ResultListener<Any>{
            override fun onSuccess(t: Any?) {
                mView?.deleteTaskSuccess()
            }
        })
    }

    override fun onDestroyView() {
        mView = null
    }

}