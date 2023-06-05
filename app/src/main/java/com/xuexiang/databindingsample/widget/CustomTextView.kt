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
import androidx.appcompat.widget.AppCompatTextView
import com.xuexiang.databindingsample.R
import com.xuexiang.xui.widget.edittext.AsteriskPasswordTransformationMethod
import com.xuexiang.xutil.display.Colors

/**
 * 自定义TextView
 *
 * 这里的自定义view，控件的自定义属性名和成员变量名相同。
 * 如果使用时使用 @{} 来设置，例如：app:password="@{state.isPassword}"，这里就是直接使用的DataBinding进行设置。
 * 否则，例如：app:password="false"，就是使用自定义属性进行设置。
 *
 * @author xuexiang
 * @since 2023/2/22 01:09
 */
class CustomTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.CustomTextStyle,
) : AppCompatTextView(context, attrs, defStyleAttr) {

    var size = MEDIUM
        set(value) {
            if (field != value) {
                field = value
                textSize = when (value) {
                    SMALL -> 12F
                    MEDIUM -> 16F
                    LARGE -> 21F
                    else -> 16F
                }
                setTextColor(
                    when (value) {
                        SMALL -> Colors.GRAY
                        MEDIUM -> Colors.BLACK
                        LARGE -> Colors.BLUE
                        else -> Colors.BLACK
                    }
                )
            }
        }

    /**
     * 注意boolean值，属性名不能是is开头，否则绑定时会找不到set方法。
     */
    var password = false
        set(value) {
            if (field != value) {
                field = value
                transformationMethod =
                    if (value) AsteriskPasswordTransformationMethod.getInstance() else null
            }
        }

    init {
        val array = context.obtainStyledAttributes(
            attrs,
            R.styleable.CustomTextView,
            defStyleAttr,
            0
        )
        size = array.getInteger(R.styleable.CustomTextView_size, size)
        password = array.getBoolean(R.styleable.CustomTextView_password, password)
        array.recycle()
    }


    companion object {
        const val SMALL = 0
        const val MEDIUM = 1
        const val LARGE = 2
    }

}