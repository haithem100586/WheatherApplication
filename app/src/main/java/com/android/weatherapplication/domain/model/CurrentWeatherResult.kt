package com.android.weatherapplication.domain.model

/**
 * Current Weather result, from web service requests.
 */
sealed class CurrentWeatherResult {

    /**
     * Success result.
     *
     * @property currentWeatherResponse downloaded currentWeatherResponse.
     */
    class Success(val currentWeatherResponse: CurrentWeatherResponse) : CurrentWeatherResult()

    /**
     * Unavailable server result.
     */
    object ServerUnavailable : CurrentWeatherResult()

    /**
     * Error on server result.
     */
    object ServerError : CurrentWeatherResult()
}
