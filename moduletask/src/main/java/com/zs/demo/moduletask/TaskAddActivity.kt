package com.zs.demo.moduletask

import android.app.DatePickerDialog
import android.text.TextUtils
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.zs.demo.commonlib.app.RouterPath
import com.zs.demo.commonlib.base.BaseActivity
import com.zs.demo.commonlib.event.RefreshEvent
import com.zs.demo.commonlib.mvp.presenter.TaskPresenter
import com.zs.demo.commonlib.mvp.view.TaskView
import com.zs.demo.commonlib.utils.FieldUtil
import kotlinx.android.synthetic.main.activity_task_add_layout.*
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.toast
import java.util.*
import kotlin.collections.HashMap


/**
 *
Created by zs
Date：2018年 08月 22日
Time：14:55
—————————————————————————————————————
About:
—————————————————————————————————————
 */
@Route(path = RouterPath.TASK_ACTIVITY_ADD)
class TaskAddActivity: BaseActivity() , TaskView {

    var mPresenter: TaskPresenter? = null

    override fun setLayoutId(): Int {
        return R.layout.activity_task_add_layout
    }

    override fun initView() {

        tv_task_date?.setOnClickListener(this)
        tv_task_save?.setOnClickListener(this)
    }

    override fun initData() {
        mPresenter = TaskPresenter(this, this)
    }

    override fun onClick(view: View?) {

        when(view?.id){
            R.id.tv_task_date ->{
                showDatePickerDialog()
            }
            R.id.tv_task_save ->{
                if (TextUtils.isEmpty(ed_task_title?.text.toString().trim())){
                    toast("请填写标题")
                }else if (TextUtils.isEmpty(tv_task_date?.text.toString().trim())){
                    toast("请选择日期")
                }else{
                    var map = HashMap<String,String>()
                    map[FieldUtil.TASK_TITLE] = ed_task_title?.text.toString().trim()
                    map[FieldUtil.TASK_CONTENT] = ed_task_content?.text.toString().trim()
                    map[FieldUtil.TASK_DATE] = tv_task_date?.text.toString().trim()
                    map[FieldUtil.TASK_TYPE] = "0"
                    mPresenter?.addTask(map)
                }
            }
        }

    }

    /**
     * 日期选择
     * @param activity
     * @param themeResId
     * @param tv
     * @param calendar
     */
    fun showDatePickerDialog() {

        var ca = Calendar.getInstance()
        var year = ca.get(Calendar.YEAR)
        var month = ca.get(Calendar.MONTH)
        var day = ca.get(Calendar.DAY_OF_MONTH)
        // 直接创建一个DatePickerDialog对话框实例，并将它显示出来
        val datePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            // 绑定监听器(How the parent is notified that the date is set.)
            // 此处得到选择的时间，可以进行你想要的操作
            tv_task_date?.text = String.format("%d-%02d-%d",year,(monthOfYear + 1),dayOfMonth)
        }, year, month, day)// 设置初始日期
        //设置起始日期和结束日期
        val datePicker = datePickerDialog.datePicker
        datePicker.minDate = System.currentTimeMillis()
//        datePicker.maxDate = System.currentTimeMillis()
        datePickerDialog.show()

    }

    override fun addTaskSuccess() {
        super.addTaskSuccess()
        EventBus.getDefault().post(RefreshEvent("task","todo"))
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.onDestroyView()
    }
}