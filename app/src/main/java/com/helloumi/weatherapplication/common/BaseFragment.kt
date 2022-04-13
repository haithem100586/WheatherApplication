package com.helloumi.weatherapplication.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

/**
 * Base class to be extended by fragments.
 *
 * Provides useful callbacks to reduce boilerplate.
 */
abstract class BaseFragment<B : ViewBinding> : Fragment() {

    private var _binding: B? = null

    /**
     * Reference of generated [ViewBinding] class which contains the views of the inflated layout.
     *
     * This property is only valid between onCreateView and onDestroyView.
     */
    val binding
        get() = _binding!!

    /**
     * Inflates the layout associated with the current Fragment and gets the generated [ViewBinding] reference.
     *
     * @param inflater the [LayoutInflater] object to use to inflate any view in the current fragment.
     * @param container the parent view that the fragment's UI should be attached to, if any.
     *
     * @return the associated [ViewBinding] object.
     */
    abstract fun bindView(inflater: LayoutInflater, container: ViewGroup?): B

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = bindView(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initEvents()
        initObservations()
    }

    override fun onDestroyView() {
        releaseEvents()
        _binding = null
        super.onDestroyView()
    }

    /**
     * Override to configure the layout's views (if needed).
     */
    open fun initViews() {}

    /**
     * Override to initialize view's events (if needed).
     */
    open fun initEvents() {}

    /**
     * Override to release view's events (if needed).
     */
    open fun releaseEvents() {}

    /**
     * Override to subscribe to the activity or fragment's view-model state (if needed).
     */
    open fun initObservations() {}
}
