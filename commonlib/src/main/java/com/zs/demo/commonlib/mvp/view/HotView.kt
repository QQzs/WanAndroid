package com.zs.demo.commonlib.mvp.view

import com.zs.demo.commonlib.bean.hot.HotBean

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