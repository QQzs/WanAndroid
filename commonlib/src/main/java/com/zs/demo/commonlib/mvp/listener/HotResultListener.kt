package com.zs.demo.commonlib.mvp.listener

import com.zs.demo.commonlib.bean.hot.HotBean

/**
 *
Created by zs
Date：2018年 08月 13日
Time：17:04
—————————————————————————————————————
About: hot
—————————————————————————————————————
 */
interface HotResultListener {

    fun onSuccess(bookmark: MutableList<HotBean>?, hotList: MutableList<HotBean>?, commonList: MutableList<HotBean>?)

    fun onFailed(errorCode: Int?, failedMessage: String?){}

}