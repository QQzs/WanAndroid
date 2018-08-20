package com.zs.demo.wanandroid.modules.task.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zs.demo.wanandroid.R
import com.zs.demo.wanandroid.modules.task.bean.TaskItemBean
import com.zs.demo.wanandroid.view.treeview.Node
import com.zs.demo.wanandroid.view.treeview.TreeRecyclerAdapter

/**
 *
Created by zs
Date：2018年 08月 20日
Time：16:10
—————————————————————————————————————
About:
—————————————————————————————————————
 */
class TaskAdapter : TreeRecyclerAdapter {

    var mType: Int = 0
    constructor(type: Int, context: Context?, datas: List<Node<String, TaskItemBean>>, defaultExpandLevel: Int, iconExpand: Int, iconNoExpand: Int) : super(context, datas, defaultExpandLevel, iconExpand, iconNoExpand){
        this.mType = type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_task_layout, parent, false)
        return TaskHolder(view)
    }

    override fun onBindViewHolder(node: Node<*, *>?, holder: RecyclerView.ViewHolder?, position: Int) {
        var taskHolder = holder as TaskHolder
        taskHolder?.bindData(node)

    }

    inner class TaskHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindData(node: Node<*, *>?){


        }

    }
}
