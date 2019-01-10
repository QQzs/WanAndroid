package com.zs.demo.loginlib

import android.text.InputFilter
import android.text.Spanned
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.zs.demo.commonlib.app.Constant
import com.zs.demo.commonlib.app.RouterPath
import com.zs.demo.commonlib.base.BaseActivity
import com.zs.demo.commonlib.bean.login.LoginBean
import com.zs.demo.commonlib.bean.login.RegisterBean
import com.zs.demo.commonlib.event.LoginEvent
import com.zs.demo.commonlib.utils.FieldUtil
import com.zs.demo.commonlib.utils.SpUtil
import com.zs.demo.loginlib.presenter.LoginPresenter
import com.zs.demo.loginlib.presenter.LoginPresenterImpl
import com.zs.demo.loginlib.view.LoginView
import kotlinx.android.synthetic.main.activity_login_layout.*
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.toast
import java.util.regex.Pattern


/**
 *
Created by zs
Date：2018年 08月 02日
Time：15:46
—————————————————————————————————————
About:
—————————————————————————————————————
 */
@Route(path = RouterPath.LOGIN_ACTIVITY)
class LoginActivity : BaseActivity(), LoginView {

    var mPresenter: LoginPresenter? = null
    @JvmField
    @Autowired(name = FieldUtil.FLAG)
    var mFlag: String? = null
    var mAction : String = "login"

    override fun setLayoutId(): Int {
        return R.layout.activity_login_layout
    }

    override fun initView() {

        ARouter.getInstance().inject(this)

        tv_login_switch?.setOnClickListener(this)

        card_login_view?.setOnClickListener(this)

        edit_text_name?.filters = arrayOf(emojiFilter,inputFilter)

        edit_text_name?.setSelection(edit_text_name?.text.toString().length)

    }

    var emojiFilter: InputFilter = object : InputFilter {
        internal var emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                Pattern.UNICODE_CASE or Pattern.CASE_INSENSITIVE)

        override fun filter(source: CharSequence, start: Int, end: Int, dest: Spanned, dstart: Int, dend: Int): CharSequence? {
            val emojiMatcher = emoji.matcher(source)
            if (emojiMatcher.find()) {
                toast("不支持输入表情")
                return ""
            }
            return null
        }
    }

    var inputFilter: InputFilter = object : InputFilter {
        internal var emoji = Pattern.compile("[a-zA-Z0-9/\\u4e00-\\u9fa5]*")

        override fun filter(source: CharSequence, start: Int, end: Int, dest: Spanned, dstart: Int, dend: Int): CharSequence? {
            val emojiMatcher = emoji.matcher(source)
            if (!emojiMatcher.matches()) {
                toast("请输入汉字字母和数字")
                return ""
            }
            return null
        }
    }


    override fun initData() {
//        mFlag = intent?.getStringExtra(FieldUtil.LOGIN)
        mPresenter = LoginPresenterImpl(this, this)

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
        EventBus.getDefault().post(LoginEvent(mFlag))
        finish()
    }

    override fun registerSuccess(userInfo: RegisterBean?) {
        SpUtil.savaData(Constant.APP_USER_ID,userInfo?.id)
        SpUtil.savaData(Constant.APP_USER_NAME,userInfo?.username)
        EventBus.getDefault().post(LoginEvent(mFlag))
        finish()
    }

    override fun onDestroy() {
        mPresenter?.onDestroyView()
        super.onDestroy()
    }

}