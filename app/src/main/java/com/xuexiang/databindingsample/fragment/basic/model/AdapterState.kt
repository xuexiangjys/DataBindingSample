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

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingConversion
import androidx.lifecycle.MutableLiveData
import com.xuexiang.databindingsample.core.databinding.DataBindingState

/**
 * 控件自定义属性绑定演示
 * 使用@BindingAdapter进行绑定
 * 进行DataBinding的使用，一定要使用 "@{}" 进行赋值。
 *
 *
 * @author xuexiang
 * @since 2023/4/23 00:20
 */
class AdapterState : DataBindingState() {

    override fun initTitle() = "控件自定义属性绑定演示"

    val user = MutableLiveData(User("小明"))

}

data class User(
    val name: String,
    val gender: String? = "男",
    val age: Int = 10,
    val phone: String? = "13124765438",
    val address: String? = null
)

// 本来User中的age是int，和这里的age定义的string不匹配，编译器会报错，但是因为有个下面的 @BindingConversion 进行自动类型转化，所以就不会报错
@BindingAdapter(value = ["name", "age"], requireAll = true)
fun TextView.setUserInfo(name: String, age: String) {
    text = "${name}今年${age}岁"
}

// 属性值自动进行类型转换
@BindingConversion
fun int2string(integer: Int) = integer.toString()


@BindingAdapter("android:userInfo")
fun TextView.setUserInfo(user: User) {
    text = "姓名: ${user.name}, 性别: ${user.gender}, 年龄: ${user.age}岁"
}
