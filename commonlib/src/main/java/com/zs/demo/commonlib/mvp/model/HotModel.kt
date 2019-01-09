package com.zs.demo.commonlib.mvp.model

import com.zs.demo.commonlib.mvp.listener.HotResultListener


/**
 *
Created by zs
Date：2018年 08月 13日
Time：16:32
—————————————————————————————————————
About:
—————————————————————————————————————
 */
interface HotModel {

    fun getHotList(hotResultListener: HotResultListener)

}