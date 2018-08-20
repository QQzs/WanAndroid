package com.zs.demo.wanandroid.modules.task.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import com.zs.demo.wanandroid.modules.task.TaskFragment

/**
 *
Created by zs
Date：2018年 08月 20日
Time：13:38
—————————————————————————————————————
About:
—————————————————————————————————————
 */
class TaskPageAdapter(var taskTitles: MutableList<String>, fm: FragmentManager): FragmentStatePagerAdapter(fm){

    override fun getItem(position: Int): Fragment {
        return TaskFragment.newInstance(position)
    }

    override fun getCount(): Int {
        return taskTitles.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return taskTitles[position]
    }

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }

}