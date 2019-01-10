package com.zs.demo.modulehot

import android.support.v4.app.Fragment
import com.alibaba.android.arouter.launcher.ARouter
import com.zs.demo.commonlib.app.RouterPath
import com.zs.demo.commonlib.base.BaseActivity

/**
 *
Created by zs
Date：2019年 01月 10日
Time：10:53
—————————————————————————————————————
About:
—————————————————————————————————————
 */
class HotActivity : BaseActivity(){

    override fun setLayoutId(): Int {
        return R.layout.activity_module_layout
    }

    override fun initView() {

    }

    override fun initData() {
        var fragment: Fragment = ARouter.getInstance().build(RouterPath.HOT_FRAGMENT).navigation() as Fragment
        var manager = supportFragmentManager.beginTransaction()
        manager.add(R.id.fl_container , fragment).commitAllowingStateLoss()
    }

}