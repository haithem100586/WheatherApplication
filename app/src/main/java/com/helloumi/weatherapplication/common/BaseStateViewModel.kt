package com.helloumi.weatherapplication.common

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel

/**
 *  Base class to be extended by view-models.
 *
 *  It is designed around the concept of an immutable state, so it exposes only *one* [LiveData]. This
 *  allows better testability, as a test can then be structured as follows:
 *
 *  - set an initial state
 *  - apply an action
 *  - check the resulting state
 *
 * Also comprises a mechanism to notify transient events to the fragments via a stateless [LiveData].
 */
abstract class BaseStateViewModel<S : BaseState, E : BaseEvent>(initialState: S) : ViewModel(), LifecycleOwner {

    private val lifecycleOwner = ViewModelLifecycleOwner()

    // FIXME - this is broken, what we should have is:
    //  - ViewModel.init: create livedatas, but do not observe them
    //  - ViewModel.onActive: observe livedatas
    //  So state onActive / onInactive should trigger CreateLifecycle+CREATE+START / STOP+DESTROY
    private val _state = object : MutableLiveData<S>(initialState) {
        override fun onActive() {
            this@BaseStateViewModel.onActive()
            lifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_START)
        }

        override fun onInactive() {
            lifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
            this@BaseStateViewModel.onInactive()
        }
    }
    private val _events = SingleLiveEvent<E>()

    /**
     * The ViewModel State LiveData.
     */
    val state: LiveData<S>
        get() = _state

    /**
     * The ViewModel Event LiveData.
     */
    val event: LiveData<E>
        get() = _events

    /**
     * The ViewModel current state.
     */
    val currentState: S
        get() = _state.value!!

    init {
        lifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
    }

    override fun onCleared() {
        lifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        super.onCleared()
    }

    override fun getLifecycle() = lifecycleOwner.lifecycle

    /**
     * Binds ViewModel to the given lifecycle by adding a [state] observation.
     *
     * @param lifecycleOwner the lifecycle owner to bind.
     */
    fun bind(lifecycleOwner: LifecycleOwner) {
        // do not use lambda to prevent conversion as singleton causing a crash when activity is recreated.
        @Suppress("ObjectLiteralToLambda")
        val observer = object : Observer<S> {
            override fun onChanged(it: S) {
                /* do nothing */
            }
        }
        state.observe(lifecycleOwner, observer)
    }

    /**
     * Updates the state to a new one.
     *
     * Sample usage:
     * ```
     * updateState { state -> state.copy(droneName = drone?.name) }
     * ```
     *
     * @param handler method returning the target state, given the source state.
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    fun updateState(handler: (S) -> (S)) {
        val currentState = state.value!!
        val newValue = handler.invoke(currentState)
        _state.value = newValue
    }

    /**
     * Notifies a given transient event.
     *
     * @param event event to notify.
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    fun notifyEvent(event: E) {
        _events.value = event
    }

    /**
     * Callback when this [ViewModel] is in use by its fragment.
     */
    open fun onActive() {}

    /**
     * Callback when this [ViewModel] isn't in use by its fragment anymore.
     */
    open fun onInactive() {}

    /**
     * The [LifecycleOwner] reflecting BaseViewModel's life:
     * - created on view-model init
     * - started when state gets observed
     * - stopped when state is not observed anymore
     * - destroyed on [ViewModel.onCleared]
     *
     * Can be used to bind a [LiveData] observation only when the state is observed.
     */
    inner class ViewModelLifecycleOwner : LifecycleOwner {
        private val lifecycleRegistry by lazy { LifecycleRegistry(this@BaseStateViewModel) }

        override fun getLifecycle() = lifecycleRegistry

        fun handleLifecycleEvent(event: Lifecycle.Event) = lifecycleRegistry.handleLifecycleEvent(event)
    }
}

/**
 * Base class to be extended by view-model states.
 */
interface BaseState

/**
 * Base class to be extended by view-model events.
 */
interface BaseEvent
