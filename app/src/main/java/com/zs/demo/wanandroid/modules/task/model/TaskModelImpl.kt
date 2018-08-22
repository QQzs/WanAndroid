package com.zs.demo.wanandroid.modules.task.model

import com.zs.demo.wanandroid.modules.task.bean.TaskBean
import com.zs.demo.wanandroid.mvp.BaseModel
import com.zs.demo.wanandroid.mvp.ResultListener
import com.zs.demo.wanandroid.request.BaseImpl
import com.zs.demo.wanandroid.request.BaseResponse
import com.zs.demo.wanandroid.request.DefaultObserver
import com.zs.demo.wanandroid.request.RequestApi
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

    }

    override fun getToDoTask(page: Int, taskListener: ResultListener<TaskBean>?) {
        RequestApi.getInstance().service
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

        RequestApi.getInstance().service
                .getDoneTask(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : DefaultObserver<BaseResponse<TaskBean>>(mBaseImpl){
                    override fun onSuccess(response: BaseResponse<TaskBean>?) {
                        taskListener?.onSuccess(response?.data)
                    }

                })

    }

    override fun updateTaskStatus(id: String , status: Int, taskListener: ResultListener<Any>?) {

        RequestApi.getInstance().service
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