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
import androidx.lifecycle.MutableLiveData
import com.xuexiang.databindingsample.R
import com.xuexiang.databindingsample.core.databinding.DataBindingPageState
import com.xuexiang.databindingsample.fragment.advanced.adapter.ItemViewParser

/**
 * RecycleView的多种布局演示
 *
 * @author xuexiang
 * @since 2023/4/23 00:20
 */
class RecyclerViewMultipleLayoutState(application: Application) :
    DataBindingPageState(application) {

    override fun initTitle() = "RecycleView的多种布局演示"

    val sampleData = MutableLiveData(getDemoData(application))

    val itemViewParser = MutableLiveData<ItemViewParser>(object : ItemViewParser {
        override fun getItemViewType(position: Int) =
            if (position % 2 == 0) {
                VIEW_TYPE_1
            } else {
                VIEW_TYPE_2
            }

        override fun getItemLayoutId(viewType: Int) =
            if (viewType == VIEW_TYPE_1) {
                R.layout.databinding_item_simple_list_1
            } else {
                R.layout.databinding_item_simple_list_2
            }
    })
}


const val VIEW_TYPE_1 = 1
const val VIEW_TYPE_2 = 2

