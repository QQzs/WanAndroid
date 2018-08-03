package com.zs.demo.wanandroid.modules.login.view

import com.zs.demo.wanandroid.modules.login.bean.LoginBean

/**
 *
Created by zs
Date：2018年 08月 02日
Time：18:01
—————————————————————————————————————
About:
—————————————————————————————————————
 */
interface LoginView {

    fun loginSuccess(userInfo: LoginBean?)

    fun registerSuccess(userInfo: LoginBean?)

    fun requestFail(code: Int? , msg: String?){}

}