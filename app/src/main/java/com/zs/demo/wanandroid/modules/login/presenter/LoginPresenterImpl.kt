package com.zs.demo.wanandroid.modules.login.presenter

import com.zs.demo.wanandroid.modules.login.bean.LoginBean
import com.zs.demo.wanandroid.modules.login.model.LoginModelImpl
import com.zs.demo.wanandroid.modules.login.view.LoginView

/**
 *
Created by zs
Date：2018年 08月 02日
Time：18:00
—————————————————————————————————————
About:
—————————————————————————————————————
 */
class LoginPresenterImpl: LoginPresenter , LoginPresenter.LoginListener{

    var mLoginView: LoginView? = null
    var mLoginModel: LoginModelImpl? = null

    constructor(mLoginView: LoginView?) {
        this.mLoginView = mLoginView
        this.mLoginModel = LoginModelImpl()
    }


    override fun login(userName: String, password: String) {
        mLoginModel?.login(userName,password,this)
    }

    override fun register(userName: String, password: String , passwordAgain: String) {
        mLoginModel?.register(userName,password,passwordAgain,this)
    }

    override fun loginSuccess(userInfo: LoginBean?) {
        mLoginView?.loginSuccess(userInfo)
    }

    override fun registerSuccess(userInfo: LoginBean?) {
        mLoginView?.registerSuccess(userInfo)
    }

    override fun requestFail(code: Int?, msg: String?) {
        mLoginView?.requestFail(code , msg)
    }


}