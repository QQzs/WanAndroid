package com.zs.demo.commonlib.utils

import android.text.TextUtils
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

        fun logShow(content : String?){
            if (TextUtils.isEmpty(content)){
                Log.d(LOG_TAG, "emptyData  $content")
            }else{
                Log.d(LOG_TAG,content)
            }

        }

        fun logShowError(content : String?){
            Log.e(LOG_TAG_ERROE,content)
        }

    }



}