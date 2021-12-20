package com.android.weatherapplication.feature.addcity

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.map
import androidx.navigation.fragment.findNavController
import com.android.weatherapplication.R
import com.android.weatherapplication.common.ScreenFragment
import com.android.weatherapplication.common.ScreenStateFragment
import com.android.weatherapplication.databinding.FragmentAddCityBinding
import com.android.weatherapplication.utils.extensions.displayToast
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.snakydesign.livedataextensions.filter
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Add city Fragment.
 */
class AddCityFragment : ScreenStateFragment<FragmentAddCityBinding>() {

    override val viewModel: AddCityViewModel by viewModel()
    override val screenOrientation = ScreenFragment.SENSOR

    ///////////////////////////////////////////////////////////////////////////
    // Views init
    ///////////////////////////////////////////////////////////////////////////

    override fun bindView(inflater: LayoutInflater, container: ViewGroup?): FragmentAddCityBinding {
        return FragmentAddCityBinding.inflate(inflater, container, false)
    }

    override fun initViews() {
        super.initViews()
        val apiKey = getString(R.string.api_key)
        if (!Places.isInitialized()) {
            Places.initialize(requireContext(), apiKey)
        }
        setupAutoCompletePlace()
    }

    ///////////////////////////////////////////////////////////////////////////
    // Views events
    ///////////////////////////////////////////////////////////////////////////

    override fun initEvents() {
        binding.apply {
            toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
            addCityButton.setOnClickListener {
                viewModel.addCity()
            }
        }
    }

    override fun initObservations() {
        viewModel.state
                .map { it.messageResId }
                .distinctUntilChanged()
                .observe(viewLifecycleOwner) {
                    it?.let { it1 -> requireContext().displayToast(it1) }
                }
        viewModel.state
                .map { it.isCityAdded }
                .filter { it == true }
                .distinctUntilChanged()
                .observe(viewLifecycleOwner) { findNavController().popBackStack() }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Internal
    ///////////////////////////////////////////////////////////////////////////

    private fun setupAutoCompletePlace() {
        // Initialize the AutocompleteSupportFragment.
        val autocompleteFragment =
                childFragmentManager.findFragmentById(R.id.autocompleteFragment) as AutocompleteSupportFragment

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME))

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                Log.i("TAG", "Place: ${place.name}, ${place.id}")
                place.id?.let { place.name?.let { it1 -> viewModel.setCity(it, it1) } }
                clearText(autocompleteFragment)
            }

            override fun onError(status: Status) {
                Log.i("TAG", "An error occurred: $status")
            }
        })
    }

    private fun clearText(autocompleteFragment: AutocompleteSupportFragment) {
        val clearButton: ImageView? =
                autocompleteFragment.view?.findViewById(R.id.places_autocomplete_clear_button)
        clearButton?.setOnClickListener {
            val placeEditText =
                    autocompleteFragment.view?.findViewById(R.id.places_autocomplete_search_input) as EditText
            placeEditText.setText("")
            viewModel.resetCity()
        }
    }
}
