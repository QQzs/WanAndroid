package com.zs.demo.wanandroid.listener

import android.view.View

/**
 *
Created by zs
Date：2018年 01月 25日
Time：17:14
—————————————————————————————————————
About:
—————————————————————————————————————
 */
interface ItemClickListener {
    fun onItemClick(position : Int , data : Any? , view : View?)
}