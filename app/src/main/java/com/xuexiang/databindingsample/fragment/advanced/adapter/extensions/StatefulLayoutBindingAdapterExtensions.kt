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

package com.xuexiang.databindingsample.fragment.advanced.adapter.extensions

import android.view.View
import androidx.databinding.BindingAdapter
import com.xuexiang.xui.widget.statelayout.StatefulLayout

/**
 * StatefulLayout的绑定拓展方法
 *
 * @author xuexiang
 * @since 2023/6/2 00:01
 */

@BindingAdapter(
    value = ["status", "onError", "onNoNet"],
    requireAll = false
)
fun StatefulLayout.refreshStatus(
    status: Status?,
    onError: View.OnClickListener?,
    onNoNet: View.OnClickListener?
) {
    when (status) {
        Status.SUCCESS -> showContent()
        Status.EMPTY -> showEmpty()
        Status.ERROR -> showError(onError)
        Status.NO_NET -> showOffline(onNoNet)
        else -> {}
    }
}

enum class Status {
    DEFAULT,
    SUCCESS,
    EMPTY,
    ERROR,
    NO_NET
}