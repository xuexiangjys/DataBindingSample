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

package com.xuexiang.databindingsample.fragment.advanced.preload.model

import java.util.concurrent.atomic.AtomicLong

/**
 * 新闻信息
 *
 * @author xuexiang
 * @since 2019/4/7 下午12:07
 */
class NewInfo : Cloneable {
    var iD: Long = 0

    /**
     * 用户名
     */
    var userName = "xuexiangjys"

    /**
     * 标签
     */
    var tag: String? = null

    /**
     * 标题
     */
    var title: String? = null

    /**
     * 摘要
     */
    var summary: String? = null

    /**
     * 图片
     */
    var imageUrl: String? = null

    /**
     * 点赞数
     */
    var praise = 0

    /**
     * 评论数
     */
    var comment = 0

    /**
     * 阅读量
     */
    var read = 0

    /**
     * 新闻的详情地址
     */
    var detailUrl: String? = null

    constructor() {}

    constructor(
        userName: String,
        tag: String?,
        title: String?,
        summary: String?,
        imageUrl: String?,
        praise: Int,
        comment: Int,
        read: Int,
        detailUrl: String?
    ) {
        this.userName = userName
        this.tag = tag
        this.title = title
        this.summary = summary
        this.imageUrl = imageUrl
        this.praise = praise
        this.comment = comment
        this.read = read
        this.detailUrl = detailUrl
    }

    constructor(
        tag: String?,
        title: String?,
        summary: String?,
        imageUrl: String?,
        detailUrl: String?
    ) {
        this.tag = tag
        this.title = title
        this.summary = summary
        this.imageUrl = imageUrl
        this.detailUrl = detailUrl
    }

    constructor(tag: String?, title: String?) {
        iD = sAtomicLong.incrementAndGet()
        this.tag = tag
        this.title = title
        praise = (Math.random() * 100 + 5).toInt()
        comment = (Math.random() * 50 + 5).toInt()
        read = (Math.random() * 500 + 50).toInt()
    }

    fun resetContent(): NewInfo {
        praise = (Math.random() * 100 + 5).toInt()
        comment = (Math.random() * 50 + 5).toInt()
        read = (Math.random() * 500 + 50).toInt()
        return this
    }

    override fun toString(): String {
        return "NewInfo{" +
                "UserName='" + userName + '\'' +
                ", Tag='" + tag + '\'' +
                ", Title='" + title + '\'' +
                ", Summary='" + summary + '\'' +
                ", ImageUrl='" + imageUrl + '\'' +
                ", Praise=" + praise +
                ", Comment=" + comment +
                ", Read=" + read +
                ", DetailUrl='" + detailUrl + '\'' +
                '}'
    }

    public override fun clone(): NewInfo {
        try {
            return super.clone() as NewInfo
        } catch (e: CloneNotSupportedException) {
            e.printStackTrace()
        }
        return NewInfo()
    }

    companion object {
        private val sAtomicLong = AtomicLong()
    }
}