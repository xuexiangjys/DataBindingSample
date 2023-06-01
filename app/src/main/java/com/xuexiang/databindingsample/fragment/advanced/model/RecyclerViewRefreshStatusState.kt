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
import android.content.Context
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener
import com.scwang.smart.refresh.layout.listener.OnRefreshListener
import com.xuexiang.databindingsample.core.databinding.DataBindingProviderState
import com.xuexiang.databindingsample.databinding.FragmentRecyclerviewRefreshStatusBinding
import com.xuexiang.databindingsample.fragment.advanced.adapter.extensions.LoadState
import com.xuexiang.databindingsample.fragment.advanced.adapter.extensions.Status

/**
 * RecyclerView的刷新和加载更多演示
 *
 * @author xuexiang
 * @since 2023/4/23 00:20
 */
class RecyclerViewRefreshStatusState(application: Application) :
    DataBindingProviderState<FragmentRecyclerviewRefreshStatusBinding>(application) {

    override fun initTitle() = "RecyclerView的刷新和加载更多演示"

    val sampleData = MutableLiveData<List<SimpleItem>>(arrayListOf())

    val loadState = MutableLiveData(LoadState.DEFAULT)

    val layoutStatus = MutableLiveData(Status.DEFAULT)

    val retryListener = View.OnClickListener {
        getBinding()?.refreshLayout?.autoRefresh()
    }

    private var pageIndex = 0

    val refreshListener = OnRefreshListener { refreshLayout ->
        refreshLayout.layout.postDelayed({
            val status = getRefreshStatus()
            layoutStatus.value = status
            when (status) {
                Status.SUCCESS -> {
                    pageIndex = 0
                    loadState.value = LoadState.REFRESH
                    sampleData.value = sampleGetData(application)
                    refreshLayout.resetNoMoreData()
                    refreshLayout.setEnableLoadMore(true)
                }
                Status.EMPTY,
                Status.ERROR,
                Status.NO_NET ->
                    refreshLayout.setEnableLoadMore(false)
                else -> {}
            }
            refreshLayout.finishRefresh()
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

    private fun getRefreshStatus(): Status {
        val status = (Math.random() * 10).toInt()
        return if (status % 2 == 0) {
            Status.SUCCESS
        } else if (status % 3 == 0) {
            Status.EMPTY
        } else if (status % 5 == 0) {
            Status.ERROR
        } else {
            Status.NO_NET
        }
    }
}

