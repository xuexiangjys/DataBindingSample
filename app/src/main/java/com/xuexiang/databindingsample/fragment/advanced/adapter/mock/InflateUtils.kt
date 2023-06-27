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

package com.xuexiang.databindingsample.fragment.advanced.adapter.mock

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xuexiang.xui.logs.UILog
import java.util.concurrent.TimeUnit

/**
 * 模拟耗时加载工具类
 *
 * @author xuexiang
 * @since 2023/6/27 22:26
 */
object InflateUtils {

    /**
     * 模拟耗时加载
     */
    fun mockLongTimeLoad(parent: ViewGroup, layoutId: Int): View {
        try {
            // 模拟耗时
            Thread.sleep(300)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        return getInflateView(parent, layoutId)
    }

    fun getInflateView(parent: ViewGroup, layoutId: Int) = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)

    /**
     * 模拟耗时加载
     */
    fun mockLongTimeLoad(inflater: LayoutInflater, parent: ViewGroup?, layoutId: Int): View {
        val startNanos = System.nanoTime()
        try {
            // 模拟耗时
            Thread.sleep(300)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        val view = inflater.inflate(layoutId, parent, false)
        val cost = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNanos)
        UILog.dTag("InflateUtils", "mockLongTimeLoad cost:$cost ms")
        return view
    }

}