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
package com.xuexiang.databindingsample.fragment.advanced.preload.core

import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import com.xuexiang.xui.logs.UILog
import java.lang.ref.SoftReference
import java.util.*

/**
 * 预加载辅助工具类
 * 灵感源于：https://blog.csdn.net/alienttech/article/details/106759615
 *
 * @author xuexiang
 * @since 6/21/23 1:41 AM
 */
class PreInflateHelper(var layoutInflater: ILayoutInflater = DefaultLayoutInflater.get()) {

    private val mViewCache = ViewCache()

    @JvmOverloads
    fun preloadOnce(parent: ViewGroup, layoutId: Int, maxCount: Int = DEFAULT_PRELOAD_COUNT) {
        preload(parent, layoutId, maxCount, 1)
    }

    @JvmOverloads
    fun preload(
        parent: ViewGroup,
        layoutId: Int,
        maxCount: Int = DEFAULT_PRELOAD_COUNT,
        forcePreCount: Int = 0
    ) {
        val viewsAvailableCount = mViewCache.getViewPoolAvailableCount(layoutId)
        if (viewsAvailableCount >= maxCount) {
            return
        }
        var needPreloadCount = maxCount - viewsAvailableCount
        if (forcePreCount > 0) {
            needPreloadCount = Math.min(forcePreCount, needPreloadCount)
        }
        UILog.dTag(
            TAG,
            "needPreloadCount:$needPreloadCount, viewsAvailableCount:$viewsAvailableCount"
        )
        for (i in 0 until needPreloadCount) {
            // 异步加载View
            preAsyncInflateView(parent, layoutId)
        }
    }

    private fun preAsyncInflateView(parent: ViewGroup, layoutId: Int) {
        layoutInflater.asyncInflateView(parent, layoutId, object : InflateCallback {
            override fun onInflateFinished(layoutId: Int, view: View?) {
                mViewCache.putView(layoutId, view)
                UILog.dTag(
                    TAG,
                    "mViewCache + 1, viewsAvailableCount:" + mViewCache.getViewPoolAvailableCount(
                        layoutId
                    )
                )
            }
        })
    }

    @JvmOverloads
    fun getView(parent: ViewGroup, layoutId: Int, maxCount: Int = DEFAULT_PRELOAD_COUNT): View {
        val view = mViewCache.getView(layoutId)
        if (view != null) {
            UILog.dTag(TAG, "get view from cache!")
            preloadOnce(parent, layoutId, maxCount)
            return view
        }
        return layoutInflater.inflateView(parent, layoutId)
    }

    private inner class ViewCache {

        private val mViewPools = SparseArray<LinkedList<SoftReference<View?>>>()

        fun getViewPool(layoutId: Int): LinkedList<SoftReference<View?>> {
            var views = mViewPools[layoutId]
            if (views == null) {
                views = LinkedList()
                mViewPools.put(layoutId, views)
            }
            return views
        }

        fun getViewPoolAvailableCount(layoutId: Int): Int {
            val views = getViewPool(layoutId)
            val it = views.iterator()
            var count = 0
            while (it.hasNext()) {
                if (it.next().get() != null) {
                    count++
                } else {
                    it.remove()
                }
            }
            return count
        }

        fun putView(layoutId: Int, view: View?) {
            if (view == null) {
                return
            }
            getViewPool(layoutId).offer(SoftReference(view))
        }

        fun getView(layoutId: Int) = getViewFromPool(getViewPool(layoutId))

        private fun getViewFromPool(views: LinkedList<SoftReference<View?>>): View? {
            return if (views.isEmpty()) {
                null
            } else views.pop().get() ?: return getViewFromPool(views)
        }
    }

    interface ILayoutInflater {
        /**
         * 异步加载View
         *
         * @param parent   父布局
         * @param layoutId 布局资源id
         * @param callback 加载回调
         */
        fun asyncInflateView(parent: ViewGroup, layoutId: Int, callback: InflateCallback?)

        /**
         * 同步加载View
         *
         * @param parent   父布局
         * @param layoutId 布局资源id
         * @return 加载的View
         */
        fun inflateView(parent: ViewGroup, layoutId: Int): View
    }

    interface InflateCallback {
        fun onInflateFinished(layoutId: Int, view: View?)
    }

    companion object {
        private const val TAG = "PreInflateHelper"

        /**
         * 默认的预加载缓存池大小，默认是5，可根据需求设置
         */
        const val DEFAULT_PRELOAD_COUNT = 5
    }
}