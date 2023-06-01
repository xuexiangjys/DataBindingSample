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
package com.xuexiang.databindingsample.fragment.basic

import com.xuexiang.databindingsample.R
import com.xuexiang.databindingsample.core.databinding.DataBindingFragment
import com.xuexiang.databindingsample.databinding.FragmentClickBindBinding
import com.xuexiang.databindingsample.fragment.basic.model.ClickState
import com.xuexiang.xpage.annotation.Page

/**
 * 点击事件绑定演示
 *
 * @author xuexiang
 * @since 2019-07-08 00:52
 */
@Page(name = "点击事件绑定")
class ClickBindFragment : DataBindingFragment<FragmentClickBindBinding, ClickState>() {

    override fun getLayoutId() = R.layout.fragment_click_bind
}