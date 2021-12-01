package com.android.weatherapplication.feature.weather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.map
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.weatherapplication.R
import com.android.weatherapplication.common.ScreenFragment
import com.android.weatherapplication.common.ScreenStateFragment
import com.android.weatherapplication.databinding.FragmentWeatherBinding
import com.android.weatherapplication.domain.model.CurrentWeatherResponse
import com.android.weatherapplication.domain.model.ListItem
import com.android.weatherapplication.utils.extensions.displayToast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Weather Fragment.
 */
class WeatherFragment : ScreenStateFragment<FragmentWeatherBinding>() {

    override val viewModel: WeatherViewModel by viewModel()
    override val screenOrientation = ScreenFragment.SENSOR

    private lateinit var adapter: ForecastAdapter

    ///////////////////////////////////////////////////////////////////////////
    // Views init
    ///////////////////////////////////////////////////////////////////////////

    override fun bindView(inflater: LayoutInflater, container: ViewGroup?): FragmentWeatherBinding {
        return FragmentWeatherBinding.inflate(inflater, container, false)
    }

    override fun initViews() {
        super.initViews()
        val args: WeatherFragmentArgs by navArgs()
        val linearLayoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.HORIZONTAL, false
        )
        binding.forecastRecyclerView.apply {
            layoutManager = linearLayoutManager
            setHasFixedSize(true)
        }
        viewModel.getCurrentWeather(args.city)
        viewModel.getForecast(args.city)
    }

    ///////////////////////////////////////////////////////////////////////////
    // Views events
    ///////////////////////////////////////////////////////////////////////////

    override fun initEvents() {
        binding.apply {
            toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
        }
    }

    override fun initObservations() {
        viewModel.state
            .map { it.isLoading }
            .distinctUntilChanged()
            .observe(viewLifecycleOwner) { binding.loading.isVisible = it }

        viewModel.state
            .map { it.errorText }
            .distinctUntilChanged()
            .observe(viewLifecycleOwner) { if (it != null) requireContext().displayToast(it) }

        viewModel.state
            .map { it.weatherIconResId }
            .distinctUntilChanged()
            .observe(viewLifecycleOwner) { displayWeatherIcon(it) }

        viewModel.state
            .map { it.currentWeatherResponse }
            .distinctUntilChanged()
            .observe(viewLifecycleOwner) { currentWeatherResponse ->
                if (!currentWeatherResponse?.weather.isNullOrEmpty()) {
                    displayCurrentWeatherData(currentWeatherResponse)
                }
            }

        viewModel.state
            .map { it.forecastResponse }
            .distinctUntilChanged()
            .observe(viewLifecycleOwner) { forecastResponse ->
                if (!(forecastResponse?.list.isNullOrEmpty())) {
                    adapter = ForecastAdapter(requireContext())
                    forecastResponse?.list?.let { updateItemsList(it) }
                    binding.forecastRecyclerView.adapter = adapter
                }
            }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Internal
    ///////////////////////////////////////////////////////////////////////////

    private fun displayWeatherIcon(weatherIconDrawable: Int?) {
        Glide.with(requireContext())
            .load(weatherIconDrawable)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.mipmap.ic_launcher)
            .dontAnimate()
            .into(binding.forecastContainer.weatherIcon)
    }

    private fun displayCurrentWeatherData(currentWeatherResponse: CurrentWeatherResponse?) {
        binding.forecastContainer.apply {
            currentWeatherResponse?.let { currentweatherCardView.setCardBackgroundColor(it.getColor()) }
            temperatureText.text = currentWeatherResponse?.main?.getTempString()
            weatherMainText.text = currentWeatherResponse?.weather?.get(0)?.description
            humidityText.text = currentWeatherResponse?.main?.getHumidityString()
        }
    }

    private fun updateItemsList(itemsList: List<ListItem>) {
        adapter.itemsList = itemsList.toMutableList()
    }
}
