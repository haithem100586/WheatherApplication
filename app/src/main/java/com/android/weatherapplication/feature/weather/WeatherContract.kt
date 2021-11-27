package com.android.weatherapplication.feature.weather

import androidx.annotation.StringRes
import com.android.weatherapplication.common.BaseState
import com.android.weatherapplication.domain.model.CurrentWeatherResponse
import com.android.weatherapplication.domain.model.ForecastResponse

/**
 * Weather contract.
 */
interface WeatherContract {

    interface ViewModel {

        /**
         * Gets current weather.
         *
         * @param cityName the name of city.
         */
        fun getCurrentWeather(cityName: String)

        /**
         * Adds city in data base.
         *
         * @param cityName the name of city.
         */
        fun getForecast(cityName: String)
    }

    /**
     * @property currentWeatherResponse the current weather response.
     * @property weatherIconDrawable resource id for weather icon.
     * @property errorText resource id for text error.
     */
    data class State(
        val currentWeatherResponse: CurrentWeatherResponse? = null,
        val forecastResponse: ForecastResponse? = null,
        val weatherIconDrawable: String? = null,
        @StringRes val errorText: Int? = null
    ) : BaseState
}