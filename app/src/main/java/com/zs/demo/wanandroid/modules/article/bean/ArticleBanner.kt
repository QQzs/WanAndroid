package com.zs.project.bean.android

/**
 *
Created by zs
Date：2018年 04月 28日
Time：11:46
—————————————————————————————————————
About:
—————————————————————————————————————
 */

data class ArticleBanner(

    var desc: String,
    var id: String,
    var imagePath: String,
    var isVisible: Int,
    var order: Int,
    var title: String,
    var type: Int,
    var url: String

)
{
    override fun toString(): String {
        return "ArticleBanner(desc='$desc', id=$id, imagePath='$imagePath', isVisible=$isVisible, order=$order, title='$title', type=$type, url='$url')"
    }
}
