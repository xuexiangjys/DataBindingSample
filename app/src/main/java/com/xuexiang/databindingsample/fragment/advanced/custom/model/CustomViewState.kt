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

package com.xuexiang.databindingsample.fragment.advanced.custom.model

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.xuexiang.databindingsample.core.databinding.DataBindingPageState

/**
 * 自定义View的state使用演示
 *
 * @author xuexiang
 * @since 2023/6/7 01:41
 */
class CustomViewState(application: Application) : DataBindingPageState(application) {

    override fun initTitle() = "自定义View的state使用演示"

}


