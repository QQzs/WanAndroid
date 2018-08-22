package com.zs.demo.wanandroid.modules.task.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zs.demo.wanandroid.R
import com.zs.demo.wanandroid.listener.ItemClickListener
import com.zs.demo.wanandroid.modules.task.bean.TaskItemBean
import com.zs.demo.wanandroid.view.treeview.Node
import com.zs.demo.wanandroid.view.treeview.TreeRecyclerAdapter
import kotlinx.android.synthetic.main.item_task_layout.view.*

/**
 *
Created by zs
Date：2018年 08月 20日
Time：16:10
—————————————————————————————————————
About:
—————————————————————————————————————
 */
class TaskAdapter : TreeRecyclerAdapter{

    var mType: Int = 0
    var mItemClickListener: ItemClickListener? = null
    constructor(type: Int,
                context: Context?,
                itemClickListener: ItemClickListener?,
                datas: List<Node<String, TaskItemBean>>,
                defaultExpandLevel: Int,
                iconExpand: Int,
                iconNoExpand: Int) : super(context, datas, defaultExpandLevel, iconExpand, iconNoExpand){
        this.mType = type
        this.mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_task_layout, parent, false)
        return TaskHolder(view)
    }

    override fun onBindViewHolder(node: Node<*, *>?, holder: RecyclerView.ViewHolder?, position: Int) {
        var taskHolder = holder as TaskHolder
        taskHolder?.bindData(node)
        taskHolder?.itemView?.iv_task_action?.setOnClickListener {
            mItemClickListener?.onItemClick(position , node , taskHolder?.itemView?.iv_task_action)
        }
        taskHolder?.itemView?.iv_task_delete?.setOnClickListener {
            mItemClickListener?.onItemClick(position , node , taskHolder?.itemView?.iv_task_delete)
        }

    }

    inner class TaskHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindData(node: Node<*, *>?) = with(itemView){

            node?.run {
                if (icon == -1){
                    iv_task_arrow?.visibility = View.GONE
                }else{
                    iv_task_arrow?.visibility = View.VISIBLE
                    iv_task_arrow?.setImageResource(icon)
                }
                if (mType == 0){
                    iv_task_action?.setImageResource(R.mipmap.ic_task_over)
                }else{
                    iv_task_action?.setImageResource(R.mipmap.ic_task_recall)
                }

                if ("-1" == getpId()){
                    rl_task_date?.visibility = View.VISIBLE
                    cl_task_item?.visibility = View.GONE
                    tv_task_date?.text = name

                }else{
                    rl_task_date?.visibility = View.GONE
                    cl_task_item?.visibility = View.VISIBLE
                    var task = node.getBean() as TaskItemBean
                    tv_task_title?.text = task.title
                    if (TextUtils.isEmpty(task.content)){
                        tv_task_content?.visibility = View.GONE
                    }else{
                        tv_task_content?.visibility = View.VISIBLE
                        tv_task_content?.text = task.content
                    }
                }
            }


        }

    }
}
