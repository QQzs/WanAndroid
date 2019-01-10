package com.zs.demo.commonlib.event

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
    var mLogin: Boolean = true

    constructor()

    constructor(mFlag: String?) {
        this.mFlag = mFlag
    }

    constructor(mFlag: String?, mLogin: Boolean) {
        this.mFlag = mFlag
        this.mLogin = mLogin
    }


}