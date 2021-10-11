package com.damcoassignment.utils

import androidx.lifecycle.Observer

class EventObserver<T>(private val onEventUnhandledContent: (T) -> Unit) : Observer<Event<T>> {
    override fun onChanged(t: Event<T>?) {
        t?.getContentIfNotHandle()?.let { value ->
            onEventUnhandledContent(value)
        }
    }
}