package com.zs.demo.wanandroid.mvp

import android.content.Context
import com.zs.demo.wanandroid.request.BaseImpl

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

    var mBaseImpl: BaseImpl? = null

    constructor(context: Context?) {
        this.mBaseImpl = context as BaseImpl
    }

}