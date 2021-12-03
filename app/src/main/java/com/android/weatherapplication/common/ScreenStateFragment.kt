package com.android.weatherapplication.common

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.annotation.CallSuper
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.map
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.android.weatherapplication.common.ScreenFragment.Companion.ScreenOrientation
import com.snakydesign.livedataextensions.filter

/**
 * Base class to be extended by first level fragments (ie. screen fragments) holding a state.
 */
abstract class ScreenStateFragment<B : ViewBinding> : BaseStateFragment<B>() {

    /**
     * Override to define the fragment's activity orientation.
     */
    @get:ScreenOrientation
    protected abstract val screenOrientation: Int

    /**
     * Callback used to handle the onBackPressed activity event.
     */
    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            onBackPressed()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBackPressedCallback.isEnabled = true
        initGlobalObservations()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, onBackPressedCallback)
    }

    override fun onDestroyView() {
        onBackPressedCallback.isEnabled = false
        super.onDestroyView()
    }

    @CallSuper
    override fun initViews() {
        // Request activity orientation if needed
        screenOrientation
            .takeIf { it != ScreenFragment.BEHIND }
            .takeIf { it != activity?.requestedOrientation }
            ?.let { requestedOrientation ->
                activity?.requestedOrientation = requestedOrientation
            }
    }

    /**
     * Override to handle the onBackPressed event.
     * Default behavior attempts to pop the back stack, finishes the activity otherwise.
     */
    open fun onBackPressed() {
        if (findNavController().popBackStack().not()) {
            // if the stack is not popped (no more destination to go back to), finish the activity
            activity?.finish()
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Internal
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Method called to observe all global state.
     */
    private fun initGlobalObservations() {
        // Close screen when shouldClose changes to true.
        (viewModel as? ScreenViewModel<*, *>)?.let { screenViewModel ->
            screenViewModel.state
                .map { it.shouldClose }
                .distinctUntilChanged()
                .filter { it == true }
                .observe(viewLifecycleOwner) { onBackPressed() }
        }
    }
}
