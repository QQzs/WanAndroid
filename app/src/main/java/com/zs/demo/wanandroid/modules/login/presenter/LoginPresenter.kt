package com.zs.demo.wanandroid.modules.login.presenter

import com.zs.demo.wanandroid.mvp.BasePresenter

/**
 *
Created by zs
Date：2018年 08月 02日
Time：17:37
—————————————————————————————————————
About:
—————————————————————————————————————
 */
interface LoginPresenter: BasePresenter{

    fun login(map: HashMap<String, String>)

    fun register(map: HashMap<String, String>)

}