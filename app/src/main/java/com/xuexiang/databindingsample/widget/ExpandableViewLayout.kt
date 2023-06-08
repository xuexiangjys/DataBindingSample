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

package com.xuexiang.databindingsample.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import com.xuexiang.databindingsample.R
import com.xuexiang.databindingsample.databinding.LayoutExpandableViewBinding

/**
 * 可伸缩的布局，可控DataBinding使用
 *
 * @author xuexiang
 * @since 2023/6/9 00:06
 */
class ExpandableViewLayout constructor(
    context: Context,
    attrs: AttributeSet?,
) : LinearLayout(context, attrs) {

    private var binding: LayoutExpandableViewBinding

    private var childBinding: ViewDataBinding? = null

    private var isExpanded: Boolean = false
        set(value) {
            field = value
            binding.childContainer.isVisible = value
        }

    var title: CharSequence?
        get() {
            return binding.tvTitle.text
        }
        set(value) {
            binding.tvTitle.text = value
        }

    var showArrow: Boolean = true
        set(value) {
            if (field != value) {
                field = value
                binding.ivArrow.isVisible = value
            }
        }

    var state: Any? = null
        set(value) {
            if (field != value) {
                field = value
                childBinding?.setVariable(BR.state, state)
            }
        }

    var layoutId: Int = View.NO_ID
        set(value) {
            if (field != value) {
                field = value
                if (value != View.NO_ID) {
                    childBinding = DataBindingUtil.inflate<ViewDataBinding>(
                        LayoutInflater.from(context),
                        value,
                        binding.childContainer,
                        true
                    )?.apply {
                        setVariable(BR.state, state)
                    }
                }
            }
        }

    var onExpandListener: OnExpandStateListener? = null

    init {
        binding = LayoutExpandableViewBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )
        val attributes =
            context.theme.obtainStyledAttributes(attrs, R.styleable.ExpandableViewLayout, 0, 0)
        try {
            title = attributes.getString(R.styleable.ExpandableViewLayout_evl_title)
            showArrow = attributes.getBoolean(R.styleable.ExpandableViewLayout_evl_show_arrow, true)
        } finally {
            attributes.recycle()
        }
        setOnClickListener {
            isExpanded = !isExpanded
            if (showArrow) {
                binding.ivArrow.animate()?.rotation(
                    if (isExpanded) {
                        90f
                    } else {
                        0f
                    }
                )?.start()
            }
            onExpandListener?.onExpandChanged(isExpanded)
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        childBinding?.unbind()
        childBinding = null
    }
}

interface OnExpandStateListener {
    fun onExpandChanged(isExpanded: Boolean)
}
