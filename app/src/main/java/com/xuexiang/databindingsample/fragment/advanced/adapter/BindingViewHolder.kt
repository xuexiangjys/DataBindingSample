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

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.recyclerview.widget.RecyclerView
import com.xuexiang.databindingsample.BR
import com.xuexiang.databindingsample.fragment.advanced.preload.core.PreloadConfig

/**
 *
 * 供绑定的ViewHolder
 *
 * @author xuexiang
 * @since 2023/5/4 22:47
 */
class BindingViewHolder<T>(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root),
    LifecycleOwner {

    private val mLifecycle = LifecycleRegistry(this)

    fun bindingData(data: T?, variableId: Int = BR.item) {
        binding.setVariable(variableId, data)
    }

    init {
        mLifecycle.currentState = Lifecycle.State.INITIALIZED
    }

    fun onAttached() {
        mLifecycle.currentState = Lifecycle.State.STARTED
    }

    fun onDetached() {
        mLifecycle.currentState = Lifecycle.State.RESUMED
    }

    override fun getLifecycle(): Lifecycle = mLifecycle

}

/**
 * 默认的布局解析器
 */
class DefaultItemViewParser(@LayoutRes val layoutId: Int): ItemViewParser {

    override fun getItemViewType(position: Int) = 0

    override fun getItemLayoutId(viewType: Int) = layoutId

    override fun getPreloadConfigs() = arrayListOf(PreloadConfig(layoutId))

}

/**
 * 布局解析器
 */
interface ItemViewParser {

    fun getItemViewType(position: Int): Int

    fun getItemLayoutId(viewType: Int): Int

    fun getPreloadConfigs() = emptyList<PreloadConfig>()
}
