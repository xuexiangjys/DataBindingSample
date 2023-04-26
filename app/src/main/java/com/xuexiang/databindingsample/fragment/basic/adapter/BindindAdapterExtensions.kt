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

package com.xuexiang.databindingsample.fragment.basic.adapter

import android.widget.TextView
import androidx.databinding.BindingAdapter

// Kotlin拓展函数式写法, 推荐使用
@BindingAdapter("customTitle")
fun TextView.setCustomTitle(title: String) {
    text = "标题1: $title"
}

// 第一个参数必须是view的子类
@BindingAdapter("customTitle1")
fun setCustomTitle1(view: TextView, title: String) {
    view.text = "标题2: $title"
}

// 多个参数进行绑定，requireAll=true，代表两个参数都设置了才生效
@BindingAdapter(value = ["customTitle", "customSize"], requireAll = true)
fun TextView.setTextContent(title: String, size: Int) {
    text = "标题3: $title"
    textSize = size.toFloat()
}
