package com.android.weatherapplication.feature.cities

import android.graphics.Canvas
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.map
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.android.weatherapplication.R
import com.android.weatherapplication.common.ScreenFragment
import com.android.weatherapplication.common.ScreenStateFragment
import com.android.weatherapplication.common.SwipeController
import com.android.weatherapplication.common.SwipeControllerActions
import com.android.weatherapplication.common.view.recyclerview.MarginItemDecorator
import com.android.weatherapplication.databinding.FragmentCitiesBinding
import com.android.weatherapplication.domain.model.entity.CitiesForSearchEntity
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Cities Fragment.
 */
class CitiesFragment : ScreenStateFragment<FragmentCitiesBinding>() {

    override val viewModel: CitiesViewModel by viewModel()
    override val screenOrientation = ScreenFragment.SENSOR

    val adapter = CitiesAdapter {
        navigateToCurrentWeatherAndForecast(it)
    }

    ///////////////////////////////////////////////////////////////////////////
    // Views init
    ///////////////////////////////////////////////////////////////////////////

    override fun bindView(inflater: LayoutInflater, container: ViewGroup?): FragmentCitiesBinding {
        return FragmentCitiesBinding.inflate(inflater, container, false)
    }

    override fun initViews() {
        super.initViews()
        binding.myCitiesRecyclerView.adapter = adapter
        binding.myCitiesRecyclerView.layoutManager = GridLayoutManager(context, 1)

        val swipeController = SwipeController(requireContext(), object : SwipeControllerActions {
            override fun onRightClicked(position: Int) {
                deleteCity(position)
            }
        }, ContextCompat.getDrawable(requireContext(), R.drawable.ic_trash))

        ItemTouchHelper(swipeController).attachToRecyclerView(binding.myCitiesRecyclerView)

        binding.myCitiesRecyclerView.apply {
            addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
                    swipeController.onDraw(c)
                }
            })
            addItemDecoration(
                MarginItemDecorator(
                    resources.getDimension(R.dimen.spacing_small).toInt()
                )
            )
        }
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

    override fun initObservations() {
        viewModel.state
            .map { it.citiesList }
            .distinctUntilChanged()
            .observe(viewLifecycleOwner, this::updateCitiesList)
    }

    ///////////////////////////////////////////////////////////////////////////
    // Internal
    ///////////////////////////////////////////////////////////////////////////

    private fun updateCitiesList(cityList: List<CitiesForSearchEntity>) {
        adapter.citiesList = cityList.toMutableList()
    }

    private fun navigateToCurrentWeatherAndForecast(city: CitiesForSearchEntity) {
        Log.i("TAG", "city : $city")
    }

    private fun deleteCity(position: Int) {
        viewModel.deleteCity(adapter.getItem(position))
        adapter.removeAt(position)
        Toast.makeText(
            requireContext(),
            viewModel.currentState.messageResId,
            Toast.LENGTH_SHORT
        ).show()
    }
}
