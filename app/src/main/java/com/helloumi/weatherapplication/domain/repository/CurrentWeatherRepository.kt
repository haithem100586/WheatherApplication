package com.helloumi.weatherapplication.domain.repository

import androidx.lifecycle.LiveData
import com.helloumi.weatherapplication.domain.model.entity.CurrentWeatherEntity

interface CurrentWeatherRepository {

    /**
     * Gets current weather.
     *
     * @return the [CurrentWeatherEntity] [LiveData].
     */
    fun getCurrentWeather(): LiveData<CurrentWeatherEntity>

    /**
     * Inserts current weather.
     *
     * @param currentWeather the current user to insert.
     */
    suspend fun insertCurrentWeather(currentWeather: CurrentWeatherEntity)

    /**
     * Deletes and inserts current weather.
     *
     * @param currentWeather the current user to insert.
     */
    suspend fun deleteAndInsert(currentWeather: CurrentWeatherEntity)

    /**
     * Deletes current weather.
     */
    suspend fun deleteCurrentWeather()

    /**
     * Gets current weather count.
     *
     * @return the count of current weather.
     */
    suspend fun getCount(): Int
}
