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

package top.tobin.common.viewbinding

import android.view.View
import androidx.viewbinding.ViewBinding
import top.tobin.common.R

inline fun <reified VB : ViewBinding> View.getBinding() = getBinding(VB::class.java)

// 反射
@Suppress("UNCHECKED_CAST")
fun <VB : ViewBinding> View.getBinding(clazz: Class<VB>) =
    getTag(R.id.tag_view_binding) as? VB ?: (clazz.getMethod("bind", View::class.java)
        .invoke(null, this) as VB)
        .also { setTag(R.id.tag_view_binding, it) }

// 非反射
@Suppress("UNCHECKED_CAST")
fun <VB : ViewBinding> View.getBinding(bind: (View) -> VB): VB =
    getTag(R.id.tag_view_binding) as? VB ?: bind(this).also { setTag(R.id.tag_view_binding, it) }
