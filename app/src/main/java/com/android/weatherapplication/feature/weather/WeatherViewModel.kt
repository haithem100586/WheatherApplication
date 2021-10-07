package com.android.weatherapplication.feature.weather

import android.util.Log
import com.android.weatherapplication.R
import com.android.weatherapplication.feature.weather.WeatherContract.State
import com.android.weatherapplication.feature.weather.WeatherContract.ViewModel
import com.android.weatherapplication.common.BaseEvent
import com.android.weatherapplication.common.BaseStateViewModel
import com.android.weatherapplication.common.ext.android.observeOnce
import com.android.weatherapplication.domain.model.CurrentWeatherResult
import com.android.weatherapplication.domain.model.ForecastResult
import com.android.weatherapplication.domain.usecases.GetCurrentWeatherUseCase
import com.android.weatherapplication.domain.usecases.GetForecastUseCase
import com.android.weatherapplication.feature.main.NetworkAvailabilityMonitor

/**
 * Weather ViewModel.
 */
class WeatherViewModel(
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
    private val getForecastUseCase: GetForecastUseCase,
    networkAvailabilityMonitor: NetworkAvailabilityMonitor,
    initialState: State = State()
) : BaseStateViewModel<State, BaseEvent>(initialState), ViewModel {

    private var isNetworkAvailable = false

    init {
        networkAvailabilityMonitor.observe(this) { available -> isNetworkAvailable = available }
    }

    ///////////////////////////////////////////////////////////////////////////
    // ViewModel contract implementation
    ///////////////////////////////////////////////////////////////////////////

    override fun getCurrentWeather(cityName: String) {
        getCurrentWeatherUseCase.execute(cityName).observeOnce(this) { result ->
            Log.i("getCurrentWeather", "result : $result")
            when (result) {
                is CurrentWeatherResult.Success -> {
                    updateState {
                        it.copy(
                            currentWeatherResponse = result.currentWeatherResponse,
                            weatherIconDrawable = result.currentWeatherResponse.weather?.get(0)?.icon,
                            errorText = null
                        )
                    }
                }
                is CurrentWeatherResult.ServerUnavailable ->
                    updateState { it.copy(errorText = R.string.weather_server_unreachable) }
                is CurrentWeatherResult.ServerError ->
                    updateState { it.copy(errorText = R.string.weather_server_error) }
            }
        }
    }

    override fun getForecast(cityName: String) {
        getForecastUseCase.execute(cityName).observeOnce(this) { result ->
            Log.i("getForecast", "result : $result")
            when (result) {
                is ForecastResult.Success -> {
                    updateState {
                        it.copy(
                            forecastResponse = result.forecastResponse,
                            errorText = null
                        )
                    }
                }
                is ForecastResult.ServerUnavailable ->
                    updateState { it.copy(errorText = R.string.weather_server_unreachable) }
                is ForecastResult.ServerError ->
                    updateState { it.copy(errorText = R.string.weather_server_error) }
            }
        }
    }
}
