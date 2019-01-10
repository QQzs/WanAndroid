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
import com.zs.demo.commonlib.event.LoginEvent
import com.zs.demo.commonlib.utils.FieldUtil
import com.zs.demo.commonlib.utils.IntentUtil
import kotlinx.android.synthetic.main.activity_task_layout.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

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

        EventBus.getDefault().register(this)
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

        if (intent?.hasExtra(FieldUtil.TYPE) as Boolean){
            mType = intent?.getStringExtra(FieldUtil.TYPE)
            mTitles = intent?.getSerializableExtra(FieldUtil.TITLE_DATE) as MutableList<TreeBean.Children>
            mPosition = intent?.getIntExtra(FieldUtil.POSITION,0)
            initViewPager()
        }else{
            mType = "task"
            mTitles.add(TreeBean.Children(0,getString(R.string.taskNotDo)))
            mTitles.add(TreeBean.Children(1,getString(R.string.taskToDo)))
            mPosition = 0
            if (IntentUtil.isLogin("initViewPager")){
                initViewPager()
            }
        }

    }

    fun initViewPager() {

        if ("type" == mType){
            tool_bar_view?.title = intent?.getStringExtra(FieldUtil.TITLE)
            tab_layout?.tabMode = TabLayout.MODE_SCROLLABLE
        }else{
            tool_bar_view?.title = getString(R.string.my_task)
            tab_layout?.tabMode = TabLayout.MODE_FIXED
        }
        if (mTitles.size == 1){
            tab_layout?.visibility = View.GONE
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun loginRefresh(event: LoginEvent?){
        if (event?.mFlag == "initViewPager"){
            initViewPager()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}