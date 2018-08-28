package com.zs.demo.wanandroid.modules.task.model

import com.zs.demo.wanandroid.modules.task.bean.TaskBean
import com.zs.demo.wanandroid.mvp.BaseModel
import com.zs.demo.wanandroid.mvp.ResultListener
import com.zs.demo.wanandroid.request.BaseImpl
import com.zs.demo.wanandroid.request.BaseResponse
import com.zs.demo.wanandroid.request.DefaultObserver
import com.zs.demo.wanandroid.utils.FieldUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 *
Created by zs
Date：2018年 08月 20日
Time：15:32
—————————————————————————————————————
About:
—————————————————————————————————————
 */
class TaskModelImpl(baseImpl: BaseImpl?): BaseModel(baseImpl) , TaskModel{

    override fun addTask(map: HashMap<String, String>, taskListener: ResultListener<Any>?) {
        mRequestApi.service
                .addTask(map[FieldUtil.TASK_TITLE] , map[FieldUtil.TASK_CONTENT] ,map[FieldUtil.TASK_DATE] , "0")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : DefaultObserver<BaseResponse<Any>>(mBaseImpl){
                    override fun onSuccess(response: BaseResponse<Any>?) {
                        taskListener?.onSuccess(response?.data)
                    }

                })
    }

    override fun deleteTask(id: String?, taskListener: ResultListener<Any>?) {
        mRequestApi.service
                .deleteTask(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : DefaultObserver<BaseResponse<Any>>(mBaseImpl){
                    override fun onSuccess(response: BaseResponse<Any>?) {
                        taskListener?.onSuccess(response?.data)
                    }

                })
    }

    override fun getToDoTask(page: Int, taskListener: ResultListener<TaskBean>?) {
        mRequestApi.service
                .getToDoTask(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : DefaultObserver<BaseResponse<TaskBean>>(mBaseImpl){
                    override fun onSuccess(response: BaseResponse<TaskBean>?) {
                        taskListener?.onSuccess(response?.data)
                    }

                })
    }

    override fun getDoneTask(page: Int, taskListener: ResultListener<TaskBean>?) {

        mRequestApi.service
                .getDoneTask(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : DefaultObserver<BaseResponse<TaskBean>>(mBaseImpl){
                    override fun onSuccess(response: BaseResponse<TaskBean>?) {
                        taskListener?.onSuccess(response?.data)
                    }

                })

    }

    override fun updateTaskStatus(id: String? , status: Int, taskListener: ResultListener<Any>?) {

        mRequestApi.service
                .updateTaskStatus(id , status)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : DefaultObserver<BaseResponse<Any>>(mBaseImpl){
                    override fun onSuccess(response: BaseResponse<Any>?) {
                        taskListener?.onSuccess(response?.data)
                    }

                })
    }



}