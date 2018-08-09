package com.zs.demo.wanandroid.modules.login.presenter

import com.zs.demo.wanandroid.modules.login.bean.LoginBean
import com.zs.demo.wanandroid.modules.login.bean.RegisterBean
import com.zs.demo.wanandroid.modules.login.model.LoginModelImpl
import com.zs.demo.wanandroid.modules.login.view.LoginView
import com.zs.demo.wanandroid.mvp.ResultListener
import com.zs.demo.wanandroid.request.BaseImpl

/**
 *
Created by zs
Date：2018年 08月 02日
Time：18:00
—————————————————————————————————————
About:
—————————————————————————————————————
 */
class LoginPresenterImpl: LoginPresenter{

    var mLoginView: LoginView? = null
    var mLoginModel: LoginModelImpl? = null

    constructor(view: LoginView , baseImpl: BaseImpl?) {
        this.mLoginView = view
        this.mLoginModel = LoginModelImpl(baseImpl)
    }

    override fun login(map: HashMap<String, String>) {
        mLoginModel?.login(map,object : ResultListener<LoginBean>{
            override fun onSuccess(t: LoginBean?) {
                mLoginView?.loginSuccess(t)
            }

            override fun onFailed(errorCode: Int?, failedMessage: String?) {
                super.onFailed(errorCode, failedMessage)
                mLoginView?.requestFail(errorCode , failedMessage)
            }
        })
    }

    override fun register(map: HashMap<String, String>) {
        mLoginModel?.register(map,object : ResultListener<RegisterBean>{
            override fun onSuccess(t: RegisterBean?) {
                mLoginView?.registerSuccess(t)
            }
        })
    }

    override fun onDestroyView() {
        mLoginView = null
    }

}