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

package com.xuexiang.databindingsample.fragment.advanced.adapter.extensions

import androidx.annotation.ColorInt
import androidx.annotation.LayoutRes
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xuexiang.databindingsample.fragment.advanced.adapter.*
import com.xuexiang.xui.adapter.recyclerview.XLinearLayoutManager
import kotlin.math.roundToInt

/**
 * 供RecyclerView绑定的方法
 *
 * @author xuexiang
 * @since 2023/5/2 16:53
 */
@BindingAdapter(
    value = ["data", "itemLayout", "itemViewParser", "enablePreload", "loadState", "dividerHeight", "dividerColor", "selectedPosition", "itemClick", "itemLongClick", "isMock"],
    requireAll = false
)
fun <T> RecyclerView.setBindingRecyclerViewAdapter(
    data: List<T>?,
    @LayoutRes layoutId: Int?,
    itemViewParser: ItemViewParser?,
    enablePreload: Boolean = false,
    loadState: LoadState? = LoadState.DEFAULT,
    dividerHeight: Float? = null,
    @ColorInt dividerColor: Int? = null,
    selectedPosition: Int? = null,
    onItemClickListener: OnItemClickListener<T>? = null,
    onItemLongClickListener: OnItemLongClickListener<T>? = null,
    isMock: Boolean = false
) {
    requireNotNull(data) { "app:data argument cannot be null!" }
    require(layoutId != null || itemViewParser != null) { "app:itemLayout and app:itemViewParser argument need a parameter that is not null!" }

    if (adapter == null) {
        val parser = itemViewParser ?: DefaultItemViewParser(layoutId!!)
        adapter = if (enablePreload) {
            BindingPreloadRecyclerViewAdapter(
                this,
                parser,
                data.toMutableList(),
                selectedPosition,
                onItemClickListener,
                onItemLongClickListener,
                isMock
            )
        } else {
            BindingRecyclerViewAdapter(
                parser,
                data.toMutableList(),
                selectedPosition,
                onItemClickListener,
                onItemLongClickListener,
                isMock
            )
        }
        layoutManager = XLinearLayoutManager(context)
        setDividerStyle(dividerHeight, dividerColor)
    } else {
        @Suppress("UNCHECKED_CAST")
        (adapter as? BindingRecyclerViewAdapter<T>)?.run {
            when (loadState) {
                LoadState.REFRESH -> refresh(data, selectedPosition)
                LoadState.LOAD_MORE -> loadMore(data)
                else -> {}
            }
        }
    }
}


fun RecyclerView.setDividerStyle(
    dividerHeight: Float? = null,
    @ColorInt dividerColor: Int? = null
) {
    val divider = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
    dividerHeight?.let {
        divider.dividerHeight = it.roundToInt()
    }
    dividerColor?.let {
        divider.dividerColor = it
    }
    addItemDecoration(divider)
}

enum class LoadState {
    DEFAULT,
    REFRESH,
    LOAD_MORE
}


