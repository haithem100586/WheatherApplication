package com.helloumi.weatherapplication.feature.weather

import android.annotation.SuppressLint
import android.content.Context
import com.helloumi.weatherapplication.R
import com.helloumi.weatherapplication.feature.weather.WeatherContract.State
import com.helloumi.weatherapplication.feature.weather.WeatherContract.ViewModel
import com.helloumi.weatherapplication.common.BaseEvent
import com.helloumi.weatherapplication.common.BaseStateViewModel
import com.helloumi.weatherapplication.common.ext.android.observeOnce
import com.helloumi.weatherapplication.domain.model.CurrentWeatherResult
import com.helloumi.weatherapplication.domain.model.ForecastResult
import com.helloumi.weatherapplication.domain.usecases.GetCurrentWeatherUseCase
import com.helloumi.weatherapplication.domain.usecases.GetForecastUseCase
import com.helloumi.weatherapplication.utils.extensions.resIdByName

/**
 * Weather ViewModel.
 */
@SuppressLint("StaticFieldLeak")
class WeatherViewModel(
    private val context: Context,
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
    private val getForecastUseCase: GetForecastUseCase,
    initialState: State = State()
) : BaseStateViewModel<State, BaseEvent>(initialState), ViewModel {

    ///////////////////////////////////////////////////////////////////////////
    // ViewModel contract implementation
    ///////////////////////////////////////////////////////////////////////////

    override fun getCurrentWeather(cityName: String) {
        updateState { it.copy(isWeatherTaken = null) }
        getCurrentWeatherUseCase.execute(cityName).observeOnce(this) { result ->
            when (result) {
                is CurrentWeatherResult.Success -> {
                    updateState {
                        it.copy(
                            isWeatherTaken = true,
                            currentWeatherResponse = result.currentWeatherResponse,
                            weatherIconResId = getWeatherIconResId(result),
                            errorText = null
                        )
                    }
                }
                is CurrentWeatherResult.ServerUnavailable ->
                    updateState {
                        it.copy(
                            isWeatherTaken = false,
                            errorText = R.string.weather_server_unreachable
                        )
                    }
                is CurrentWeatherResult.ServerError ->
                    updateState {
                        it.copy(isWeatherTaken = false, errorText = R.string.weather_server_error)
                    }
            }
        }
    }

    override fun getForecast(cityName: String) {
        updateState { it.copy(isForecastTaken = null) }
        getForecastUseCase.execute(cityName).observeOnce(this) { result ->
            when (result) {
                is ForecastResult.Success -> {
                    updateState {
                        it.copy(
                            isForecastTaken = true,
                            forecastResponse = result.forecastResponse,
                            errorText = null
                        )
                    }
                }
                is ForecastResult.ServerUnavailable ->
                    updateState {
                        it.copy(
                            isForecastTaken = false,
                            errorText = R.string.weather_server_unreachable
                        )
                    }
                is ForecastResult.ServerError ->
                    updateState {
                        it.copy(isForecastTaken = false, errorText = R.string.weather_server_error)
                    }
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Internal
    ///////////////////////////////////////////////////////////////////////////

    private fun getWeatherIconResId(currentWeatherResult: CurrentWeatherResult.Success) =
        context.resIdByName(
            "icon_${currentWeatherResult.currentWeatherResponse.weather?.get(0)?.icon}",
            "drawable"
        )
}
