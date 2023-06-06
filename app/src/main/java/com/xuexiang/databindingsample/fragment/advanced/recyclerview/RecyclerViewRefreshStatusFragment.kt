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

package com.xuexiang.databindingsample.fragment.advanced.recyclerview

import com.xuexiang.databindingsample.R
import com.xuexiang.databindingsample.core.databinding.DataBindingFragment
import com.xuexiang.databindingsample.databinding.FragmentRecyclerviewRefreshStatusBinding
import com.xuexiang.databindingsample.fragment.advanced.recyclerview.model.RecyclerViewRefreshStatusState
import com.xuexiang.xpage.annotation.Page

/**
 * RecyclerView的刷新状态布局
 *
 * @author xuexiang
 * @since 2023/6/1 23:37
 */
@Page(name = "RecyclerView的刷新状态布局\n自动切换状态，包含出错、无网络、暂无数据等")
class RecyclerViewRefreshStatusFragment :
    DataBindingFragment<FragmentRecyclerviewRefreshStatusBinding, RecyclerViewRefreshStatusState>() {

    override fun getLayoutId() = R.layout.fragment_recyclerview_refresh_status

    override fun initViews() {
        binding?.refreshLayout?.autoRefresh()
    }

    override fun onDataBindingUpdate(binding: FragmentRecyclerviewRefreshStatusBinding) {
        super.onDataBindingUpdate(binding)
        viewModel.setDataBindingProvider(this)
    }

}