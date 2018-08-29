package com.zs.demo.wanandroid.modules.type.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import com.zs.demo.wanandroid.R
import com.zs.demo.wanandroid.modules.type.bean.TreeBean
import com.zs.demo.wanandroid.utils.StringUtil.Companion.getRandomColor

class TypeTagAdapter(context: Context?, datas: MutableList<TreeBean.Children>?) :
    TagAdapter<TreeBean.Children>(datas) {
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(parent: FlowLayout, position: Int, data: TreeBean.Children): View {
        return (inflater.inflate(R.layout.item_common_hot, parent, false) as TextView).apply {
            text = data.name
            val parseColor = try {
                Color.parseColor(getRandomColor())
            } catch (_: Exception) {
                @Suppress("DEPRECATION")
                context.resources.getColor(R.color.colorAccent)
            }
//            setTextColor(parseColor)
        }
    }
}