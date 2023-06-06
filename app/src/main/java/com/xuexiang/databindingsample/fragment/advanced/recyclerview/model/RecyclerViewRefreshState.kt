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

package com.xuexiang.databindingsample.fragment.advanced.recyclerview.model

import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener
import com.scwang.smart.refresh.layout.listener.OnRefreshListener
import com.xuexiang.databindingsample.core.databinding.DataBindingPageState
import com.xuexiang.databindingsample.fragment.advanced.adapter.extensions.LoadState

/**
 * RecyclerView的刷新和加载更多演示
 *
 * @author xuexiang
 * @since 2023/4/23 00:20
 */
class RecyclerViewRefreshState(application: Application) : DataBindingPageState(application) {

    override fun initTitle() = "RecyclerView的刷新和加载更多演示"

    val sampleData = MutableLiveData<List<SimpleItem>>(arrayListOf())

    val loadState = MutableLiveData(LoadState.DEFAULT)

    private var pageIndex = 0

    val refreshListener = OnRefreshListener { refreshLayout ->
        refreshLayout.layout.postDelayed({
            pageIndex = 0
            loadState.value = LoadState.REFRESH
            sampleData.value = sampleGetData(application)
            refreshLayout.finishRefresh()
            refreshLayout.resetNoMoreData()
        }, 1000)
    }

    val loadMoreListener = OnLoadMoreListener { refreshLayout ->
        refreshLayout.layout.postDelayed({
            pageIndex += 1
            loadState.value = LoadState.LOAD_MORE
            sampleData.value = sampleGetData(application)
            if (pageIndex >= 3) { // 模拟只能加载更多3页，即总共4页的数据
                refreshLayout.finishLoadMoreWithNoMoreData()
            } else {
                refreshLayout.finishLoadMore()
            }
        }, 1000)
    }

    /**
     * 模拟获取数据
     */
    private fun sampleGetData(context: Context) =
        getDemoData(context, pageIndex * PAGE_SIZE + 1, PAGE_SIZE * (pageIndex + 1))
}

