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

package com.xuexiang.databindingsample.core.databinding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData


/**
 * 组合LiveData
 */
open class CombinedLiveData<T>(vararg liveData: LiveData<*>, block: () -> T) :
    MediatorLiveData<T>() {
    init {
        value = block()
        liveData.forEach {
            addSource(it) {
                val newValue = block()
                if (value != newValue) {
                    value = newValue
                }
            }
        }
    }
}

fun <R, T1, T2> combineLiveData(
    liveData1: LiveData<T1>,
    liveData2: LiveData<T2>,
    block: (T1?, T2?) -> R
) = CombinedLiveData(liveData1, liveData2) { block(liveData1.value, liveData2.value) }