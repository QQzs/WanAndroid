package com.zs.demo.commonlib.bean.login

/**
 *
Created by zs
Date：2018年 03月 23日
Time：10:10
—————————————————————————————————————
About:
—————————————————————————————————————
 */
class LoginBean{

    var id : String = ""
    var username : String  = ""
    var password : String  = ""
    var icon : String = ""

    constructor(id: String, username: String) {
        this.id = id
        this.username = username
    }

    override fun toString(): String {
        return "LoginBean(id='$id', username='$username', password='$password', icon='$icon')"
    }

}