package com.helloumi.weatherapplication.feature.cities

import android.graphics.Canvas
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.map
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.helloumi.weatherapplication.R
import com.helloumi.weatherapplication.common.ScreenFragment
import com.helloumi.weatherapplication.common.ScreenStateFragment
import com.helloumi.weatherapplication.common.SwipeController
import com.helloumi.weatherapplication.common.SwipeControllerActions
import com.helloumi.weatherapplication.common.view.recyclerview.MarginItemDecorator
import com.helloumi.weatherapplication.databinding.FragmentCitiesBinding
import com.helloumi.weatherapplication.domain.model.entity.CitiesForSearchEntity
import com.helloumi.weatherapplication.utils.extensions.displayToast
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Cities Fragment.
 */
class CitiesFragment : ScreenStateFragment<FragmentCitiesBinding>() {

    override val viewModel: CitiesViewModel by viewModel()
    override val screenOrientation = ScreenFragment.SENSOR

    private val citiesAdapter = CitiesAdapter {
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
        binding.myCitiesRecyclerView.apply {
            adapter = citiesAdapter
            layoutManager = GridLayoutManager(context, 1)
            swipeToRecyclerView(this)
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
        citiesAdapter.citiesList = cityList.toMutableList()
    }

    private fun navigateToCurrentWeatherAndForecast(city: CitiesForSearchEntity) {
        if (viewModel.currentState.isInternetAvailable) {
            Log.i("navigateToWeather", "city : $city")
            if (city.name != null) {
                val action = CitiesFragmentDirections.toWeather(city.name)
                findNavController().navigate(action)
            }
        } else {
            requireContext().displayToast(R.string.weather_no_connection)
        }
    }

    private fun swipeToRecyclerView(recyclerView: RecyclerView) {
        val swipeController = SwipeController(requireContext(), object : SwipeControllerActions {
            override fun onRightClicked(position: Int) {
                deleteCity(position)
            }
        }, ContextCompat.getDrawable(requireContext(), R.drawable.ic_trash))

        ItemTouchHelper(swipeController).attachToRecyclerView(recyclerView)

        recyclerView.apply {
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

    private fun deleteCity(position: Int) {
        viewModel.deleteCity(citiesAdapter.getItem(position))
        citiesAdapter.removeAt(position)
        requireContext().displayToast(viewModel.currentState.messageResId)
    }
}
