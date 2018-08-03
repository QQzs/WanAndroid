package com.zs.project.bean.android

/**
 * Created by zs
 * Date：2018年 04月 27日
 * Time：16:41
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */

data class ArticleList(

        var curPage: Int,
        var datas: MutableList<Article> = mutableListOf(),
        var offset: Int,
        var over: Boolean,
        var pageCount: Int,
        var size: Int,
        var total: Int



) {
    override fun toString(): String {
        return "ArticleList(curPage=$curPage, datas=$datas, offset=$offset, over=$over, pageCount=$pageCount, size=$size, total=$total)"
    }
}