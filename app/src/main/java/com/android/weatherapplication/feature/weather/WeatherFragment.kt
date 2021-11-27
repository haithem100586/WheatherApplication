package com.android.weatherapplication.feature.weather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.android.weatherapplication.utils.extensions.resIdByName
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
            .map { it.weatherIconDrawable }
            .distinctUntilChanged()
            .observe(viewLifecycleOwner) { displayWeatherIcon(it) }

        viewModel.state
            .map { it.currentWeatherResponse }
            .distinctUntilChanged()
            .observe(viewLifecycleOwner) { currentWeatherResponse ->
                if (currentWeatherResponse?.weather.isNullOrEmpty()) {
                    setVisibilityWeatherDataView(false)
                } else {
                    displayCurrentWeatherData(currentWeatherResponse)
                    setVisibilityWeatherDataView(true)
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
                    setVisibilityForecastDataView(true)
                } else {
                    setVisibilityForecastDataView(false)
                }
            }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Internal
    ///////////////////////////////////////////////////////////////////////////

    private fun displayWeatherIcon(weatherIconDrawable: String?) {
        Glide.with(requireContext())
            .load(requireContext().resIdByName("icon_$weatherIconDrawable", "drawable"))
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

    private fun setVisibilityWeatherDataView(show: Boolean) {
        binding.apply {
            noDataText.visibility = if (show) View.GONE else View.VISIBLE
            weatherRelativeLayout.visibility = if (show) View.VISIBLE else View.GONE
            loading.visibility = View.GONE
        }
    }

    private fun setVisibilityForecastDataView(show: Boolean) {
        binding.apply {
            noDataText.visibility = if (show) View.GONE else View.VISIBLE
            weatherRelativeLayout.visibility = if (show) View.VISIBLE else View.GONE
            loading.visibility = View.GONE
        }
    }

    private fun updateItemsList(itemsList: List<ListItem>) {
        adapter.itemsList = itemsList.toMutableList()
    }
}
