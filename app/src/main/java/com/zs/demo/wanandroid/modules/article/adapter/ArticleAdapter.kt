package com.zs.demo.wanandroid.modules.article.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.zs.demo.wanandroid.R
import com.zs.project.bean.android.Article
import kotlinx.android.synthetic.main.home_list_item.view.*

/**
 *
Created by zs
Date：2018年 08月 09日
Time：14:27
—————————————————————————————————————
About:
—————————————————————————————————————
 */
class ArticleAdapter: RecyclerView.Adapter<ArticleAdapter.ArticleHolder>(){

    var mData: MutableList<Article> = mutableListOf()

    fun initData(data: MutableList<Article>){
        this.mData = data
        notifyDataSetChanged()
    }

    fun appendData(data: MutableList<Article>){
        mData.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleHolder {
        return ArticleHolder(View.inflate(parent?.context, R.layout.home_list_item,null))
    }

    override fun getItemCount(): Int {

        return mData.size

    }

    override fun onBindViewHolder(holder: ArticleHolder, position: Int) {
        holder?.bindData(position)
    }

    inner class ArticleHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        fun bindData(position: Int) = with(itemView){
            var article = mData[position]
            article?.run {
                homeItemAuthor?.text = author
                homeItemTitle?.text = title
                homeItemType?.text = chapterName
            }
        }
    }

}