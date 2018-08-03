package com.zs.demo.wanandroid.modules.login.model

import com.zs.demo.wanandroid.modules.login.presenter.LoginPresenter

/**
 *
Created by zs
Date：2018年 08月 02日
Time：17:14
—————————————————————————————————————
About:
—————————————————————————————————————
 */
interface LoginModel {

    fun login(userName: String , password: String , loginListener: LoginPresenter.LoginListener?)

    fun register(userName: String , password: String , passwordAgain: String , loginListener: LoginPresenter.LoginListener?)

}