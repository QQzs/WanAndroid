package com.zs.demo.wanandroid.modules.login.model

import com.zs.demo.wanandroid.modules.login.bean.LoginBean
import com.zs.demo.wanandroid.modules.login.presenter.LoginPresenter
import com.zs.demo.wanandroid.request.BaseResponse
import com.zs.demo.wanandroid.request.DefaultObserver
import com.zs.demo.wanandroid.request.RequestApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 *
Created by zs
Date：2018年 08月 02日
Time：17:58
—————————————————————————————————————
About:
—————————————————————————————————————
 */
class LoginModelImpl: LoginModel{

    override fun login(userName: String, password: String, loginListener: LoginPresenter.LoginListener?) {
        RequestApi.getInstance()
                .service
                .loginAndroid(userName,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : DefaultObserver<BaseResponse<LoginBean>>(){

                    override fun onSuccess(response: BaseResponse<LoginBean>?) {
                        loginListener?.loginSuccess(response?.data)
                    }

                    override fun onFail(response: BaseResponse<LoginBean>?) {
                        super.onFail(response)
                        loginListener?.requestFail(response?.errorCode , response?.errorMsg)
                    }
                })
    }

    override fun register(userName: String, password: String , passwordAgain: String , loginListener: LoginPresenter.LoginListener?) {
        RequestApi.getInstance()
                .service
                .registerAndroid(userName,password,passwordAgain)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : DefaultObserver<BaseResponse<LoginBean>>(){

                    override fun onSuccess(response: BaseResponse<LoginBean>?) {
                        loginListener?.loginSuccess(response?.data)
                    }

                    override fun onFail(response: BaseResponse<LoginBean>?) {
                        super.onFail(response)
                        loginListener?.requestFail(response?.errorCode , response?.errorMsg)
                    }

                })
    }

}