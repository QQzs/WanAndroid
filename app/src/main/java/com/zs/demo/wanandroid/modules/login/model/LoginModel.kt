package com.zs.demo.wanandroid.modules.login.model

import com.zs.demo.wanandroid.modules.login.bean.LoginBean
import com.zs.demo.wanandroid.modules.login.bean.RegisterBean
import com.zs.demo.wanandroid.mvp.ResultListener

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