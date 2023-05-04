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

package com.xuexiang.databindingsample.fragment.advanced.model

import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.xuexiang.databindingsample.R
import com.xuexiang.databindingsample.core.databinding.DataBindingPageState

/**
 * 简单的RecycleView使用演示
 *
 * @author xuexiang
 * @since 2023/4/23 00:20
 */
class RecyclerViewSimpleState(application: Application) : DataBindingPageState(application) {

    override fun initTitle() = "简单的RecycleView使用演示"

    val sampleData = MutableLiveData(getDemoData(application))

}


fun getDemoData(context: Context): List<SimpleItem> {
    val list = mutableListOf<SimpleItem>()
    for (index in 1..40) {
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

