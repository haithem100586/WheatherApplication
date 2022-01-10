package com.helloumi.weatherapplication.common

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

/**
 * Base class to be extended by activities.``
 */
abstract class BaseActivity<B : ViewBinding> : AppCompatActivity() {

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
     *
     * @return the associated [ViewBinding] object.
     */
    abstract fun bindView(inflater: LayoutInflater): B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = bindView(layoutInflater)
        setContentView(binding.root)
        initObservations()
    }

    /**
     * Override to subscribe to the activity's view-model state (if needed).
     */
    open fun initObservations() {}
}
