package com.android.weatherapplication.common

/**
 * Screen view-model class extends [BaseStateViewModel] class.
 *
 * It extends [BaseStateViewModel] to add screen related state properties in [ScreenBaseState].
 *
 * @param initialState initial state of the viewmodel (mainly for testing purpose).
 */
abstract class ScreenViewModel<S : ScreenBaseState, E : BaseEvent>(
        initialState: S
) : BaseStateViewModel<S, E>(initialState)

/**
 * Base state including screen related properties to be extended by view-model states.
 */
interface ScreenBaseState : BaseState {

    /**
     * Whether or not we should close the screen.
     */
    val shouldClose: Boolean
}
