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

package com.xuexiang.databindingsample.fragment.advanced.recyclerview.model

import android.content.Context
import com.xuexiang.databindingsample.R

/**
 * 演示数据
 *
 * @author xuexiang
 * @since 2023/5/9 00:38
 */
fun getDemoData(context: Context, from: Int = 1, to: Int = 40): List<SimpleItem> {
    val list = mutableListOf<SimpleItem>()
    for (index in from..to) {
        list.add(
            SimpleItem(
                context.getString(R.string.item_example_number_title, index),
                context.getString(R.string.item_example_number_subtitle, index)
            )
        )
    }
    return list
}

data class SimpleItem(
    val title: String? = "",
    val subTitle: String? = "",
)

const val PAGE_SIZE = 10
