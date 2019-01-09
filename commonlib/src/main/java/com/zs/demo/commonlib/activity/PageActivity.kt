package com.zs.demo.commonlib.activity

import android.support.design.widget.TabLayout
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.zs.demo.commonlib.R
import com.zs.demo.commonlib.adapter.TaskPageAdapter
import com.zs.demo.commonlib.app.RouterPath
import com.zs.demo.commonlib.base.BaseActivity
import com.zs.demo.commonlib.bean.type.TreeBean
import com.zs.demo.commonlib.utils.FieldUtil
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
@Route(path = RouterPath.COMMON_PAGE_ACTIVITY)
class PageActivity: BaseActivity() {

    var mType: String? = null
    var mPosition: Int? = null
    var mPageAdapter: TaskPageAdapter? = null
    var mTitles = mutableListOf<TreeBean.Children>()

    override fun setLayoutId(): Int {
        return R.layout.activity_task_layout
    }

    override fun initView() {

        ARouter.getInstance().inject(this)
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
        mPosition = intent?.getIntExtra(FieldUtil.POSITION,0)
        if (mTitles.size == 1){
            tab_layout?.visibility = View.GONE
        }

        if ("type" == mType){
            tool_bar_view?.title = intent?.getStringExtra(FieldUtil.TITLE)
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

        view_pager_task?.currentItem = mPosition?:0

    }

}