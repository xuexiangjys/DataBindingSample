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

package com.xuexiang.databindingsample.fragment.advanced.preload.model

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener
import com.scwang.smart.refresh.layout.listener.OnRefreshListener
import com.xuexiang.databindingsample.core.databinding.DataBindingPageState
import com.xuexiang.databindingsample.fragment.advanced.adapter.extensions.LoadState

open class NewListState(application: Application) : DataBindingPageState(application) {

    override fun initTitle() = "ViewHolder异步预加载优化演示"

    val enablePreload = MutableLiveData(false)

    val data = MutableLiveData<List<NewInfo>>(arrayListOf())

    val loadState = MutableLiveData(LoadState.DEFAULT)

    val refreshListener = OnRefreshListener { refreshLayout ->
        refreshLayout.layout.postDelayed({
            loadState.value = LoadState.REFRESH
            data.value = getRefreshDemoNewInfos()
            refreshLayout.finishRefresh()
            refreshLayout.resetNoMoreData()
        }, 1000)
    }

    val loadMoreListener = OnLoadMoreListener { refreshLayout ->
        refreshLayout.layout.postDelayed({
            loadState.value = LoadState.LOAD_MORE
            data.value = getRefreshDemoNewInfos()
            refreshLayout.finishLoadMore()
        }, 1000)
    }

    private fun getRefreshDemoNewInfos(): List<NewInfo> {
        val list: MutableList<NewInfo> = ArrayList()
        list.add(
            NewInfo("公众号", "X-Library系列文章视频介绍").apply {
                summary = "获取更多咨询，欢迎点击关注公众号:【我的Android开源之旅】，里面有一整套X-Library系列文章视频介绍！\n"
                detailUrl =
                    "http://mp.weixin.qq.com/mp/homepage?__biz=Mzg2NjA3NDIyMA==&hid=5&sn=bdee5aafe9cc2e0a618d055117c84139&scene=18#wechat_redirect"
                imageUrl =
                    "https://p6-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/463930705a844f638433d1b26273a7cf~tplv-k3u1fbpfcp-watermark.image"
            }
        )
        list.add(
            NewInfo("Android UI", "XUI 一个简洁而优雅的Android原生UI框架，解放你的双手").apply {
                summary =
                    "涵盖绝大部分的UI组件：TextView、Button、EditText、ImageView、Spinner、Picker、Dialog、PopupWindow、ProgressBar、LoadingView、StateLayout、FlowLayout、Switch、Actionbar、TabBar、Banner、GuideView、BadgeView、MarqueeView、WebView、SearchView等一系列的组件和丰富多彩的样式主题。\n"
                detailUrl = "https://juejin.im/post/5c3ed1dae51d4543805ea48d"
                imageUrl =
                    "https://p6-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/463930705a844f638433d1b26273a7cf~tplv-k3u1fbpfcp-watermark.image"
            }
        )
        list.add(
            NewInfo("Android", "XUpdate 一个轻量级、高可用性的Android版本更新框架").apply {
                summary =
                    "XUpdate 一个轻量级、高可用性的Android版本更新框架。本框架借鉴了AppUpdate中的部分思想和UI界面，将版本更新中的各部分环节抽离出来，形成了如下几个部分：\n"
                detailUrl = "https://juejin.im/post/5b480b79e51d45190905ef44"
                imageUrl =
                    "https://p6-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/463930705a844f638433d1b26273a7cf~tplv-k3u1fbpfcp-watermark.image"
            }
        )
        list.add(
            NewInfo(
                "Android/HTTP",
                "XHttp2 一个功能强悍的网络请求库，使用RxJava2 + Retrofit2 + OKHttp进行组装"
            ).apply {
                summary = "一个功能强悍的网络请求库，使用RxJava2 + Retrofit2 + OKHttp组合进行封装。还不赶紧点击使用说明文档，体验一下吧！\n"
                detailUrl = "https://juejin.im/post/5b6b9b49e51d4576b828978d"
                imageUrl =
                    "https://p6-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/463930705a844f638433d1b26273a7cf~tplv-k3u1fbpfcp-watermark.image"
            }
        )
        list.add(
            NewInfo("源码", "Android源码分析--Android系统启动").apply {
                summary =
                    "其实Android系统的启动最主要的内容无非是init、Zygote、SystemServer这三个进程的启动，他们一起构成的铁三角是Android系统的基础。"
                detailUrl = "https://juejin.im/post/5c6fc0cdf265da2dda694f05"
                imageUrl =
                    "https://p6-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/463930705a844f638433d1b26273a7cf~tplv-k3u1fbpfcp-watermark.image"
            }
        )
        return list
    }
}