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
import com.xuexiang.databindingsample.fragment.advanced.preload.AfterOptimizeFragment
import com.xuexiang.databindingsample.fragment.advanced.preload.BeforeOptimizeFragment
import com.xuexiang.xpage.annotation.Page

/**
 * ViewHolder异步预加载优化演示
 *
 * @author xuexiang
 * @since 6/20/23 12:33 AM
 */
@Page(name = "ViewHolder异步预加载优化")
class PreloadViewHolderFragment : BaseContainerFragment() {

    override fun getPagesClasses() = arrayOf(
        BeforeOptimizeFragment::class.java,
        AfterOptimizeFragment::class.java,
    )
}