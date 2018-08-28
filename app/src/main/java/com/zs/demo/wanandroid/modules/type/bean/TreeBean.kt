package com.zs.demo.wanandroid.modules.type.bean

import java.io.Serializable

/**
 *
Created by zs
Date：2018年 08月 13日
Time：10:44
—————————————————————————————————————
About:体系
—————————————————————————————————————
 */
data class TreeBean(
        var id: Int,
        var name: String,
        var courseId: Int,
        var parentChapterId: Int,
        var order: Int,
        var visible: Int,
        var children: MutableList<Children>?
): Serializable{

    data class Children(
            var id: Int,
            var name: String,
            var courseId: Int = 0,
            var parentChapterId: Int = 0,
            var order: Int = 0,
            var visible: Int = 0
    ) : Serializable

}