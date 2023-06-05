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

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.xuexiang.databindingsample.core.databinding.DataBindingState
import com.xuexiang.databindingsample.widget.CustomTextView.Companion.LARGE
import com.xuexiang.databindingsample.widget.CustomTextView.Companion.SMALL

/**
 *
 * 自定义View属性绑定演示
 *
 * @author xuexiang
 * @since 2023/6/6 02:21
 */
class CustomViewState : DataBindingState() {

    override fun initTitle() = "自定义View属性绑定演示"

    val isPassword = MutableLiveData(false)
    val size = MutableLiveData(LARGE)

    fun onChangeStyle(view: View) {
        if (isPassword.value == true) {
            isPassword.value = false
            size.value = LARGE
        } else {
            isPassword.value = true
            size.value = SMALL
        }
    }
}