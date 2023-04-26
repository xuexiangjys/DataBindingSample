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
import java.util.concurrent.atomic.AtomicInteger

/**
 * 点击状态，演示点击事件绑定
 * 1.无参响应函数： android:onClick="@{() -> state.onIncrement()}"
 * 2.接口变量响应函数： android:onClick="@{state.onClickDecrement}"
 * 3.有参响应函数：  android:onClick="@{(view) -> state.onReset(view)}"
 *                android:onClick="@{state::onReset}"
 *                两者等价
 *
 * @author xuexiang
 * @since 2023/4/23 00:20
 */
class ClickState : DataBindingState() {

    override fun initTitle() = "点击事件绑定演示"

    private val count = AtomicInteger()

    fun onIncrement() {
        title.value = "Data:${count.incrementAndGet()}"
    }

    val onClickDecrement = View.OnClickListener {
        title.value = "Data:${count.decrementAndGet()}"
    }

    fun onReset(view: View) {
        count.set(0)
        title.value = "Data:${count.get()}, view:${view.javaClass.simpleName}"
    }


}