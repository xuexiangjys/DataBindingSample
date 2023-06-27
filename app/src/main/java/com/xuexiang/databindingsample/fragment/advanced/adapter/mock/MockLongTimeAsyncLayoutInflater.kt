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
package com.xuexiang.databindingsample.fragment.advanced.adapter.mock

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Message
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.UiThread
import androidx.core.util.Pools.SynchronizedPool
import java.util.concurrent.ArrayBlockingQueue

/**
 * 模拟耗时的布局异步加载，copy from AsyncLayoutInflater
 *
 * @author xuexiang
 * @since 6/21/23 11:06 PM
 */
class MockLongTimeAsyncLayoutInflater(context: Context) {

    private var mInflater = BasicInflater(context)
    private var mInflateThread = InflateThread.get()

    private val mHandlerCallback = Handler.Callback { msg ->
        val request = msg.obj as InflateRequest
        if (request.view == null) {
            request.view = mInflater.inflate(
                request.resid, request.parent, false
            )
        }
        request.callback?.invoke(
            request.view!!, request.resid, request.parent
        )
        mInflateThread.releaseRequest(request)
        true
    }

    var mHandler = Handler(mHandlerCallback)

    @UiThread
    fun inflate(@LayoutRes resId: Int, parent: ViewGroup?, callback: OnInflateFinishedListener?) {
        if (callback == null) {
            throw NullPointerException("callback argument may not be null!")
        }
        val request = mInflateThread.obtainRequest()
        request.inflater = this
        request.resid = resId
        request.parent = parent
        request.callback = callback
        mInflateThread.enqueue(request)
    }

    class InflateRequest {
        var inflater: MockLongTimeAsyncLayoutInflater? = null
        var parent: ViewGroup? = null
        var resid = 0
        var view: View? = null
        var callback: OnInflateFinishedListener? = null
    }

    class BasicInflater constructor(context: Context?) : LayoutInflater(context) {
        override fun cloneInContext(newContext: Context): LayoutInflater {
            return BasicInflater(newContext)
        }

        @Throws(ClassNotFoundException::class)
        override fun onCreateView(name: String, attrs: AttributeSet): View {
            for (prefix in sClassPrefixList) {
                try {
                    val view = createView(name, prefix, attrs)
                    if (view != null) {
                        return view
                    }
                } catch (e: ClassNotFoundException) {
                    // In this case we want to let the base class take a crack
                    // at it.
                }
            }
            return super.onCreateView(name, attrs)
        }

        companion object {
            private val sClassPrefixList = arrayOf(
                "android.widget.",
                "android.webkit.",
                "android.app."
            )
        }
    }

    class InflateThread : Thread() {
        private val mQueue = ArrayBlockingQueue<InflateRequest>(10)
        private val mRequestPool = SynchronizedPool<InflateRequest>(10)

        @SuppressLint("LongLogTag")
        private fun runInner() {
            val request = try {
                mQueue.take()
            } catch (ex: InterruptedException) {
                // Odd, just continue
                Log.w(TAG, ex)
                return
            }
            try {
                // 模拟耗时加载
                request.view = InflateUtils.mockLongTimeLoad(
                    request.inflater!!.mInflater,
                    request.parent, request.resid
                )
            } catch (ex: RuntimeException) {
                // Probably a Looper failure, retry on the UI thread
                Log.w(
                    TAG, "Failed to inflate resource in the background! Retrying on the UI"
                            + " thread", ex
                )
            }
            Message.obtain(request.inflater!!.mHandler, 0, request)
                .sendToTarget()
        }

        override fun run() {
            while (true) {
                runInner()
            }
        }

        fun obtainRequest(): InflateRequest {
            var obj = mRequestPool.acquire()
            if (obj == null) {
                obj = InflateRequest()
            }
            return obj
        }

        fun releaseRequest(obj: InflateRequest) {
            obj.callback = null
            obj.inflater = null
            obj.parent = null
            obj.resid = 0
            obj.view = null
            mRequestPool.release(obj)
        }

        fun enqueue(request: InflateRequest) {
            try {
                mQueue.put(request)
            } catch (e: InterruptedException) {
                throw RuntimeException(
                    "Failed to enqueue async inflate request", e
                )
            }
        }

        private object InstanceHolder {
            val sInstance = InflateThread().apply { start() }
        }

        companion object {
            fun get() = InstanceHolder.sInstance
        }
    }

    companion object {
        private const val TAG = "MockLongTimeAsyncLayoutInflater"
    }
}

typealias OnInflateFinishedListener = (View, Int, ViewGroup?) -> Unit
