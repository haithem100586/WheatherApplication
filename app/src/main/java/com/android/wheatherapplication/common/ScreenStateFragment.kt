/*
 *      Copyright (C) 2020 Parrot Drones SAS
 *
 *      Redistribution and use in source and binary forms, with or without
 *      modification, are permitted provided that the following conditions
 *      are met:
 *      * Redistributions of source code must retain the above copyright
 *         notice, this list of conditions and the following disclaimer.
 *      * Redistributions in binary form must reproduce the above copyright
 *         notice, this list of conditions and the following disclaimer in
 *         the documentation and/or other materials provided with the
 *         distribution.
 *      * Neither the name of the Parrot Company nor the names
 *        of its contributors may be used to endorse or promote products
 *        derived from this software without specific prior written
 *        permission.
 *
 *      THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 *      "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 *      LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 *      FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 *      PARROT COMPANY BE LIABLE FOR ANY DIRECT, INDIRECT,
 *      INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 *      BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS
 *      OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED
 *      AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 *      OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 *      OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 *      SUCH DAMAGE.
 */

package com.android.wheatherapplication.common

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.annotation.CallSuper
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.map
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.android.wheatherapplication.common.ScreenFragment.Companion.ScreenOrientation
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
