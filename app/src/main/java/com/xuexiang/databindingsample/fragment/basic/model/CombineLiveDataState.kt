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

package com.xuexiang.databindingsample.fragment.basic.model

import androidx.lifecycle.MutableLiveData
import com.xuexiang.databindingsample.core.databinding.DataBindingState
import com.xuexiang.databindingsample.core.databinding.combineLiveData

/**
 * 组合LiveData的使用演示
 *
 * @author xuexiang
 * @since 2023/4/23 00:20
 */
class CombineLiveDataState : DataBindingState() {

    override fun initTitle() = "组合LiveData的使用演示"

    val userName = MutableLiveData("小明")

    val userAge = MutableLiveData(20)

    val userInfo = combineLiveData(userName, userAge) { name, age ->
        "${name}今年${age}岁了!"
    }

    fun onAgeChanged() {
        userAge.value = userAge.value?.plus(1)
    }
}