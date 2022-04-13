package com.helloumi.weatherapplication.common

import androidx.viewbinding.ViewBinding

/**
 * Base class to be extended by fragments.
 *
 * Provides direct access to the [MainActivity]'s ViewModel.
 */
abstract class BaseStateFragment<B : ViewBinding> : BaseFragment<B>() {

    abstract val viewModel: BaseStateViewModel<out BaseState, out BaseEvent>
}
