package com.zs.demo.commonlib.mvp

/**
 *
Created by zs
Date：2018年 08月 03日
Time：16:39
—————————————————————————————————————
About:
—————————————————————————————————————
 */
interface ResultListener<in T>{

    fun onSuccess(t: T?)

    fun onFailed(errorCode: Int?, failedMessage: String?){}

}