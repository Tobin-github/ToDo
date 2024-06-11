@file:Suppress("unused")

package top.tobin.common.viewbinding

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

inline fun <reified VB : ViewBinding> Fragment.bindingView() =
    FragmentBindingProperty(VB::class.java)

inline fun <reified VB : ViewBinding> Fragment.binding() =
    FragmentInflateBindingProperty(VB::class.java)

class FragmentBindingProperty<VB : ViewBinding>(private val clazz: Class<VB>) :
    ReadOnlyProperty<Fragment, VB> {

    override fun getValue(thisRef: Fragment, property: KProperty<*>): VB =
        requireNotNull(thisRef.view) {
            "The constructor missing layout id or the property of ${property.name} has been destroyed."
        }.getBinding(clazz)
}

class FragmentInflateBindingProperty<VB : ViewBinding>(private val clazz: Class<VB>) :
    ReadOnlyProperty<Fragment, VB> {
    private var binding: VB? = null
    private val handler by lazy { Handler(Looper.getMainLooper()) }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): VB {
        if (binding == null) {
            try {
                @Suppress("UNCHECKED_CAST")
                binding = (clazz.getMethod("inflate", LayoutInflater::class.java)
                    .invoke(null, thisRef.layoutInflater) as VB)
            } catch (e: IllegalStateException) {
                throw IllegalStateException("The property of ${property.name} has been destroyed.")
            }
            thisRef.viewLifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
                override fun onDestroy(owner: LifecycleOwner) {
                    handler.post { binding = null }
                }
            })
        }
        return binding!!
    }
}

