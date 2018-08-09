package com.zs.demo.wanandroid.utils

import android.util.Log

/**
 *
Created by zs
Date：2018年 03月 28日
Time：9:15
—————————————————————————————————————
About:
—————————————————————————————————————
 */
class LogUtil{

    companion object{

        val LOG_TAG = "My_Log"
        val LOG_TAG_ERROE = "My_Log_Error"

        fun logShow(content : String){
            Log.d(LOG_TAG,content)
        }

        fun logShowError(content : String){
            Log.e(LOG_TAG_ERROE,content)
        }

    }



}