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

package com.xuexiang.databindingsample.fragment.advanced

import com.xuexiang.databindingsample.core.BaseContainerFragment
import com.xuexiang.databindingsample.fragment.advanced.custom.CustomViewStateFragment
import com.xuexiang.databindingsample.fragment.advanced.recyclerview.RecyclerViewBasicFragment
import com.xuexiang.databindingsample.fragment.advanced.recyclerview.RecyclerViewMultipleLayoutsFragment
import com.xuexiang.databindingsample.fragment.advanced.recyclerview.RecyclerViewRefreshFragment
import com.xuexiang.databindingsample.fragment.advanced.recyclerview.RecyclerViewRefreshStatusFragment
import com.xuexiang.xpage.annotation.Page

/**
 * 自定义组件的state使用
 *
 * @author xuexiang
 * @since 2023/5/2 16:42
 */
@Page(name = "自定义组件的state使用")
class CustomComponentStateFragment : BaseContainerFragment() {

    override fun getPagesClasses() = arrayOf(
        CustomViewStateFragment::class.java,
    )
}