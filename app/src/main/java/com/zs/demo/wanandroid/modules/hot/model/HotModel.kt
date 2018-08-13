package com.zs.demo.wanandroid.modules.hot.model

import com.zs.demo.wanandroid.modules.hot.listener.HotResultListener

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