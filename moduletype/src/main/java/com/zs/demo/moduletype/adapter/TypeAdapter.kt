package com.zs.demo.moduletype.adapter

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
class TypeAdapter(mData: MutableList<TreeBean>):
        BaseQuickAdapter<TreeBean, BaseViewHolder>(R.layout.item_type_list,mData){

    override fun convert(helper: BaseViewHolder?, item: TreeBean?) {

        item?.run {
            helper?.setText(R.id.tv_type_title,name)
            children?.run {
                helper?.setText(R.id.tv_type_content,
                        joinToString("     ", transform = { child -> child.name
                }))
            }
        }

    }

}