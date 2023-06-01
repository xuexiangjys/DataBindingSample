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
import com.xuexiang.databindingsample.fragment.advanced.recyclerview.RecyclerViewBasicFragment
import com.xuexiang.databindingsample.fragment.advanced.recyclerview.RecyclerViewMultipleLayoutsFragment
import com.xuexiang.databindingsample.fragment.advanced.recyclerview.RecyclerViewRefreshFragment
import com.xuexiang.databindingsample.fragment.advanced.recyclerview.RecyclerViewRefreshStatusFragment
import com.xuexiang.xpage.annotation.Page

/**
 * 简化RecycleView的使用演示
 *
 * @author xuexiang
 * @since 2023/5/2 16:42
 */
@Page(name = "简化RecycleView的使用")
class RecyclerViewFragment : BaseContainerFragment() {

    override fun getPagesClasses() = arrayOf(
        RecyclerViewBasicFragment::class.java,
        RecyclerViewMultipleLayoutsFragment::class.java,
        RecyclerViewRefreshFragment::class.java,
        RecyclerViewRefreshStatusFragment::class.java,
    )
}