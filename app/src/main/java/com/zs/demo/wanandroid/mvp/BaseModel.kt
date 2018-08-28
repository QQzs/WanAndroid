package com.zs.demo.wanandroid.mvp

import com.zs.demo.wanandroid.request.BaseImpl
import com.zs.demo.wanandroid.request.RequestApi

/**
 *
Created by zs
Date：2018年 08月 06日
Time：15:39
—————————————————————————————————————
About:
—————————————————————————————————————
 */
open class BaseModel{

    var mRequestApi: RequestApi
    var mBaseImpl: BaseImpl? = null

    constructor(baseImpl: BaseImpl?) {
        this.mBaseImpl = baseImpl
        mRequestApi = RequestApi.getInstance()
    }

}