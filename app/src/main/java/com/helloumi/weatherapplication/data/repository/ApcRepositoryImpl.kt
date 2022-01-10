package com.helloumi.weatherapplication.data.repository

import androidx.lifecycle.liveData
import com.helloumi.weatherapplication.data.api.apc.ApcAPI
import com.helloumi.weatherapplication.domain.model.CurrentWeatherResponse
import com.helloumi.weatherapplication.domain.model.CurrentWeatherResult
import com.helloumi.weatherapplication.domain.model.ForecastResponse
import com.helloumi.weatherapplication.domain.model.ForecastResult
import com.helloumi.weatherapplication.domain.repository.ApcRepository
import com.helloumi.weatherapplication.utils.Constants.NetworkService.API_KEY_VALUE
import com.helloumi.weatherapplication.utils.Constants.Unit.UNITS
import com.snakydesign.livedataextensions.toSingleLiveData
import kotlinx.coroutines.Dispatchers
import retrofit2.Response
import java.io.IOException

class ApcRepositoryImpl(private val apcAPI: ApcAPI) : ApcRepository {

    override fun getCurrentWeatherByCityName(cityName: String) = liveData(Dispatchers.IO) {
        emit(
            when (val response = processCallCurrentWeather(
                apcAPI.getCurrentWeatherByCityName(cityName, UNITS, API_KEY_VALUE)
            )) {
                is CurrentWeatherResponse -> CurrentWeatherResult.Success(response)
                is Int -> CurrentWeatherResult.ServerError
                else -> CurrentWeatherResult.ServerUnavailable
            }
        )
    }.toSingleLiveData()

    override fun getForecastByCityName(cityName: String)= liveData(Dispatchers.IO) {
        emit(
            when (val response = processCallForecast(
                apcAPI.getForecastByCityName(cityName, UNITS, API_KEY_VALUE)
            )) {
                is ForecastResponse -> ForecastResult.Success(response)
                is Int -> ForecastResult.ServerError
                else -> ForecastResult.ServerUnavailable
            }
        )
    }.toSingleLiveData()

    ///////////////////////////////////////////////////////////////////////////
    // Internal
    ///////////////////////////////////////////////////////////////////////////

    private fun processCallCurrentWeather(responseCall: Response<CurrentWeatherResponse>): Any? {
        return try {
            if (responseCall.isSuccessful) {
                responseCall.body()
            } else {
                responseCall.code()
            }
        } catch (e: IOException) {
            CurrentWeatherResult.ServerUnavailable
        }
    }

    private fun processCallForecast(responseCall: Response<ForecastResponse>): Any? {
        return try {
            if (responseCall.isSuccessful) {
                responseCall.body()
            } else {
                responseCall.code()
            }
        } catch (e: IOException) {
            ForecastResult.ServerUnavailable
        }
    }
}
