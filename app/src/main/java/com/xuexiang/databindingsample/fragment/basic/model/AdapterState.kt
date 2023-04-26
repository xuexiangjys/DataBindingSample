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

package com.xuexiang.databindingsample.fragment.basic.model

import com.xuexiang.databindingsample.core.databinding.DataBindingState

/**
 * 控件自定义属性绑定演示
 * 使用@BindingAdapter进行绑定
 *
 *
 *
 * @author xuexiang
 * @since 2023/4/23 00:20
 */
class AdapterState : DataBindingState() {

    override fun initTitle() = "控件自定义属性绑定演示"



}

data class User(
    val name: String,
    val gender: String? = "男",
    val age: Int = 10,
    val phone: String? = ""
)