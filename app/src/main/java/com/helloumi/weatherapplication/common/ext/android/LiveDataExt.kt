@file:Suppress("TooManyFunctions")

package com.helloumi.weatherapplication.common.ext.android

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

// Some basic stream-style operations on live data, as kotlin extensions.

/**
 * Observes a live data's first value, then unsubscribes.
 *
 * @param owner lifecycle to bind the observation to, useful until no value has been fired.
 * @param observer the observer.
 */
@Suppress("NOTHING_TO_INLINE")
inline fun <T> LiveData<T>.observeOnce(owner: LifecycleOwner, observer: Observer<T> = Observer { }) =
    observe(owner, object : Observer<T> {
        override fun onChanged(t: T) {
            removeObserver(this)
            observer.onChanged(t)
        }
    })

/**
 * Observes a live data's first value without observer.
 *
 * @param owner lifecycle to bind the observation to, useful until no value has been fired.
 */
@Suppress("NOTHING_TO_INLINE")
inline fun <T> LiveData<T>.observeOnce(owner: LifecycleOwner) = observeOnce(owner) {}