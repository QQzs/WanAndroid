package com.zs.demo.wanandroid.modules.type.model

import com.zs.demo.wanandroid.modules.type.bean.TreeBean
import com.zs.demo.wanandroid.mvp.ResultListener

/**
 *
Created by zs
Date：2018年 08月 13日
Time：10:47
—————————————————————————————————————
About:
—————————————————————————————————————
 */
interface TypeModel {

    fun getTypeTree(treeListener: ResultListener<MutableList<TreeBean>>?)

}