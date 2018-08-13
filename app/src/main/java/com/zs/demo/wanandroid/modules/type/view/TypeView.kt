package com.zs.demo.wanandroid.modules.type.view

import com.zs.demo.wanandroid.modules.type.bean.TreeBean
import com.zs.demo.wanandroid.mvp.BaseView

/**
 *
Created by zs
Date：2018年 08月 13日
Time：11:12
—————————————————————————————————————
About:
—————————————————————————————————————
 */
interface TypeView: BaseView{

    fun getTreeSuccess(typeTree: MutableList<TreeBean>?)

}