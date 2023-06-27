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

import android.util.SparseIntArray
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.xuexiang.databindingsample.fragment.advanced.adapter.mock.MockLongTimeLayoutInflater
import com.xuexiang.databindingsample.fragment.advanced.preload.core.DefaultLayoutInflater
import com.xuexiang.databindingsample.fragment.advanced.preload.core.PreInflateHelper

/**
 * 支持Preload的RecyclerView.Adapter
 *
 * @author xuexiang
 * @since 2023/6/27 02:47
 */
class BindingPreloadRecyclerViewAdapter<T>(
    recyclerView: RecyclerView,
    itemViewParser: ItemViewParser,
    dataSource: MutableList<T>,
    selectedPosition: Int?,
    onItemClickListener: OnItemClickListener<T>?,
    onItemLongClickListener: OnItemLongClickListener<T>?,
    isMock: Boolean = false
) : BindingRecyclerViewAdapter<T>(
    itemViewParser,
    dataSource,
    selectedPosition,
    onItemClickListener,
    onItemLongClickListener,
) {

    private val configMap = SparseIntArray()

    init {
        if (isMock) {
            getPreloadHelper().layoutInflater = MockLongTimeLayoutInflater.get()
        } else {
            getPreloadHelper().layoutInflater = DefaultLayoutInflater.get()
        }

        itemViewParser.getPreloadConfigs().forEach { config ->
            configMap.append(config.layoutId, config.maxCount)
            getPreloadHelper().preload(recyclerView, config.layoutId, config.maxCount)
        }
    }

    override fun inflateView(parent: ViewGroup, layoutId: Int): ViewDataBinding {
        return DataBindingUtil.bind(
            getPreloadHelper().getView(
                parent,
                layoutId,
                configMap.get(layoutId)
            )
        ) ?: super.inflateView(parent, layoutId)
    }

    private object InstanceHolder {
        val sInstance = PreInflateHelper()
    }

    companion object {
        fun getPreloadHelper() = InstanceHolder.sInstance
    }
}