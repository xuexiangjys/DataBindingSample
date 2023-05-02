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

/**
 *
 * 常用运算符
 * 算术 + - / * %
 * 字符串合并 +
 * 逻辑 && ||
 * 二元 & | ^
 * 一元 + - ! ~
 * 移位 >> >>> <<
 * 比较 == > < >= <=
 * 三元 ?:
 * Array 访问 []
 *
 * 常用转义字符
 * 空格	&nbsp; &#160;
 * <小于号 &lt; &#60;
 * >大于号 &gt; &#62;
 * &与号	&amp; &#38;
 * "引号	&quot; &#34;
 * ‘ 撇号 &apos;	&#39;
 * ×乘号	&times;	&#215;
 * ÷除号	&divide; &#247;
 *
 * 资源使用
 * @string @color @drawable @dimen @array
 *
 * 集合
 * <import type="java.util.List"/>
 * <import type="android.util.SparseArray"/>
 * <import type="java.util.Map"/>
 * <variable name="list" type="List&lt;String>"/>
 * <variable name="sparse" type="SparseArray&lt;String>"/>
 * <variable name="map" type="Map&lt;String, String>"/>
 *
 * 引用方法
 * 注意这里指的是静态方法。使用<import type="xxx"/>导入类
 *
 *
 */
class ExpressionState : DataBindingState() {

    override fun initTitle() = "@{}中表达式使用"

    val user = MutableLiveData(User("小明"))


}


