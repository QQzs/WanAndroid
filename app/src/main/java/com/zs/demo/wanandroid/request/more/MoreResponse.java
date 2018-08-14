package com.zs.demo.wanandroid.request.more;

/**
 * Created by zs
 * Date：2017年 09月 22日
 * Time：14:11
 * —————————————————————————————————————
 * About:返回数据的基类  根据需求设计
 * —————————————————————————————————————
 */

public class MoreResponse{

    private int errorCode;
    private String errorMsg;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
