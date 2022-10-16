package com.base.core.base

import android.content.Context
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import java.lang.ref.WeakReference

/**
 * Created by Wildan Nafian on 12/01/2022.
 * Github https://github.com/Wildanafian
 * wildanafian8@gmail.com
 */

abstract class BaseRecycleViewAdapter<T, RV : RecyclerView.ViewHolder>(fragment: Fragment) : RecyclerView.Adapter<RV>() {
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

    override fun onBindViewHolder(holder: RV, position: Int) {
        getBindViewHolder(holder, position, items[position])
    }

    abstract fun getBindViewHolder(holder: RV, position: Int, data: T)

    private val fragmentRef = WeakReference(fragment)

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        setupLifecycleObserver(recyclerView)
    }

    private fun setupLifecycleObserver(recyclerView: RecyclerView) {
        val fragment = fragmentRef.get() ?: return
        val weakThis = WeakReference(this)
        val weakRecyclerView = WeakReference(recyclerView)

        fragment.viewLifecycleOwner.lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                val actualRecyclerView = weakRecyclerView.get() ?: return
                when (event.targetState) {
                    Lifecycle.State.DESTROYED -> {
                        clear()
                        actualRecyclerView.adapter = null
                    }
                    Lifecycle.State.RESUMED -> {
                        val self = weakThis.get() ?: return
                        if (actualRecyclerView.adapter != self) {
                            actualRecyclerView.adapter = self
                        }
                    }
                    else -> {}
                }
            }
        })
    }
}