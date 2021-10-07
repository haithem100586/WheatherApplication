package com.android.weatherapplication.domain.usecases

import com.android.weatherapplication.data.repository.ApcRepositoryImpl

/**
 * Gets forecast.
 */
class GetForecastUseCase(private val apcRepositoryImpl: ApcRepositoryImpl) {

    /**
     * @return the current weather result LiveData.
     */
    fun execute(cityName: String) = apcRepositoryImpl.getForecastByCityName(cityName)
}
