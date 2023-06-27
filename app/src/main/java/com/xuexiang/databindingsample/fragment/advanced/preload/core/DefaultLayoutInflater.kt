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

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.ContextThemeWrapper
import androidx.asynclayoutinflater.view.AsyncLayoutInflater
import com.xuexiang.databindingsample.fragment.advanced.preload.core.PreInflateHelper.ILayoutInflater
import com.xuexiang.databindingsample.fragment.advanced.preload.core.PreInflateHelper.InflateCallback

class DefaultLayoutInflater private constructor() : ILayoutInflater {

    private var mInflater: AsyncLayoutInflater? = null

    private object InstanceHolder {
        val sInstance = DefaultLayoutInflater()
    }

    override fun asyncInflateView(parent: ViewGroup, layoutId: Int, callback: InflateCallback?) {
        if (mInflater == null) {
            mInflater = AsyncLayoutInflater(
                ContextThemeWrapper(
                    parent.context.applicationContext,
                    parent.context.theme
                )
            )
        }
        mInflater?.inflate(
            layoutId,
            parent
        ) { view: View?, resId: Int, _: ViewGroup? ->
            callback?.onInflateFinished(
                resId,
                view
            )
        }
    }

    override fun inflateView(parent: ViewGroup, layoutId: Int): View {
        return LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
    }

    companion object {
        fun get(): DefaultLayoutInflater {
            return InstanceHolder.sInstance
        }
    }
}