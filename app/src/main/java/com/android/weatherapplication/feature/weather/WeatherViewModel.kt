package com.android.weatherapplication.feature.weather

import android.annotation.SuppressLint
import android.content.Context
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
import com.android.weatherapplication.utils.extensions.resIdByName

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
        updateState { it.copy(isWeatherTaken = true) }
        getCurrentWeatherUseCase.execute(cityName).observeOnce(this) { result ->
            Log.i("getCurrentWeather", "result : $result")
            when (result) {
                is CurrentWeatherResult.Success -> {
                    updateState {
                        it.copy(
                            isWeatherTaken = false,
                            currentWeatherResponse = result.currentWeatherResponse,
                            weatherIconResId = context.resIdByName(
                                "icon_${result.currentWeatherResponse.weather?.get(0)?.icon}",
                                "drawable"
                            ),
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
        updateState { it.copy(isForecastTaken = true) }
        getForecastUseCase.execute(cityName).observeOnce(this) { result ->
            Log.i("getForecast", "result : $result")
            when (result) {
                is ForecastResult.Success -> {
                    updateState {
                        it.copy(
                            isForecastTaken = false,
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
}
