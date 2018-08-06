package com.zs.demo.wanandroid.modules.login.model

import com.zs.demo.wanandroid.modules.login.bean.LoginBean
import com.zs.demo.wanandroid.modules.login.bean.RegisterBean
import com.zs.demo.wanandroid.mvp.ResultListener
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

    override fun login(map: HashMap<String, String>, loginListener: ResultListener<LoginBean>?) {
        RequestApi.getInstance().service
                .loginAndroid(map["name"], map["password"])
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : DefaultObserver<BaseResponse<LoginBean>>(){

                    override fun onSuccess(response: BaseResponse<LoginBean>?) {
                        loginListener?.onSuccess(response?.data)
                    }

//                    override fun onFail(response: BaseResponse<LoginBean>?) {
//                        super.onFail(response)
//                        loginListener?.onFailed(response?.errorCode , response?.errorMsg)
//                    }
                })
    }

    override fun register(map: HashMap<String, String> , loginListener: ResultListener<RegisterBean>?) {
        RequestApi.getInstance().service
                .registerAndroid(map["name"], map["password"], map["passwordAgain"])
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : DefaultObserver<BaseResponse<RegisterBean>>(){

                    override fun onSuccess(response: BaseResponse<RegisterBean>?) {
                        loginListener?.onSuccess(response?.data)
                    }

                })
    }

}