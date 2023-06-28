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

package com.xuexiang.databindingsample.fragment

import com.xuexiang.databindingsample.core.BaseContainerFragment
import com.xuexiang.databindingsample.fragment.basic.*
import com.xuexiang.xpage.annotation.Page

/**
 * 基础使用演示
 *
 * @author xuexiang
 * @since 2023/4/23 21:20
 */
@Page(name = "基础使用演示")
class BasicUseFragment : BaseContainerFragment() {

    override fun getPagesClasses() = arrayOf(
        ClickBindFragment::class.java,
        BindingAdapterFragment::class.java,
        ExpressionFragment::class.java,
        IncludeViewStubFragment::class.java,
        CustomViewFragment::class.java,
        CombineLiveDataFragment::class.java
    )
}