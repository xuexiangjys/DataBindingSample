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
package com.xuexiang.databindingsample.fragment.basic

import com.xuexiang.databindingsample.R
import com.xuexiang.databindingsample.core.databinding.DataBindingFragment
import com.xuexiang.databindingsample.databinding.FragmentBindingAdapterBinding
import com.xuexiang.databindingsample.fragment.basic.model.AdapterState
import com.xuexiang.xpage.annotation.Page

/**
 * 控件自定义属性绑定演示
 * 使用@BindingAdapter进行绑定
 * BindingAdapter: 设置自定义属性. 可以覆盖系统原有属性
 * BindingMethod/BindingMethods: 关联自定义属性到控件原有的setter方法
 * BindingConversion: 如果属性不能匹配类型参数将自动根据类型参数匹配到该注解修饰的方法来转换
 * InverseMethod: 负责实现视图和数据之间的转换
 * InverseBindingAdapter: 视图通知数据刷新的
 * InverseBindingMethod/InverseBindingMethods: 视图通知数据刷新的(如果存在已有getter方法可用的情况下)
 * BindingMethods系优先级高于BindingAdapter系列
 * 所有注解的功能都是基于XML属性值为Databinding表达式才生效(即@{})
 * 建议参考官方实现源码: DataBindingAdapter: https://android.googlesource.com/platform/frameworks/data-binding/+/android-7.0.0_r19/extensions/baseAdapters/src/main/java/android/databinding/adapters?autodive=0/
 *
 * @author xuexiang
 * @since 2019-07-08 00:52
 */
@Page(name = "@BindingAdapter的使用")
class BindingAdapterFragment : DataBindingFragment<FragmentBindingAdapterBinding?, AdapterState>() {

    override fun getLayoutId() = R.layout.fragment_binding_adapter

    /**
     * 初始化控件
     */
    override fun initViews() {}
}