package com.zs.demo.commonlib.event

/**
 *
Created by zs
Date：2018年 08月 10日
Time：13:40
—————————————————————————————————————
About:
—————————————————————————————————————
 */
class RefreshEvent{

    var mFlag: String? = null
    var mType: String? = null

    constructor(mFlag: String?) {
        this.mFlag = mFlag
    }

    constructor(mFlag: String?, mType: String?) {
        this.mFlag = mFlag
        this.mType = mType
    }


}