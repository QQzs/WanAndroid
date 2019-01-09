package com.zs.demo.commonlib.mvp.view

import com.zs.demo.commonlib.bean.type.TreeBean
import com.zs.demo.commonlib.mvp.BaseView

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