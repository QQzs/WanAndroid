package com.zs.demo.wanandroid.modules.article.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.zs.demo.wanandroid.R
import com.zs.demo.wanandroid.listener.ItemClickListener
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
class ArticleAdapter(): RecyclerView.Adapter<ArticleAdapter.ArticleHolder>(){

    var mIsCollect: Boolean = false
    var mItemclickListener: ItemClickListener?= null
    var mData: MutableList<Article> = mutableListOf()

    constructor(mItemclickListener: ItemClickListener?) : this() {
        this.mItemclickListener = mItemclickListener
    }

    constructor(mIsCollect: Boolean, mItemclickListener: ItemClickListener?) : this() {
        this.mIsCollect = mIsCollect
        this.mItemclickListener = mItemclickListener
    }

    fun initData(data: MutableList<Article>){
        this.mData = data
        notifyDataSetChanged()
    }

    fun appendData(data: MutableList<Article>){
        mData.addAll(data)
        notifyDataSetChanged()
    }

    /**
     * 知识列表刷新
     */
    fun updateData(position: Int , article: Article){
        if (position < mData.size){
            mData[position] = article
            notifyItemChanged(position + 2)
        }
    }

    /**
     * 体系列表刷新
     */
    fun updateDataType(position: Int , article: Article){
        if (position < mData.size){
            mData[position] = article
            notifyItemChanged(position + 1)
        }
    }

    fun deleteData(position: Int,article: Article){
        mData.remove(article)
        var index = position + 1
        notifyItemRemoved(index)
        if (position != mData.size) {
            notifyItemRangeChanged(index, mData.size - position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleHolder {
        return ArticleHolder(View.inflate(parent?.context, R.layout.home_list_item,null))
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: ArticleHolder, position: Int) {
        holder?.bindData(position)
        holder?.itemView?.setOnClickListener {
            mItemclickListener?.onItemClick(position,mData[position],holder?.itemView?.rl_layout)
        }

        holder?.itemView?.homeItemLike?.setOnClickListener {

            mItemclickListener?.onItemClick(position,mData[position],holder?.itemView?.homeItemLike)
        }

    }

    inner class ArticleHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        fun bindData(position: Int) = with(itemView){
            var article = mData[position]
            article?.run {
                homeItemAuthor?.text = author
                homeItemTitle?.text = title
                homeItemType?.text = chapterName
                if (mIsCollect || collect){
                    homeItemLike?.setImageResource(R.drawable.ic_action_like)
                }else{
                    homeItemLike?.setImageResource(R.drawable.ic_action_no_like)
                }
            }
        }
    }

}