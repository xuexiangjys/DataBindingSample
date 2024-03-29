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

import android.view.View
import com.xuexiang.databindingsample.R
import com.xuexiang.databindingsample.core.databinding.DataBindingFragment
import com.xuexiang.databindingsample.databinding.FragmentIncludeViewstubBinding
import com.xuexiang.databindingsample.fragment.basic.model.IncludeViewStubState
import com.xuexiang.xpage.annotation.Page

/**
 * DataBinding中include和viewStub的使用
 *
 * @author xuexiang
 * @since 2019-07-08 00:52
 */
@Page(name = "include和viewStub使用")
class IncludeViewStubFragment : DataBindingFragment<FragmentIncludeViewstubBinding, IncludeViewStubState>() {

    override fun getLayoutId() = R.layout.fragment_include_viewstub

    override fun initViews() {
//        binding?.includeLayout?.tvTitle?.text = "用户信息"
        viewModel.onClickLoadViewStub = View.OnClickListener {
            binding?.userInfo?.viewStub?.inflate()
        }
    }
}