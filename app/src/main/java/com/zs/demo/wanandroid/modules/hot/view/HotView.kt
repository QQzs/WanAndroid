package com.zs.demo.wanandroid.modules.hot.view

import com.zs.demo.wanandroid.modules.hot.bean.HotBean

/**
 *
Created by zs
Date：2018年 08月 13日
Time：17:11
—————————————————————————————————————
About:
—————————————————————————————————————
 */
interface HotView {

    fun getHotSuccess(bookmark: MutableList<HotBean>?, hotList: MutableList<HotBean>?, commonList: MutableList<HotBean>?)

}