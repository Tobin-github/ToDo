/*
 * Copyright (c) 2020. Dylan Cai
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:Suppress("unused")

package top.tobin.common.viewbinding

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import kotlin.LazyThreadSafetyMode.NONE

inline fun <reified VB : ViewBinding> Activity.popupWindow(
    width: Int = ViewGroup.LayoutParams.WRAP_CONTENT,
    height: Int = ViewGroup.LayoutParams.WRAP_CONTENT,
    focusable: Boolean = false,
    crossinline block: VB.() -> Unit
) = lazy(NONE) {
    PopupWindow(inflateBinding<VB>(layoutInflater).apply(block).root, width, height, focusable)
}

inline fun <reified VB : ViewBinding> Fragment.popupWindow(
    width: Int = ViewGroup.LayoutParams.WRAP_CONTENT,
    height: Int = ViewGroup.LayoutParams.WRAP_CONTENT,
    focusable: Boolean = false,
    crossinline block: VB.() -> Unit
) = lazy(NONE) {
    PopupWindow(inflateBinding<VB>(layoutInflater).apply(block).root, width, height, focusable)
}


inline fun <reified VB : ViewBinding> View.popupWindow(
    width: Int = ViewGroup.LayoutParams.WRAP_CONTENT,
    height: Int = ViewGroup.LayoutParams.WRAP_CONTENT,
    focusable: Boolean = false,
    crossinline block: VB.() -> Unit
) = lazy(NONE) {
    PopupWindow(
        inflateBinding<VB>(LayoutInflater.from(context)).apply(block).root,
        width,
        height,
        focusable
    )
}