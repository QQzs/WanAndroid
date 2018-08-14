package com.zs.demo.wanandroid.utils

/**
 * Created by zs
 * Date：2018年 08月 14日
 * Time：14:54
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */
class StringUtil{

    companion object {

        /**
         * get random color
         * @return 16777215 is FFFFFF, 0 is 000000
         */
        fun getRandomColor(): String = "#${Integer.toHexString((Math.random() * 16777215).toInt())}"

    }

}
