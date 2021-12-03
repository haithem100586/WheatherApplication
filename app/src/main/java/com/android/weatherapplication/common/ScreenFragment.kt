package com.android.weatherapplication.common

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.annotation.CallSuper
import androidx.annotation.IntDef
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding

/**
 * Base class to be extended by first level fragments (ie. screen fragments).
 */
abstract class ScreenFragment<B : ViewBinding> : BaseFragment<B>() {

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
                .takeIf { it != BEHIND }
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
        if (!findNavController().popBackStack()) {
            // if the stack is not popped (no more destination to go back to), finish the activity
            activity?.finish()
        }
    }

    companion object {
        @IntDef(BEHIND, PORTRAIT, LANDSCAPE, SENSOR)
        @Retention(AnnotationRetention.SOURCE)
        annotation class ScreenOrientation

        const val BEHIND = ActivityInfo.SCREEN_ORIENTATION_BEHIND
        const val PORTRAIT = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT
        const val LANDSCAPE = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
        const val SENSOR = ActivityInfo.SCREEN_ORIENTATION_SENSOR
    }
}
