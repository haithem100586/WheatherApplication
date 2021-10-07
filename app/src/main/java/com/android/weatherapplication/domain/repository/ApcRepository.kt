package com.android.weatherapplication.domain.repository

import com.android.weatherapplication.domain.model.CurrentWeatherResult
import com.android.weatherapplication.domain.model.ForecastResult
import com.snakydesign.livedataextensions.livedata.SingleLiveData

interface ApcRepository {

    /**
     * Requests current weather to the APC services.
     *
     * @param cityName the name city.
     *
     * @return a CurrentWeatherResult SingleLiveData
     */
    fun getCurrentWeatherByCityName(cityName: String): SingleLiveData<CurrentWeatherResult>

    /**
     * Requests forecast to the APC services.
     *
     * @param cityName the name city.
     *
     * @return a ForecastResult SingleLiveData
     */
    fun getForecastByCityName(cityName: String): SingleLiveData<ForecastResult>
}
