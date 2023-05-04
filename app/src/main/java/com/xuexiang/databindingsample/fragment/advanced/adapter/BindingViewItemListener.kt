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

package com.xuexiang.databindingsample.fragment.advanced.adapter

import android.view.View

/**
 * 列表条目点击监听
 */
interface OnItemClickListener<T> {
    /**
     * 条目点击
     *
     * @param itemView 条目
     * @param item     数据
     * @param position 索引
     */
    fun onItemClick(itemView: View?, item: T?, position: Int)
}

/**
 * 列表条目长按监听
 */
interface OnItemLongClickListener<T> {
    /**
     * 条目长按
     *
     * @param itemView 条目
     * @param item     数据
     * @param position 索引
     */
    fun onItemLongClick(itemView: View?, item: T?, position: Int) : Boolean = true
}