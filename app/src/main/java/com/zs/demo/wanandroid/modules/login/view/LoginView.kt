package com.zs.demo.wanandroid.modules.login.view

import com.zs.demo.wanandroid.modules.login.bean.LoginBean
import com.zs.demo.wanandroid.modules.login.bean.RegisterBean
import com.zs.demo.wanandroid.mvp.BaseView

/**
 *
Created by zs
Date：2018年 08月 02日
Time：18:01
—————————————————————————————————————
About:
—————————————————————————————————————
 */
interface LoginView: BaseView {

    fun loginSuccess(userInfo: LoginBean?)

    fun registerSuccess(userInfo: RegisterBean?)

}