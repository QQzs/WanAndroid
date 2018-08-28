package com.zs.demo.wanandroid.modules.task.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import com.zs.demo.wanandroid.modules.task.TaskFragment
import com.zs.demo.wanandroid.modules.type.TypeArticleFragment
import com.zs.demo.wanandroid.modules.type.bean.TreeBean

/**
 *
Created by zs
Date：2018年 08月 20日
Time：13:38
—————————————————————————————————————
About:
—————————————————————————————————————
 */
class TaskPageAdapter(fm: FragmentManager?): FragmentStatePagerAdapter(fm) {

    var mType: String? = null
    var mTitles = mutableListOf<TreeBean.Children>()

    constructor(fm: FragmentManager?, mType: String?, mTitles: MutableList<TreeBean.Children>) : this(fm) {
        this.mType = mType
        this.mTitles = mTitles
    }

    override fun getItem(position: Int): Fragment {
        if ("type" == mType){
            return TypeArticleFragment.newInstance(mTitles[position].id)
        }else{
            return TaskFragment.newInstance(position)
        }
    }

    override fun getCount(): Int {
        return mTitles.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mTitles[position].name
    }

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }

}