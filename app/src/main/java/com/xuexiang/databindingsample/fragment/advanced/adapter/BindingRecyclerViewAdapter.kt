/*
 * Copyright (C) 2023 xuexiangjys(xuexiangjys@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.xuexiang.databindingsample.fragment.advanced.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * 供绑定的RecyclerView.Adapter
 *
 * @author xuexiang
 * @since 2023/5/4 23:20
 */
class BindingRecyclerViewAdapter<T>(
    private val itemViewParser: ItemViewParser,
    var dataSource: MutableList<T>,
    var selectedPosition: Int?,
    var onItemClickListener: OnItemClickListener<T>?,
    var onItemLongClickListener: OnItemLongClickListener<T>?,
) : RecyclerView.Adapter<BindingViewHolder<T>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder<T> {
        val layoutInflater = LayoutInflater.from(parent.context)
        val holder = createViewHolder(layoutInflater, parent, viewType)
        initViewHolder(holder)
        return holder
    }

    private fun createViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): BindingViewHolder<T> {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            layoutInflater,
            itemViewParser.getItemLayoutId(viewType),
            parent,
            false
        )
        val holder = BindingViewHolder<T>(binding)
        binding.lifecycleOwner = holder
        return holder
    }

    private fun initViewHolder(holder: BindingViewHolder<T>) {
        onItemClickListener?.run {
            holder.itemView.setOnClickListener {
                val position = holder.itemView.tag as Int
                onItemClick(it, dataSource.getOrNull(position), position)
            }
        }
        onItemLongClickListener?.run {
            holder.itemView.setOnLongClickListener {
                val position = holder.itemView.tag as Int
                onItemLongClick(it, dataSource.getOrNull(position), position)
            }
        }
    }

    override fun onBindViewHolder(holder: BindingViewHolder<T>, position: Int) {
        holder.bindingData(dataSource.getOrNull(position))
        selectedPosition?.let {
            holder.itemView.isSelected = position == it
        }
        holder.itemView.tag = position
        if (holder.binding.hasPendingBindings()) holder.binding.executePendingBindings()
    }

    override fun getItemCount() = dataSource.size

    @SuppressLint("NotifyDataSetChanged")
    fun refresh(data: List<T>, position: Int?) {
        if (data.isNotEmpty()) {
            dataSource = data.toMutableList()
            selectedPosition = position
            notifyDataSetChanged()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun loadMore(data: List<T>) {
        if (data.isNotEmpty()) {
            dataSource.addAll(data)
            notifyDataSetChanged()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return itemViewParser.getItemViewType(position)
    }

    override fun onViewAttachedToWindow(holder: BindingViewHolder<T>) {
        holder.onAttached()
    }

    override fun onViewDetachedFromWindow(holder: BindingViewHolder<T>) {
        holder.onDetached()
    }

    override fun onViewRecycled(holder: BindingViewHolder<T>) {
        holder.binding.lifecycleOwner = null
        super.onViewRecycled(holder)
    }
}