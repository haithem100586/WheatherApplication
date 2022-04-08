package com.helloumi.weatherapplication.domain.usecases

import com.helloumi.weatherapplication.data.repository.ApcRepositoryImpl

/**
 * Gets current weather.
 */
class GetCurrentWeatherUseCase(private val apcRepositoryImpl: ApcRepositoryImpl) {

    /**
     * Executes use case.
     *
     * @return the current weather result LiveData.
     */
    fun execute(cityName: String) = apcRepositoryImpl.getCurrentWeatherByCityName(cityName)
}
