package com.zs.demo.wanandroid

import android.support.v7.app.ActionBarDrawerToggle
import android.view.View
import com.zs.demo.wanandroid.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {


    override fun setLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        toolbar.run {
            title = getString(R.string.app_name)
            setSupportActionBar(this)
        }
        bottomNavigation.run {
//            setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
            selectedItemId = R.id.navigation_home
        }
        drawerLayout.run {
            val toggle = ActionBarDrawerToggle(
                    this@MainActivity,
                    this,
                    toolbar,
                    R.string.navigation_drawer_open,
                    R.string.navigation_drawer_close
            )
            addDrawerListener(toggle)
            toggle.syncState()
        }

    }

    override fun initData() {

    }

    override fun onClick(p0: View?) {

    }

}
