package com.base.myapplication.features.fragA

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.base.core.base.BaseRecycleViewAdapter
import com.base.core.model.BaseResponse
import com.base.myapplication.databinding.ItemRecycleviewBinding

/**
 * Created by Wildan Nafian on 25/05/2022.
 * Github https://github.com/Wildanafian
 * wildanafian8@gmail.com
 */


class ExampleAdapter : BaseRecycleViewAdapter<BaseResponse, ExampleAdapter.ViewHolder>() {

    var someClickListener: ((BaseResponse) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemRecycleviewBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getBindViewHolder(holder: ViewHolder, position: Int, data: BaseResponse) {
        holder.bind(data)
        holder.itemView.setOnClickListener {
            someClickListener?.invoke(data)
        }
    }

    class ViewHolder(private val binding: ItemRecycleviewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: BaseResponse) {
            binding.itemData = data
            binding.executePendingBindings()
        }
    }
}