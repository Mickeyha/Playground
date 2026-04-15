package com.example.playground.libs

import android.view.View
import androidx.viewbinding.ViewBinding
import com.airbnb.epoxy.EpoxyHolder
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

abstract class KotlinEpoxyHolder : EpoxyHolder() {
    private lateinit var _view: View

    override fun bindView(itemView: View) {
        _view = itemView
    }

    protected fun <V : View> bind(id: Int): ReadOnlyProperty<KotlinEpoxyHolder, V> =
        Lazy { holder, prop ->
            holder._view.findViewById(id) as V?
                ?: error("View ID $id for '${prop.name}' not found.")
        }

    protected fun <B : ViewBinding> bind(binder: (View) -> B): ReadOnlyProperty<KotlinEpoxyHolder, B> =
        Lazy { holder, _ -> binder(holder._view) }

    private class Lazy<V>(
        private val initializer: (KotlinEpoxyHolder, KProperty<*>) -> V
    ) : ReadOnlyProperty<KotlinEpoxyHolder, V> {
        private var value: Any? = UNSET

        override fun getValue(thisRef: KotlinEpoxyHolder, property: KProperty<*>): V {
            if (value === UNSET) value = initializer(thisRef, property)
            @Suppress("UNCHECKED_CAST")
            return value as V
        }

        companion object { private val UNSET = Any() }
    }
}
