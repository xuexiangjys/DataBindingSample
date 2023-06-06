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

package com.xuexiang.databindingsample.fragment.advanced.custom

import com.xuexiang.databindingsample.R
import com.xuexiang.databindingsample.core.databinding.DataBindingFragment
import com.xuexiang.databindingsample.databinding.FragmentCustomViewStateBinding
import com.xuexiang.databindingsample.fragment.advanced.custom.model.CustomViewState
import com.xuexiang.xpage.annotation.Page

/**
 * 自定义View的state使用
 *
 * @author xuexiang
 * @since 2023/5/2 16:44
 */
@Page(name = "自定义View的state使用")
class CustomViewStateFragment : DataBindingFragment<FragmentCustomViewStateBinding, CustomViewState>() {

    override fun getLayoutId() = R.layout.fragment_custom_view_state
}