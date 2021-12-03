package com.android.weatherapplication.common

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.ProcessLifecycleOwner

/**
 * [LifecycleOwner] that represents a composite of all created activities, in a similar way to
 * [ProcessLifecycleOwner], also ignoring transient destroy/create sequences occurring during
 * configuration changes.
 *
 * It only provides the following events:
 * - [Lifecycle.Event.ON_CREATE]: may fire more than once, unlike [ProcessLifecycleOwner]
 * - [Lifecycle.Event.ON_START]
 * - [Lifecycle.Event.ON_STOP]
 * - [Lifecycle.Event.ON_DESTROY]: may fire more than once, unlike [ProcessLifecycleOwner]
 */
class DestructionLifeCycleOwner : LifecycleOwner {

    private val registry = LifecycleRegistry(this)
    private val handler = Handler(Looper.getMainLooper())
    private var destroySent = true

    private val callbacks = object : Application.ActivityLifecycleCallbacks {
        private var counter = 0

        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            // if an activity had been destroyed shortly before, don't consider it a destroy event
            handler.removeCallbacks(delayedDestroyRunnable)

            // first activity created? fire create event
            counter++
            if (counter == 1 && destroySent) {
                registry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
                registry.handleLifecycleEvent(Lifecycle.Event.ON_START)
                destroySent = false
            }
        }

        override fun onActivityDestroyed(activity: Activity) {
            counter--

            // last activity getting destroyed? if none gets created shortly after, consider it a
            // destroy event
            if (counter < 1) {
                handler.postDelayed(delayedDestroyRunnable, TIMEOUT_MS)
            }
        }

        override fun onActivityStarted(activity: Activity) {
            // Do nothing
        }

        override fun onActivityResumed(activity: Activity) {
            // Do nothing
        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            // Do nothing
        }

        override fun onActivityPaused(activity: Activity) {
            // Do nothing
        }

        override fun onActivityStopped(activity: Activity) {
            // Do nothing
        }
    }

    // last activity really destroyed? fire destroy event
    private val delayedDestroyRunnable = Runnable {
        registry.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
        registry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        destroySent = true
    }

    override fun getLifecycle(): Lifecycle {
        return registry
    }

    private fun attach(context: Context) {
        registry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
        registry.handleLifecycleEvent(Lifecycle.Event.ON_START)
        val app = context.applicationContext as Application
        app.registerActivityLifecycleCallbacks(callbacks)
    }

    companion object {
        /**  Delay after onDestroy before we consider the last activity really destroyed. */
        private const val TIMEOUT_MS = 700L

        private val instance = DestructionLifeCycleOwner()

        fun init(context: Context) {
            instance.attach(context)
        }

        fun get(): LifecycleOwner = instance
    }
}
