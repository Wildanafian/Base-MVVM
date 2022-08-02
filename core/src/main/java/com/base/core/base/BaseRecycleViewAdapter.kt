package com.base.core.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Wildan Nafian on 12/01/2022.
 * Github https://github.com/Wildanafian
 * wildanafian8@gmail.com
 */

abstract class BaseRecycleViewAdapter<T, RV : RecyclerView.ViewHolder?> : RecyclerView.Adapter<RV>() {
    var mlayout: Int? = null
    var items:ArrayList<T> = ArrayList()
    var customSize = 0
    var context: Context? = null


    override fun getItemCount(): Int{
        return if (customSize == 0 ) items.size
        else customSize
    }

    open fun add(item: T) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun addAll(items: List<T>) {
        for (item in items) {
            add(item)
        }
    }

    fun removeAt(position: Int){
        items.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount)
//        notifyDataSetChanged()
    }

    fun editAt(position: Int, newData : T){
        items[position] = newData
        notifyItemChanged(position)
        notifyItemRangeChanged(position, itemCount)
//        notifyDataSetChanged()
    }

    fun clear() {
        notifyItemRangeRemoved(0, itemCount)
        items.clear()
    }

    abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RV

    fun setView(parent: ViewGroup, layout: Int): View {
        context = parent.context
        return LayoutInflater.from(parent.context).inflate(layout, parent, false)
    }

    override fun onBindViewHolder(holder: RV, position: Int) {
        getBindViewHolder(holder, position, items[position])
    }

    abstract fun getBindViewHolder(holder: RV, position: Int, data: T)

}