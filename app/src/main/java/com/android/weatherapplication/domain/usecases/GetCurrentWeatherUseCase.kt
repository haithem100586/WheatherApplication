package com.android.weatherapplication.domain.usecases

import com.android.weatherapplication.data.repository.ApcRepositoryImpl

/**
 * Gets current weather.
 */
class GetCurrentWeatherUseCase(private val apcRepositoryImpl: ApcRepositoryImpl) {

    /**
     * @return the current weather result LiveData.
     */
    fun execute(cityName: String) = apcRepositoryImpl.getCurrentWeatherByCityName(cityName)
}
