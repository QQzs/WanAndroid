package com.zs.demo.wanandroid.modules.login.presenter

import com.zs.demo.wanandroid.modules.login.bean.LoginBean

/**
 *
Created by zs
Date：2018年 08月 02日
Time：17:37
—————————————————————————————————————
About:
—————————————————————————————————————
 */
interface LoginPresenter{

    fun login(userName: String , password: String)

    fun register(userName: String , password: String , passwordAgain: String)

    interface LoginListener{

        fun loginSuccess(userInfo: LoginBean?)

        fun registerSuccess(userInfo: LoginBean?)

        fun requestFail(code: Int? ,  msg: String?)

    }

}