/*
 * Copyright (C) 2019 xuexiangjys(xuexiangjys@163.com)
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

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.xuexiang.xui.utils.DensityUtils

/**
 * 最后一个列表项的分割线不画，可自定义分割线的样式
 *
 * @author xuexiang
 * @since 2019-08-11 18:54
 */
class DividerItemDecoration(context: Context, val orientation: Int) : ItemDecoration() {
    /**
     * 画笔
     */
    private var mPaint: Paint? = null

    var divider: Drawable? = null
        set(value) {
            if (field != value) {
                field = value
                value?.let {
                    dividerHeight = it.intrinsicHeight
                }
            }
        }

    /**
     * 分割线高度，默认为1dp
     */
    var dividerHeight: Int = 0

    /**
     * 分割线颜色，默认为灰色
     */
    @ColorInt
    var dividerColor: Int = 0
        set(value) {
            if (field != value) {
                field = value
                mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
                    .apply {
                        color = value
                        style = Paint.Style.FILL
                    }
            }
        }

    /**
     * 额外的padding
     */
    var extraPadding: Int = 0

    /**
     * 默认分割线：高度为1dp，颜色为灰色
     */
    init {
        require(!(orientation != LinearLayoutManager.VERTICAL && orientation != LinearLayoutManager.HORIZONTAL)) { "请输入正确的参数！" }
        val a = context.obtainStyledAttributes(intArrayOf(android.R.attr.listDivider))
        divider = a.getDrawable(0)
        a.recycle()
        dividerHeight = divider?.intrinsicHeight ?: DensityUtils.dp2px(context, 1F)
    }

    /**
     * 获取分割线尺寸
     */
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        if (orientation == LinearLayoutManager.VERTICAL) {
            outRect[0, 0, 0] = dividerHeight
        } else {
            outRect[0, 0, dividerHeight] = 0
        }
    }

    /**
     * 绘制分割线
     */
    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(canvas, parent, state)
        if (orientation == LinearLayoutManager.VERTICAL) {
            drawVertical(canvas, parent)
        } else {
            drawHorizontal(canvas, parent)
        }
    }

    /**
     * 绘制纵向列表时的分隔线  这时分隔线是横着的
     * 每次 left相同，top根据child变化，right相同，bottom也变化
     *
     * @param canvas
     * @param parent
     */
    private fun drawVertical(canvas: Canvas, parent: RecyclerView) {
        val left = parent.paddingLeft + extraPadding
        val right = parent.measuredWidth - parent.paddingRight - extraPadding
        val childSize = parent.childCount
        for (i in 0 until childSize - 1) {
            val child = parent.getChildAt(i)
            val layoutParams = child.layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + layoutParams.bottomMargin
            val bottom = top + dividerHeight

            drawDivider(canvas, left, top, right, bottom)
        }
    }

    /**
     * 绘制横向列表时的分隔线  这时分隔线是竖着的
     * l、r 变化； t、b 不变
     *
     * @param canvas
     * @param parent
     */
    private fun drawHorizontal(canvas: Canvas, parent: RecyclerView) {
        val top = parent.paddingTop + extraPadding
        val bottom = parent.measuredHeight - parent.paddingBottom - extraPadding
        val childSize = parent.childCount
        for (i in 0 until childSize - 1) {
            val child = parent.getChildAt(i)
            val layoutParams = child.layoutParams as RecyclerView.LayoutParams
            val left = child.right + layoutParams.rightMargin
            val right = left + dividerHeight
            drawDivider(canvas, left, top, right, bottom)
        }
    }

    private fun drawDivider(
        canvas: Canvas,
        left: Int,
        top: Int,
        right: Int,
        bottom: Int
    ) {
        mPaint?.let {
            canvas.drawRect(
                left.toFloat(),
                top.toFloat(),
                right.toFloat(),
                bottom.toFloat(),
                it
            )
        } ?: divider?.run {
            setBounds(left, top, right, bottom)
            draw(canvas)
        }
    }
}