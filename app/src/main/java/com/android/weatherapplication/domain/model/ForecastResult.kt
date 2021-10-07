package com.android.weatherapplication.domain.model

/**
 * Current Weather result, from web service requests.
 */
sealed class ForecastResult {

    /**
     * Success result.
     *
     * @property forecastResponse downloaded forecastResponse.
     */
    class Success(val forecastResponse: ForecastResponse) : ForecastResult()

    /**
     * Unavailable server result.
     */
    object ServerUnavailable : ForecastResult()

    /**
     * Error on server result.
     */
    object ServerError : ForecastResult()
}
