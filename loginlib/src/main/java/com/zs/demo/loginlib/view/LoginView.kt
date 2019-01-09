package com.zs.demo.loginlib.view

import com.zs.demo.commonlib.bean.login.LoginBean
import com.zs.demo.commonlib.bean.login.RegisterBean
import com.zs.demo.commonlib.mvp.BaseView

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