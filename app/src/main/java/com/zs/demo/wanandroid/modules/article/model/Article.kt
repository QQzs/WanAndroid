package com.zs.project.bean.android

/**
 * Created by zs
 * Date：2018年 04月 27日
 * Time：16:47
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */

data class Article(

    var apkLink: String,
    var author: String,
    var chapterId: Int,
    var chapterName: String,
    var collect: Boolean,
    var courseId: Int,
    var desc: String,
    var envelopePic: String,
    var fresh: Boolean,
    var id: Int,
    var link: String,
    var niceDate: String,
    var origin: String,
    var originId: Int = -1,
    var projectLink: String,
    var publishTime: Long,
    var superChapterId: Int,
    var superChapterName: String,
    var tags: List<Any>,
    var title: String,
    var type: Int,
    var visible: Int,
    var zan: Int

)
{
    override fun toString(): String {
        return "Article(apkLink='$apkLink', author='$author', chapterId=$chapterId, chapterName='$chapterName', collect=$collect, courseId=$courseId, desc='$desc', envelopePic='$envelopePic', fresh=$fresh, id=$id, link='$link', niceDate='$niceDate', origin='$origin', projectLink='$projectLink', publishTime=$publishTime, superChapterId=$superChapterId, superChapterName='$superChapterName', tags=$tags, title='$title', type=$type, visible=$visible, zan=$zan)"
    }
}