package com.zs.demo.wanandroid.modules.login

import android.view.View
import com.zs.demo.wanandroid.Constant
import com.zs.demo.wanandroid.R
import com.zs.demo.wanandroid.base.BaseActivity
import com.zs.demo.wanandroid.modules.login.bean.LoginBean
import com.zs.demo.wanandroid.modules.login.bean.RegisterBean
import com.zs.demo.wanandroid.modules.login.presenter.LoginPresenter
import com.zs.demo.wanandroid.modules.login.presenter.LoginPresenterImpl
import com.zs.demo.wanandroid.modules.login.view.LoginView
import com.zs.demo.wanandroid.utils.SpUtil
import kotlinx.android.synthetic.main.activity_login_layout.*

/**
 *
Created by zs
Date：2018年 08月 02日
Time：15:46
—————————————————————————————————————
About:
—————————————————————————————————————
 */
class LoginActivity : BaseActivity(), LoginView{

    var mPresenter: LoginPresenter? = null
    var mAction : String = "login"

    override fun setLayoutId(): Int {
        return R.layout.activity_login_layout
    }

    override fun initView() {

        tv_login_switch?.setOnClickListener(this)

        card_login_view?.setOnClickListener(this)
    }

    override fun initData() {

        mPresenter = LoginPresenterImpl(this)

    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.tv_login_switch ->{
                if (mAction == "login"){
                    mAction = "register"
                    text_input_password_again?.visibility = View.VISIBLE
                    tv_login_switch?.text = "登录"
                    tv_login_view?.text = "注册"

                }else{
                    mAction = "login"
                    text_input_password?.editText?.setText("")
                    text_input_password_again?.editText?.setText("")
                    text_input_password_again?.visibility = View.GONE
                    tv_login_switch?.text = "注册"
                    tv_login_view?.text = "登录"
                }
            }
            R.id.card_login_view ->{
                var map = HashMap<String,String>()
                map["name"] = edit_text_name?.text.toString().trim()
                map["password"] = edit_text_password?.text.toString().trim()
                if ("login" == mAction){
                    mPresenter?.login(map)
                }else{
                    map["passwordAgain"] = edit_text_password?.text.toString().trim()
                    mPresenter?.register(map)
                }

            }
        }
    }

    override fun loginSuccess(userInfo: LoginBean?) {
        SpUtil.savaData(Constant.APP_USER_ID,userInfo?.id)
        SpUtil.savaData(Constant.APP_USER_NAME,userInfo?.username)
        dismissLoading()
    }

    override fun registerSuccess(userInfo: RegisterBean?) {
        SpUtil.savaData(Constant.APP_USER_ID,userInfo?.id)
        SpUtil.savaData(Constant.APP_USER_NAME,userInfo?.username)
        dismissLoading()
    }

    override fun onDestroy() {
        mPresenter?.onDestroyView()
        super.onDestroy()
    }

}