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
import android.content.ContextWrapper
import android.content.DialogInterface
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.xuexiang.databindingsample.R
import com.xuexiang.databindingsample.databinding.LayoutSelectValueBinding

/**
 * 点击弹出自定义可选择弹窗
 *
 * @author xuexiang
 * @since 2023/6/9 00:34
 */
class SelectValueLayout constructor(
    context: Context,
    attrs: AttributeSet?,
) : ConstraintLayout(context, attrs) {

    private var TAG = this.javaClass.simpleName

    private var mBinding: LayoutSelectValueBinding

    private var mDialog: SelectDialog? = null

    var title: CharSequence?
        get() {
            return mBinding.tvTitle.text
        }
        set(value) {
            mBinding.tvTitle.text = value
        }

    var content: CharSequence?
        get() {
            return mBinding.tvContent.text
        }
        set(value) {
            mBinding.tvContent.text = value
            if (!value.isNullOrEmpty()) {
                dismissDialog()
            }
        }

    var state: Any? = null

    var layoutId: Int = View.NO_ID

    init {
        mBinding = LayoutSelectValueBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )
        val attributes =
            context.theme.obtainStyledAttributes(attrs, R.styleable.SelectValueLayout, 0, 0)
        try {
            title = attributes.getString(R.styleable.SelectValueLayout_svl_title)
        } finally {
            attributes.recycle()
        }
        setOnClickListener {
            showDialog(context)
        }
    }

    private fun showDialog(context: Context) {
        getFragmentManager(context)?.run {
            if (mDialog == null) {
                mDialog = SelectDialog(layoutId, state)
            }
            mDialog?.let { dialog ->
                dialog.onDismissListener = DialogInterface.OnDismissListener {
                    mDialog = null
                }
                dialog.show(this, TAG)
            }
        }
    }

    private fun getFragmentManager(context: Context): FragmentManager? {
        if (context is FragmentActivity) {
            return context.supportFragmentManager
        }
        if (context is ContextWrapper) {
            return getFragmentManager(context.baseContext)
        }
        return null
    }

    override fun onDetachedFromWindow() {
        dismissDialog()
        super.onDetachedFromWindow()
    }

    private fun dismissDialog() {
        mDialog?.dismissAllowingStateLoss()
    }

}