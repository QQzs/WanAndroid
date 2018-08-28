package com.zs.demo.wanandroid.modules

import android.support.design.widget.TabLayout
import com.zs.demo.wanandroid.R
import com.zs.demo.wanandroid.base.BaseActivity
import com.zs.demo.wanandroid.modules.task.adapter.TaskPageAdapter
import com.zs.demo.wanandroid.modules.type.bean.TreeBean
import com.zs.demo.wanandroid.utils.FieldUtil
import kotlinx.android.synthetic.main.activity_task_layout.*

/**
 *
Created by zs
Date：2018年 08月 16日
Time：16:41
—————————————————————————————————————
About:
—————————————————————————————————————
 */
class PageActivity: BaseActivity() {

    var mType: String? = null
    var mPageAdapter: TaskPageAdapter? = null
    var mTitles = mutableListOf<TreeBean.Children>()

    override fun setLayoutId(): Int {
        return R.layout.activity_task_layout
    }

    override fun initView() {

        tool_bar_view?.run {
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            setNavigationOnClickListener({
                finish()
            })
        }
    }

    override fun initData() {

        mType = intent?.getStringExtra(FieldUtil.TYPE)
        mTitles = intent?.getSerializableExtra(FieldUtil.TITLE_DATE) as MutableList<TreeBean.Children>

        if ("type" == mType){
            tool_bar_view?.title = getString(R.string.app_name)
            tab_layout?.tabMode = TabLayout.MODE_SCROLLABLE
        }else{
            tool_bar_view?.title = getString(R.string.my_task)
            tab_layout?.tabMode = TabLayout.MODE_FIXED
        }
//        tab_layout?.addTab(tab_layout.newTab().setText("ddd"))
//        tab_layout?.addTab(tab_layout.newTab().setText("ddddd"))

        mPageAdapter = TaskPageAdapter(supportFragmentManager, mType , mTitles)
        view_pager_task?.adapter = mPageAdapter
        tab_layout?.setupWithViewPager(view_pager_task)
        view_pager_task?.offscreenPageLimit = 3

        tab_layout?.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(view_pager_task))
        view_pager_task?.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tab_layout))

    }

}