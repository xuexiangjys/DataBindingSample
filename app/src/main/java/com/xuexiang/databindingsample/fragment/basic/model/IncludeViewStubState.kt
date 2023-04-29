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

/**
 * include和ViewStub
 *
 * 在主布局文件中将相应的变量传递给 include 布局，需使用自定义的 bind 命名空间将变量传递给 （include/ViewStub）， 从而使两个布局文件之间共享同一个变量
 *
 * 例如在include中定义的变量id是：<variable name="user" type="...User"/>, 那么就使用 app:user="@{state.user}" 来绑定数据，与variable定义的name保持一致。
 *
 */
class IncludeViewStubState : DataBindingState() {

    override fun initTitle() = "include和viewStub使用演示"

    val user = MutableLiveData(User("张三"))

    var onClickLoadViewStub: View.OnClickListener? = null

}

