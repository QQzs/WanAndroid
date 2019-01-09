package com.zs.demo.commonlib.mvp.model

import com.zs.demo.commonlib.bean.type.TreeBean
import com.zs.demo.commonlib.mvp.ResultListener

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