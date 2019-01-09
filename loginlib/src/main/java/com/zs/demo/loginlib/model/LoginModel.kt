package com.zs.demo.loginlib.model

import com.zs.demo.commonlib.bean.login.LoginBean
import com.zs.demo.commonlib.bean.login.RegisterBean
import com.zs.demo.commonlib.mvp.ResultListener

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

    fun login(map: HashMap<String, String> , loginListener: ResultListener<LoginBean>?)

    fun register(map: HashMap<String, String> , loginListener: ResultListener<RegisterBean>?)

}