package com.zs.demo.moduletype.adapter

import android.support.v4.content.ContextCompat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zs.demo.commonlib.bean.type.TreeBean
import com.zs.demo.moduletype.R

/**
 *
Created by zs
Date：2018年 08月 13日
Time：11:35
—————————————————————————————————————
About:
—————————————————————————————————————
 */
class TypeLeftAdapter(mData: MutableList<TreeBean>):
        BaseQuickAdapter<TreeBean, BaseViewHolder>(R.layout.item_left_layout,mData){

    private var mIndex: Int? = 0

    fun updateStatus(position: Int){
        if(position != mIndex){
            mIndex = position
            notifyDataSetChanged()
        }
    }

    override fun convert(helper: BaseViewHolder?, item: TreeBean?) {
        item?.run {
            helper?.setText(R.id.tv_type_name,name)
            if (helper?.adapterPosition  == mIndex){
                helper?.setTextColor(R.id.tv_type_name , ContextCompat.getColor(mContext,R.color.colorPrimary))
                helper?.setBackgroundRes(R.id.tv_type_name , R.color.white)
            }else{
                helper?.setTextColor(R.id.tv_type_name , ContextCompat.getColor(mContext,R.color.font_default))
                helper?.setBackgroundRes(R.id.tv_type_name , R.color.loading_bg_color)

            }
        }

    }

}