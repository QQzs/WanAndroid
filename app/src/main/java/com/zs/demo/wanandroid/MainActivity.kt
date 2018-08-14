package com.zs.demo.wanandroid

import android.support.design.widget.BottomNavigationView
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.text.TextUtils
import android.view.View
import com.zs.demo.wanandroid.base.BaseActivity
import com.zs.demo.wanandroid.event.LoginEvent
import com.zs.demo.wanandroid.modules.article.ArticleFragment
import com.zs.demo.wanandroid.modules.hot.HotFragment
import com.zs.demo.wanandroid.modules.login.LoginActivity
import com.zs.demo.wanandroid.modules.type.TypeFragment
import com.zs.demo.wanandroid.utils.FieldUtil
import com.zs.demo.wanandroid.utils.SpUtil
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_header_main.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.startActivity

class MainActivity : BaseActivity() {

    private var fragment1: ArticleFragment = ArticleFragment()
    private var fragment2: TypeFragment = TypeFragment()
    private var fragment3: HotFragment = HotFragment()
    private var lastFragment: Fragment? = null

    override fun setLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        toolbar.run {
            title = getString(R.string.app_name)
            setSupportActionBar(this)
        }
        bottomNavigation.run {
            setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
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
        initNavigation()
        navigationView.getHeaderView(0).navigationViewLogout?.run {
            setOnClickListener {
                if (TextUtils.isEmpty(SpUtil.getString(Constant.APP_USER_ID,null))) {
                    this@MainActivity.startActivity<LoginActivity>(FieldUtil.LOGIN to FieldUtil.LOGIN)
                } else {
                    SpUtil.clearAll()
                    EventBus.getDefault().post(LoginEvent(FieldUtil.LOGIN,false))
                }
            }
        }

    }

    private fun initNavigation(){
        navigationView.getHeaderView(0)?.navigationViewUsername?.run{
            text = if (TextUtils.isEmpty(SpUtil.getString(Constant.APP_USER_ID,null))){
                getString(R.string.not_login)
            }else{
                SpUtil.getString(Constant.APP_USER_NAME,"")
            }

        }
        navigationView.getHeaderView(0).navigationViewLogout?.run {
            text = if (TextUtils.isEmpty(SpUtil.getString(Constant.APP_USER_ID,null))) {
                getString(R.string.goto_login)
            } else {
                getString(R.string.logout)
            }
        }
    }

    override fun initData() {
        switchContent(fragment1)
        EventBus.getDefault().register(this)
    }

    override fun onClick(view: View?) {

    }

    private val onDrawerNavigationItemSelectedListener =
            NavigationView.OnNavigationItemSelectedListener { item ->
                when (item.itemId) {
//                    R.id.nav_like -> {
//
//                    }
//                    R.id.nav_about -> {
//
//                    }
                }
                drawerLayout.closeDrawer(GravityCompat.START)
                true
            }

    /**
     * NavigationItemSelect监听
     */
    private val onNavigationItemSelectedListener =
            BottomNavigationView.OnNavigationItemSelectedListener { item ->
                return@OnNavigationItemSelectedListener when (item.itemId) {
                    R.id.navigation_home -> {
                        switchContent(fragment1)
                        toolbar?.title = getString(R.string.title_home)
                        true
                    }
                    R.id.navigation_type -> {
                        switchContent(fragment2)
                        toolbar?.title = getString(R.string.title_type)
                        true
                    }
                    R.id.navigation_hot -> {
                        switchContent(fragment3)
                        toolbar?.title = getString(R.string.title_hot)
                        true
                    }
                    else -> {
                        false
                    }
                }
            }

    private fun switchContent(to: Fragment?) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        }
        if (to != null && lastFragment != to) {
            if (lastFragment != null) {
                val transaction = supportFragmentManager?.beginTransaction()
                transaction?.hide(lastFragment)?.commitAllowingStateLoss()
            }
            lastFragment = to
            val transaction = supportFragmentManager.beginTransaction()
            if (!to.isAdded) {    // 先判断是否被add过
                transaction.add(R.id.fragment_container, to).commitAllowingStateLoss() // 隐藏当前的fragment，add下一个到Activity中
            } else {
                transaction.show(to).commitAllowingStateLoss() // 隐藏当前的fragment，显示下一个
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun loginRefresh(event: LoginEvent?){
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        }
        initNavigation()
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}
