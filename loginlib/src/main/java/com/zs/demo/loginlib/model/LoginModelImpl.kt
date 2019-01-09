package com.zs.demo.loginlib.model

import com.zs.demo.commonlib.bean.login.LoginBean
import com.zs.demo.commonlib.bean.login.RegisterBean
import com.zs.demo.commonlib.mvp.BaseModel
import com.zs.demo.commonlib.mvp.ResultListener
import com.zs.demo.commonlib.request.BaseImpl
import com.zs.demo.commonlib.request.BaseResponse
import com.zs.demo.commonlib.request.DefaultObserver
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
class LoginModelImpl(baseImpl: BaseImpl?): BaseModel(baseImpl), LoginModel {

    override fun login(map: HashMap<String, String>, loginListener: ResultListener<LoginBean>?) {
        mRequestApi.service
                .loginAndroid(map["name"], map["password"])
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : DefaultObserver<BaseResponse<LoginBean>>(mBaseImpl,true){

                    override fun onSuccess(response: BaseResponse<LoginBean>?) {
                        loginListener?.onSuccess(response?.data)
                    }
                })
    }

    override fun register(map: HashMap<String, String> , loginListener: ResultListener<RegisterBean>?) {
        mRequestApi.service
                .registerAndroid(map["name"], map["password"], map["passwordAgain"])
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : DefaultObserver<BaseResponse<RegisterBean>>(mBaseImpl){

                    override fun onSuccess(response: BaseResponse<RegisterBean>?) {
                        loginListener?.onSuccess(response?.data)
                    }

                })
    }

}