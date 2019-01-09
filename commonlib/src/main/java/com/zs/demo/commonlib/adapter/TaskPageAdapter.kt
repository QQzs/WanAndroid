package com.zs.demo.commonlib.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import com.alibaba.android.arouter.launcher.ARouter
import com.zs.demo.commonlib.app.RouterPath
import com.zs.demo.commonlib.bean.type.TreeBean
import com.zs.demo.commonlib.utils.FieldUtil

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
            return ARouter.getInstance()
                    .build(RouterPath.TYPE_FRAGMENT_ARTICLE)
                    .withInt(FieldUtil.CID, mTitles[position].id)
                    .navigation() as Fragment
        }else{
            return ARouter.getInstance()
                    .build(RouterPath.TASK_FRAGMENT)
                    .withInt(FieldUtil.TASK_TYPE, position)
                    .navigation() as Fragment
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