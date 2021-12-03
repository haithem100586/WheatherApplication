package com.android.weatherapplication.domain.repository

import androidx.lifecycle.LiveData
import com.android.weatherapplication.domain.model.entity.ForecastEntity

interface ForecastRepository {

    /**
     * Fetches all forecasts.
     *
     * @return the [ForecastEntity] [LiveData].
     */
    fun getAllForecast(): LiveData<ForecastEntity>

    /**
     * Fetches all forecasts by coord.
     *
     * @param lat the latitude
     * @param lon the longitude.
     *
     * @return the [ForecastEntity] [LiveData].
     */
    fun getForecastByCoord(lat: Double, lon: Double): LiveData<ForecastEntity>

    /**
     * Inserts forecast state.
     *
     * @param forecast the forecast to insert.
     */
    suspend fun insertForecast(forecast: ForecastEntity)

    /**
     * Deletes all forecast and inserts one.
     *
     * @param forecast the forecast to insert.
     */
    suspend fun deleteAndInsert(forecast: ForecastEntity)

    /**
     * Updates forecast.
     *
     * @param forecast the forecast to update.
     */
    suspend fun updateForecast(forecast: ForecastEntity)

    /**
     * Deletes forecast.
     *
     * @param forecast the forecast to delete.
     */
    suspend fun deleteForecast(forecast: ForecastEntity)

    /**
     * Deletes all forecasts.
     */
    suspend fun deleteAllForecast()

    /**
     * Gets forecasts count.
     *
     * @return the count of forecast.
     */
    suspend fun getCount(): Int
}