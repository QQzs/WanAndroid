package com.zs.demo.wanandroid.modules.hot.bean

/**
 *
Created by zs
Date：2018年 08月 13日
Time：16:48
—————————————————————————————————————
About:
—————————————————————————————————————
 */
data class HotBean(
        var id: Int,
        var name: String,
        var link: String,
        var visible: Int,
        var order: Int,
        var icon: Any
){
    override fun toString(): String {
        return "HotBean(id=$id, name='$name', link='$link', visible=$visible, order=$order, icon=$icon)"
    }
}