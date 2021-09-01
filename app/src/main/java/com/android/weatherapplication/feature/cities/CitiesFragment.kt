package com.android.weatherapplication.feature.cities

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.android.weatherapplication.R
import com.android.weatherapplication.common.ScreenFragment
import com.android.weatherapplication.common.ScreenStateFragment
import com.android.weatherapplication.databinding.FragmentCitiesBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Cities Fragment.
 */
class CitiesFragment : ScreenStateFragment<FragmentCitiesBinding>() {

    override val viewModel: CitiesViewModel by viewModel()
    override val screenOrientation = ScreenFragment.SENSOR

    ///////////////////////////////////////////////////////////////////////////
    // Views init
    ///////////////////////////////////////////////////////////////////////////

    override fun bindView(inflater: LayoutInflater, container: ViewGroup?): FragmentCitiesBinding {
        return FragmentCitiesBinding.inflate(inflater, container, false)
    }

    ///////////////////////////////////////////////////////////////////////////
    // Views events
    ///////////////////////////////////////////////////////////////////////////

    override fun initEvents() {
        binding.apply {
            addCityButton.setOnClickListener {
                findNavController().navigate(R.id.to_add_city)
            }
        }
    }
}
