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

package com.xuexiang.databindingsample.core.databinding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel

/**
 * DataBind拓展方法
 *
 * @author xuexiang
 * @since 2023/4/23 00:05
 */
fun <DataBinding : ViewDataBinding> DataBinding.bindViewModel(
    viewLifecycleOwner: LifecycleOwner,
    viewModel: ViewModel,
    onBinding: OnDataBindingListener<DataBinding>? = null,
    variableId: Int = BR.state
): View {
    lifecycleOwner = viewLifecycleOwner
    setVariable(variableId, viewModel)
    onBinding?.onDataBindingUpdate(this)
    return root
}

fun <DataBinding : ViewDataBinding> bindActivity(
    activity: ComponentActivity,
    layoutId: Int,
    onBinding: OnDataBindingListener<DataBinding>,
): DataBinding = DataBindingUtil.setContentView<DataBinding>(activity, layoutId).apply {
    lifecycleOwner = activity
    onBinding.onDataBindingUpdate(this)
}

fun <DataBinding : ViewDataBinding> bindView(
    inflater: LayoutInflater,
    layoutId: Int,
    parent: ViewGroup? = null,
    attachToParent: Boolean = false,
    onBinding: OnDataBindingListener<DataBinding>? = null,
): DataBinding = DataBindingUtil.inflate<DataBinding>(inflater, layoutId, parent, attachToParent).apply {
    onBinding?.onDataBindingUpdate(this)
}

/**
 * DataBinding监听
 *
 * @author xuexiang
 * @since 2023/4/23 00:11
 */
interface OnDataBindingListener<DataBinding : ViewDataBinding> {
    /**
     * DataBinding更新
     * @param binding DataBinding
     */
    fun onDataBindingUpdate(binding: DataBinding)

    /**
     * DataBinding解绑
     */
    fun onDataBindingUnbind()
}

/**
 * 提供DataBinding的接口
 *
 * @author xuexiang
 * @since 2023/4/23 00:11
 */
interface IDataBindingProvider<DataBinding : ViewDataBinding> {

    fun getViewBinding(): DataBinding?
}






