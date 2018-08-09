package com.zs.demo.wanandroid.event

/**
 *
Created by zs
Date：2018年 08月 09日
Time：15:56
—————————————————————————————————————
About:
—————————————————————————————————————
 */
class LoginEvent{

    var mFlag: String? = null
    var mType: String? = null

    constructor(mFlag: String?, mType: String?) {
        this.mFlag = mFlag
        this.mType = mType
    }
}