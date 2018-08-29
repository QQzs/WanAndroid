package com.zs.demo.wanandroid.modules.type.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zhy.view.flowlayout.TagFlowLayout
import com.zs.demo.wanandroid.R
import com.zs.demo.wanandroid.modules.type.bean.TreeBean

/**
 *
Created by zs
Date：2018年 08月 13日
Time：11:35
—————————————————————————————————————
About:
—————————————————————————————————————
 */
class TypeRightAdapter(mData: MutableList<TreeBean>):
        BaseQuickAdapter<TreeBean, BaseViewHolder>(R.layout.item_right_layout,mData){

    override fun convert(helper: BaseViewHolder?, item: TreeBean?) {

        item?.run {
            helper?.setText(R.id.tv_type_name,name)
            var adapter = TypeTagAdapter(mContext,children)
            var flowLayout = helper?.getView(R.id.tag_flow_layout) as TagFlowLayout
            flowLayout.adapter = adapter

        }

    }

}